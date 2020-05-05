# Item 30: Favor Generic Methods

Methods can also be generic
- static utility methods that operate on parameterized types are usually generic


## EXAMPLE OF METHOD THAT USES RAW TYPES

    EX:
    
        public static Set union(Set set1, Set set2) {
            Set result = new HashSet(set1);
            result.addAll(set2);
            return result;
        }
        
    Method Compiles w/ Warnings
    
        Blah.java:lineNo: warning: [unchecked] unchecked call to
        HashSet(Collection<? extends E>) as a member of raw type HashSet
            Set result = new HashSet(set1);
                         ^
                         
        Blah.java:lineNo: warning [unchecked] unchecked call to 
        addAll(Collection<? extends E>) as a member of raw type Set
            result.addAll(set2);
                          ^
                          
## SOLVING RAW TYPE METHODS
TYPE PARAMETER LISTS
- declares type parameters
- part of method declaration between the return type and the access modifiers


    EX:
        public static <E> Set<E> union(Set<E> set1, Set<E> set2) {
            Set<E> result = new HashSet<>(set1);
            result.addAll(set2);
            return result;
        }
        
The provided example can be made more flexible by using BOUNDED WILDCARD TYPES. 
Currently the return type and both parameters must be identical.

## GENERIC METHODS w/ IMMUTABLE OBJECTS
Since generics are impl'd erasure, you can use a single object for all
required type parameterizations
- this would require a STATIC FACTORY METHOD to dole out object for each
type of parameterization.

### GENERIC SINGLETON FACTORY
(the aforementioned pattern). This is used for function objects
(i.e. Collections.reverseOrder())
        
THE IDENTITY SCENARIO   
- If we want to create an identity function 
    - Java provides Function.identity, so we don't need to write our own
    -  BUT, its very wasteful to create a new ID function each time one is 
    requested, because its stateless. 
    
- Since GENERICS are NOT reified, they don't need one ID function per type
    - therefore a GENERIC SINGLETON FACTORY is enough.
    
    
    EX:
    
    // GENERIC SINGLETON FACTORY PATTERN
    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;
    
    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }
    

NOTE: The cast of IDENTITY_FN -> UnaryOperator<T> generates an 
unchecked cast warning 
    - UnaryOperator<Object> isn't always UnaryOperator<T>
    - The ID function is unique, because it returns its arg
    unmodified, therefore it HAS to be type safe. 
    
    
### RECURSIVE TYPE BOUNDS
- A recursive type bound is a rare occasion for a TYPE PARAMETER
(in generics) to be bounded by some expression involving the 
type param itself. 
- This is common in the COMPARABLE interface
    - as the ex. shows, the type parameter (T) defines the type to
    which elements of the type IMPLEMENTATING Comparable<T> can be
    compared. 

    EX: 
        
        public interface Comparable<T> {    
            int compareTo(T o);
        }
        
- Nearly all types can be compared only to elements of their own type
    - EX: 
        - String impls Comparable<String>
        - Integer impls Comparable<Integer>
        - etc. 
        
MUTUALLY COMPARABLE ELEMENTS
- Extending the concept introduced above, many methods will
accept a Collection of elems implementing Comparable to perform
one or more tasks. 
- in order to accomplish those task, every element in the 
collection must be COMPARABLE to every other element. 


    EX:
        An Example of a Recursive Type Bound to express
        MUTUAL COMPARABILITY
        
        public static <E extends Comparable<E>> E max(Collection<E> c);
        
        TYPE BOUND:
            <E extends Comparable<E>>   "any type E that may be compared to 
                                        itself"
                                        
       
