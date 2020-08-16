# Item 61: Prefer primitive types to boxed primitives

# Type System

## Primitives
- int
- short
- byte
- long
- char
- double
- float
- boolean

## Reference Type
- String - List

### Boxed Primitive
These are reference types that correspond to primitives
- Int
- Short
- Byte
- Long
- Char
- Double
- Float
- Boolean

### Autoboxing/Auto-unboxing
- blurs, but does not erase the distinction between
boxed/non-boxed primitives (Item 6)

- autoboxing reduces the VERBOSITY of boxed primitives but not the DANGER

## Differences between primitives and boxed primitives
1. values and identities
    - primitives have values only
    - boxed primitives have identities distinct from their values
    - (2 boxed primitives can have the same value, but different
    identities)
2. functional and non-functional values
    - primitives have functional values only 
    - boxed primtivies support a non-functional value (NULL) in addition to their functional values
    (which correspond to the values supported by their associated primitive)
3. Temporal and Spatial Complexity
    - primitives (due to their simplicity) are more time and space efficient than 
    boxed primitives.
    
### Problem 1 - The broken comparator
- Comparator.compare() returns: 
    - a negative number if the first argument is less than the second
    - zero if the first and second arguments are equal
    - a positive number if the first argument is greater than the second
    
    
    EX:
    
        Comparator<Integer> naturalOrder = 
            (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
            
            
IT WORKS IN SOME CASES
- This will work fine w/ something like Collections.sort() 

IT BREAKS IN MOST CASES <--- "Deeply Flawed"

    EX: 
        naturalOrder.compare(new Integer(42), new Integer(42));
        
        EXPECTED VALUE: 0   (i.e. the values are equal) 
        
        ACTUAL VALUE: 1     (indicates that the first integer > second integer)
        
- first test (i < j)
    - values are auto-unboxed from Integer to int
    - this works fine. 
        - given our inputs, the result is false, so we move to the latter portion of the ternary 
        operator
- second test (i == j) ? 0 : 1
    - this is NOT unboxed, because '==' is a valid comparison for references. Rather than testing 
    value equivalence, it performs an IDENTITY COMPARISON on the two object references
    - although the two objects have the same value, they have a different identity, so this
    test evaluates to FALSE. 
        - this means we return the latter porition of the ternary operator -> 1. 
        
#### Natural Ordering Solution
1. The easiest solution is not to write the comparator yourself. 
    - USE Comparator.naturalOrder()
2. If you are going to write the comparator yourself, (silly!) 
    - add a layer of indirection that performs the auto-unboxing prior to using the variables in 
    the ternary operators. This ensures that the ternary operator is only using primitives and not
    their boxed counterparts. 
    
    
        EX: 
        
            Comparator<Integer> naturalOrder = (iBoxed, jBoxed) -> {
                int i = iBoxed, j = jBoxed; // Auto-Unboxing happens here
                return i < j ? -1 : (i == j ? 0 : 1);
            };    
            
### Problem 2 - Mixing Wrappers and Primitives (Wrappers are auto-unboxed)
This is the case of the Phantom NPE


    EX: 
    
        public class Unbelievable {
            static Integer i;
            
            public static void main(String[] args) {
                if (i == 42) {
                    System.out.println("Unbelievable");
                }
            }
        }
        
        
        EXPECTED RESULT: nothing 
        
        ACTUAL RESULT: NullPointerException
        

What Happened?         
- Integer i is set to null. 
- i == 42 is a mixed statement since i is a Wrapper and 42 is a primitive. 
    - in mixed statements, wrappers are auto-unboxed. 
- since i was never initialized, the attempt to unbox it results in an NPE

#### Solution
- Don't leave it up to the compiler!!! 
- There's no advantage to using Integer in the example, just use an int. 


    EX: 
           
        public class Unbelievable {
            static int i;
            
            public static void main(String[] args) {
                if (i == 42) {
                    System.out.println("Unbelievable");
                }
            }
        }
           
           
### Problem 3 - Slowpoke


    EX: 
    
        public static void main(String[] args) {
            Long sum = 0L;
            for (long i = 0; i < Integer.MAX_VALUE;i++) {
                sum += i;
            }
            System.out.println(sum);
        }    

WHY IS THIS SLOW? 
- sum is a local variable that was declared as a boxed type 
- the loop variable (i) is a primitive
    - as we recall, when mixing primitives and wrappers, the wrapper will be auto-boxed. 
    - this means that 'sum' is going to be boxed and auto-boed on EVERY iteration. 
    - note, we are iterating Integer.MAX_VALUE times
    - that is a LOT of overhead. 
            

## WHEN TO USE BOXED PRIMITIVES
- Collections
    - primitives can NOT be used in Collections
    - use boxed prims as ELEMENTS, KEYS and VALUES
- Generics
    - primtives not supported. 
    - boxed prims MUST be used as type parameters in parameterized types and methods
        - Ex: ThreadLocal<Integer>