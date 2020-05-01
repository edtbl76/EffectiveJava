# Item 26: Don't use raw types
GENERIC CLASS/INTERFACE (a.k.a. GENERIC TYPES):
- any class/interface whose declaration includes TYPE PARAMETERS
- each generic type defines a set of PARAMETERIZED TYPES
    - class/interface name followed by angle-bracketed list of ACTUAL TYPE PARAMETERS
    - actual type params correspond to the generic type's formal type parameter
- RAW TYPES are generic types w/o TYPE PARAMETERS
    - these behave as if all generic type data were removed from the type declaration
    - exist almost entirely for backward compatibility to pre-generic code.
    
    
    EX:
    
        GENERIC TYPE:
            List<E>         List of E
            E               FORMAL TYPE PARAMETER
            
        PARAMETERIZED TYPE
            List<String>    List of String
            String          ACTUAL TYPE PARAMETER (that corresponds to FORMAL TYPE PARAM, E)
            
        RAW TYPE
            List            List.

## BAD RAW TYPES 

PRE GENERIC, you had to use raw types. <br>
NOTE: If you use raw types you lose ALL SAFETY AND EXPRESIVENESS benefits of Generic Types

    EX:
    
        private final Collection stamps = ...;
        
Doing this today creates problems, because we can add objects to the collection that will compile... 

    EX:
        stamps.add(new Coin(...)); // <-- causes warning at compile time, but this won't work
                                            with raw types.
                              
                              
But won't execute do to ClassCastException

   
    EX:
    
         // Raw iterator! Don't do this either
         for (Iterator iterator = stamps.iterator(); iterator.hasNext(); ) {
            Stamp stamp = (Stamp) iterator.next();  // <--- This causes ClassCastException
                stamp.cancel();
         }
                                        
                               
This is especially bad, because we are violating one of the important concepts of SHIFT LEFT 
- we are pushing an error that should probably be determined at compile time to runtime. 
- When an error is discovered at compile time, then it can't be built, tested or go through a 
pipeline to be delivered to a customer. 
- When errors are discovered at runtime, it is feasible that the first person to discover the
problem is a customer/user. Not good. 


## USING GENERICS

    EX:
    
        private final Collection<Stamp> stamps;
        
The declaration above guarantees (at compile time!) that this collection instance can only 
contain objects of type Stamp.
- when using generics, the compiler inserts "invisible casts" when retrieving elements from
Collections, and guarantees success. 

If someone writes a line that inserts any other type than a Stamp in that collection, 
it will fail at compile time.


### WHEN TO USE RAW TYPES (IF YOU HAVE TO)
1 . Circumstances that require MIGRATION COMPATIBILITY. 
- some legacy codebases predate generics. While less and less of this code is in use, Java
saw fit to ensure that current versions of java still support raw types. 

2 . When using Class Literals.
- java specification does not permit the use of PARAMETERIZED TYPES w/ class literals
    
    
    EX:
        LEGAL:      List.class, String[].class, int.class
        NOT:        List<String>.class, List<?>.class
        
3 . When using "instanceof" operator.
- Generic Type information is erased at runtime, therefore it is impossible to use INSTANCEOF
with PARAMETERIZED TYPES other than UNBOUNDED WILDCARD TYPES.


This is an example of using INSTANCEOF w/ GENERIC TYPES (and RAW TYPES) properly

    EX:
        if (o instanceof Set) {             // Raw Type
            Set<?> s = (Set<?>) o;          // UNBOUNDED Wildcard Type
        }
        
    
    

### ERASURE GENERICS VS RAW TYPES
What is the difference between List\<Object> (arbitrary object, or ERASURE) vs. List (raw type)
- raw types are not Generics. They don't have access to any of the code of the system of generics
provided by the Java Platform. 
- List\<Object> is "truly" Generic as it is a generic type that specifies java.lang.Object as
the actual type parameter. 
    - this means the collection is capable of holding any type of object
    
ERASURES preserve type safety, whereas raw types don't


In the following example, we are passing a generic type to a method that uses a raw type.
This results in a RUNTIME failure when we try to use the Collection that has been modified
by the raw type.
- Depending on compiler, you SHOULD see warnings at compile time, but the compile won't
fail.


    EX:
    
        public static void main(String[] args) {
            List<String> strings = new ArrayList<>();
            unsafeAdd(strings, Integer.valueOf(42));
            
            // This has a compiler-generated cast from the generics system
            String s = strings.get(0); 
        }
        
        // This method takes a RAW TYPE.
        private static void unsafeAdd(List list, Object object) {
            list.add(object);
        }
        
        

The following change to the unsafeAdd() method would push failures to compile time
- This still isn't going to work, because List<String> isn't the same as List<Object>. 
- However, it fails at compile time, preventing issues from propogating to users. 

    EX:
    
        private static void unsafeAdd(List<Object> list, Object object) {
            list.add(object);
        }
        
        
### HOW TO HANDLE CASES WHEN TYPE ISN'T KNOWN UNTIL RUNTIME
Sometimes, we need to be able to have flexibility in collections. 

The old way of accomplishing this used Raw Types (and it suffers from 
all of the downsides we mentioned above. 
- no TypeSafety!)

    
    EX
        static int numElemsInCommon(Set set1, Set set2) {
            int result = 0;
            for (Object object : set1) {
                if (set2.contains(object)) {
                    result++;
                }
            }
            return result;
        }
        
... Instead use UNBOUNDED WILDCARD TYPES, which are the "most 
general parameterized type of collections, capable of holding ANY type of collection"
- The important concept is that it is getting you TYPE SAFETY
- you can't put any element(other than NULL) into a Collection<?>  
    - If you need more flexibility than provided within these restrictions use:
        - BOUNDED WILDCARD TYPES
        - GENERIC METHODS

    EX:
    
        Set<?>      Set of some type   (ACTUAL TYPE PARAMETER IS ?)
        
        static int numElemsInCommon(Set<?> set1, Set<?> set2) { ... }
        

