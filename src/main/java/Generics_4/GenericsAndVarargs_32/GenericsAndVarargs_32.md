# Item 32: Combine generics and varargs Judiciously

RULE: 
- it is UNSAFE to store a value in a generic varargs parameter. 

Varargs and Generics don't play well because varargs is a leaky abstraction
built on top of arrays, which are goverened by different rules than 
generics. 


## Leaky Abstraction -> Compiler Warnings. 
Varargs were designed to allow clients to pass a variable num of 
args to a a method. 
- when varargs is invoked an array is created to hold the varargs
- unfortunately, that array is visible even though it should be an
implementation detail. 

This leads to compiler warnings when varargs parameters have
generic or parameterized types. 

## Non-Reifiable Type -> Compiler Warnings (Heap Pollution)
(review) - A type whose runtime representation has less information
than it's compile-time representation. 
- almost ALL generic/parameterizede types are Non-Reifiable. 

Varargs methods of non-reifiable types -> compile warnings.  <br>
If method is invoked against inferred types that are non-reifiable, 
compiler generates a warning on invocation as well. 


### Heap Pollution Defined
Heap Pollution occurs when a variable of a parameterized type refers 
to an object that isn't of that type. 
- it leads to compiler's auto-generated casts to fail, which breaks
the entire generic type system. 
- these are the types of warnings we get above. 


    EX
    
        // Mixing Generics and VARARGS can violate type safety!
        
        static void dontDoThis(List<String>... stringLists) {
            
            List<Integer> intList = List.of(10);
            Object[] objects = stringLists;
            objects[0] = intList;               <-- HEAP POLLUTION!!!!!
            String s = stringLists[0].get(0);   <-- ClassCastException... You're shit is broke.
            
        }
            
The example has an "invisible" cast in the last line, because stringLists has ints due to the
stupid code above it. 

## Why is it even allowed to do this? 
Based on the example, why doesn't the compile just barf on the heap pollution and prevent 
us from getting into the sticky situation of the ClassCast at runtime? 
- There are actually typesafe implmentations of this, and it CAN be useful


    EX: 
    
        Arrays.asList(T... a)
        
        Collections.addAll(Collection<? super T> c, T... elements)
        
        EnumSet.of(E first, E... rest)
        

Prior to Java 7, there was no way to prevent the warnings. However, as of Java7, the SaveVarargs
annotation was added to Java. 
- This provides a promise from the developer of the code that the method is typesafe. 

### Ensuring Type Safety For Usability 
REMEMBER: a generic array is created when the method is invoked in order to hold the varargs 
parameters
- if the method doesn't store anything into the array AND
- it doesn't allow a reference to the array to escape THEN
- it is safe. 

Storing values in the array would overwrite the collected parameters, which is unsafe <br>
Allowing references to the array, enables untrusted code to access it, which... is unsafe <br> 

IF, the varargs parameter array is used ONLY to transmit a variable number of args from 
caller to method, then the method is safe. 

(We could also say that... if you use it explicitly for the purpose it was designed for, you 
should be ok. Don't get stupid!)

    EX:
    
        static <T> T[] toArray(T... args) {
            return args
        }
        
This is stupid. Don't do it. 
- This is returning a reference to the array, which means that any untrusted code that has
access to this method could potentially pollute the heap up the stack and result in 
ClassCastExceptions. 


    EX: 
    
        static <T> T[] pickTwo(T a, T b, T c) {
            switch(ThreadLocalRandom.current().nextInt(3) {
                case 0:
                    return toArray(a, b);
                case 1:
                    return toArray(a, c);
                case 2:
                    return toArray(b, c);
            }
            throw new AssertionError();         <-- This won't ever happen.
        }
        
There is nothing wrong with this, other than that it calls the shitty method above (toArray)
- At compile time, the compiler generates code to create a varargs param array to handle the
return statement in any of the switch statement branches. 
    - the array is generic, so it is of type Object[]
    
    
    Main
        
        public static void main(String[] args) {
            String[] attributes = pickTwo("Good", "Fast", "Cheap");
        }
        
Look at this main method. There is nothing wrong with this code. It will compile FINE. 
- However, this will throw a ClassCastException when you run it, because pickTwo() is going
to return an array of Objects, not strings. 
    - NOTE: This is a challenging problem, because the cast exception is removed by two calls up
    the stack. 
        - we are calling pickTwo() from main, which should be normal
        - pickTwo() makes the call to toArray() which results in the creation of the Object[].
        


### WHAT DID WE LEARN?
IT IS UNSAFE TO GIVE ANOTHER METHOD ACCESS TO A GENERIC VARARGS PARAMETER ARRAY

EXCEPTIONS
- it is ok to pass the array to another varargs method that is annotated w/ @SafeVarargs
- it is safe to pass the array to a NON-varargs method that is only computing a function on the
contents of the array. 

### Safe Usage Example (Using @SafeVarargs)

    @SafeVarargs
    static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list: lists)
            result.addAll(list);
        return result;
    }
    
    
    audience = flatten(List.of(friends, romans, countrymen));
    
(The above is supported because List.of is annotated w/ @SafeVarargs)    


The rule of thumb for using @SafeVarargs is 
- Use it on EVERY method w/ a varargs parameter of a GENERIC or PARAMETERIZED
type. 
    - this prevents users from being annoyed by the compiler warnings
    - MAKE SURE IT IS SAFE!!!!!
    
1. It shouldn't store anything in the varargs parameter array
2. it doesn't make the array (or a clone of it) visible to untrusted code


## @SAFEVARARGS Approach

PROS:
- compiler can PROVE that method is typesafe. 
- you don't have to worry about any errors in determining type safety
(It will break if it isn't type safe!)

CONS:
- client code is more complicated/ verbose
- performance hit. 


