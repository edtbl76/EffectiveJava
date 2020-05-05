# Item 29: Favor Generic Types

BASIC USAGE:
- use generic types/methods provided by JDK and third party libraries

ADVANCED USAGE:
- writing your own generic types.


## GENERIFYING NON-PARAMETERIZED CLASSES
Non-parameterized classes require that clinets have to cast objects
returned by the methods of the class. 
- any of those casts can (will) fail at runtime. 

### STEPS
1. add 1(or more) type parameters to the declaration
    - conventional name for a type parameter used as an element of 
    a collection is "E"
2. replace all uses of the type Object w/ the type parameter
3. try to compile
    - might not work!
4. If it fails, you need to play whack-a-mole with fatal errors
(using the best practices we are establishing in Section 4 on 
Generics!)

### (See GenericTry Examples)
Solution 1 (PREFERRED)
- more readable 
    - because an array of type E, clearly indicates that
it contains only E instances
- more concise 
    - there is only a single cast (when the array is created)
- causes HEAP POLLUTION
    - runtime type of array doesn't match compile-time type (unless
    E happens to be Object)
    - typically harmless
    
Solution 2
- less concise
    - requires a separate cast every time an element is read.
- often chosen by devs who are concerned about HEAP POLLUTION

## WHEN TO CHOOSE ARRAYS
- Java doesn't support lists natively, so some generic types must be 
implemented atop arrays
    - EX: ArrayList
- PERFORMANCE
    - Sets, HashMaps use arrays due to the perf. advantages