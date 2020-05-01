# Item 28: Prefer lists to arrays

Arrays differ from generics in 2 ways:

## 1. COVARIANCE vs. INVARIANCE
- Arrays are COVARIANT
    - this means that if Sub is a subtype of Super, then 
    Sub[] is a subtype of Super[]
- Generics are INVARIANT
    - for two distinct types: Type1 and Type2, 
        - List<Type1> is unrelated to List<Type2>
        
        
Due to COVARIANCE, there are loopholes that make it through the
compiler. This means that potential errors are left to be found
by users at runtime 


    EX:
    
        Object[] objectArray = new Long[1];
        
        // This throws an ArrayStoreException at runtime. 
        // However the compiler should have caught it. 
        objectArray[0] = "I don't belong here!"; 


INVARIANCE provides type safety by catching the errors at
compile time


    EX:
    
        The following code won't compile:
        
        /* 
            The first line isn't going to get through the compiler
            because the return value isn't compatible w/ the
            the declared type.
        */
        List<Object> object = new ArrayList<Long>(); 
        object.add("I don't belong here");
        
        
## 2. ARRAYS ARE REIFIED, GENERICS IMPLEMENTED THROUGH ERASURE
REIFYING <br> 
- this means that arrays know + enforce their element's type at runtime
- violating Reification results in an ArrayStoreException (at Runtime)

ERASURE <br> 
- This means that generics enforce their type constraints only at 
COMPILE time. 
- they discard their element type information at runtime. 
    - this is the magic underneath Generic types to interoperate
    w/ pre-generic and post-generic code bases
    

## IMPACT
The differences between Arrays and Generics result in a number of 
illegal forms/compatibility issues
- this makes sense because the two properties of arrays mentioned 
above (COVARIANCE and REIFICATION) result in a lack of TYPE SAFETY
in arrays.
- allowing the creations below to occur make the structures
capable of throwing ClassCastExceptions

GENERIC ARRAY CREATION ERROR SCENARIOS:
- trying to create an array of a generic type
    - new List<E>[]
- trying to create an array of a parameterized type
    - new List<String>[]
- trying to create an array of type parameters
    - new E[]


    EX:
    
    1   List<String>[] strings = new List<>[1]
    2   List<Integer> ints = List.of(42);
    3   Object[] objects = strings;
    4   objects[0] = ints;
    5   String s = strings[0].get(0);
    
    If line 1 were (creates a "generic array") legal:
    -   line 2, creates a single int element (ok)
    -   line 3, stores generic array in Object array
        - this is allowed (COVARIANCE!)
    -   line 4, stores List<Integer> as the only element of
    the Object array from line 3.
        - this SUCCEEDS due to ERASURE
            - element type is discarded after compilation
        (runtime type of List<Integer> is just List)
        (runtime type of List<String>[] is List[])
            - no ArrayStoreException
    -   line 5, we get() the element from the Object array. 
        - the compiler auto casts it to String, but its an Integer
        - BOOM, ClassCastException at runtime. 
        
## NONREIFIABLE TYPES
A non-reifiable type is one whose RUNTIME REPRESENTATION contains less
information than its COMPILE-TIME REPRESENTATION.
- EX: E, List<E>, List<String>

NOTE: Remember that UNBOUNDED WILD CARD TYPES are REIFIABLE
- EX: List<?>, Map<?,?>

## DISADVANTAGES OF ILLEGAL CREATION OF GENERIC ARRAYS
- Its not possible for generic collection to return an array of its
element type.
- results in indirection/confusing warnings when using varargs methods and
generic types together.
    - (varargs methods require arrays to hold varargs parameters)
    - warnings are thrown in the case that the element type is NONREIFIABLE (i.e. 
    all generic types other than UNBOUNDED WILDCARDS).
    - Can be addressed using "SafeVarargs" annotation
    
## SOLVING GENERIC ARRAY CREATION ERRORS/UNCHECKED CAST WARNINGS ON CASTS TO ARRAY TYPES
- Use SafeVarargs annotation when using VARARGS METHODS
- prefer List<E> to E[]

## TRADEOFFS
WHAT I LOSE
- conciseness 
- performance

WHAT I GAIN
- type safety
- interoperability
    

### Example of Array Based Class:


    EX:
    
        public class Chooser {
            private final Object[] choiceArray;
            
            public Chooser(Collection choices) {
                choiceArray = choices.toArray();
            }
            
            public Object choose() {
                Random random = ThreadLocalRandom.current();
                return choiceArray[rnd.nextInt(choiceArray.length)];
            }
        }
        
The above code is an example of a class that would benefit from the
use of Generics.
- in order to use the class, you have to cast the choose
method's return value from Object to the desired type every time
the method is invoked. 
    - if you get the type wrong -> ClassCastException at Runtime
    
### Example of Fixed Class With Generics

First Example won't compile.
    
    EX:
    
        public class Chooser<T> {
            private final T[] choiceArray;
            
            public Chooser(Collection<T> choices) {
                choiceArray = choices.toArray();
            }
            
            public Object choose() {
                Random random = ThreadLocalRandom.current();
                return choiceArray[rnd.nextInt(choiceArray.length)];
            }
        }
        
        Fails to compile:
        
        Chooser.java:9: warning: [unchecked] unchecked cast
            choiceArray = (T[]) choices.toArray();
                                               ^
                                               
            required: T[], found: Object[]
            where T is a type-variable:
            T extends Object declared in class Chooser
            

This fails because of ERASURE
- since element type information is erased from generics at 
runtime, the compiler can't determine if the type is going to be safe
because it won't know what T represents. 
- (coincidentally, this will actually work, but the compiler fails
because it can't prove it due to the incompatibility between
Arrays and Lists.)

The solution is Lists > Arrays (See below for the final typesafe
version)
    
    EX:
    
        public class Chooser<T> {
            private final List<T> choiceList;
            
            public Chooser(Collection<T> choices) {
                choiceList = new ArrayList<>(choices);
            }
            
            public T choose() {
                Random random = ThreadLocalRandom.current();
                return choiceList.get(rnd.nextInt(choiceList.size()));
            }
        }
    
        
        