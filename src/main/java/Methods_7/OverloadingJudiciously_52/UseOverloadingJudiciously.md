# Item 52: User Overloading Judiciously

## (see Overloading.CollectionClassifier) 
- The code demonstrates an OVERLOADED classify() method and a 
main class that appears to use classify() to identify a Set, List
and Unkown Collection respectively. 

- However, the actual output is Unknown Collection x 3. 

### WHY? 
- choice of which overloaded method to invoke is made at COMPILE TIME
- even though the runtime uses different types for each iteration
of the for loop in the code, the compile-time types are always the
same -> Collection<?>. 

## IMPORTANT RULE (Overloading vs. Overridding)
- Selection from OVERLOADED methods is STATIC
    - Static happens at COMPILE-TIME. 
- Selection from OVERRIDDEN methods is DYNAMIC
    - Dynamic happens at RUNTIME. 

(If you want to see an Example. run Overriding.Client)
- this has a Wine -> SparkingWine -> Champagne hierarchy that
is iterated through, demonstrating the dynamic nature of 
OVERRIDDEN methods.

OVERRIDE NOTE: Compile-Time type has NO IMPACT on which method is executed
- the most SPECIFIC overriding method always gets executed. 

OVERLOAD NOTE: Runtime type has NO IMPACT on which overload is executed
- the selection is made based on the COMPILE-TIME type of the parameters

## Workaround for CollectionClassifier Problem
- There is no way to make overloading do what you want, so we have to find a different hammer. 
    - use a single classify() method that uses instance of to determine type
    - see Overloading.BetterCollectionClassifier for the code
    
## Overload-By-Name 
A common way to avoid the limitations of overloading is to use a more specific naming
convention

    EX: 
    
        ObjectOutputStream class has a write() method that performs basic writes
        
        It doesn't use overloading, instead it renames methods for primitives: 
        
            - writeBoolean(boolean) 
            - writeInt(int) 
            - writeLong(long)
            - etc.
            
        TWO Additional Benefits
        - It is a clearer naming convention. We know EXACTLY what the method writes
        - we can also provide reads w/ corresponding names
        
            - readBoolean()
            - readInt()
            - readLong()
            - etc. 
            
### Constructors
Constructors are the exception to the rule
- they CANT be renamed, so multiple constructors are always overloaded. 
- WORKAROUND
    - You can use static factories instead of constructors, which can support overload-by-name

Constructors can't be overridden, so there are no concerns in terms of conflicts. 

## Constructor Overloadign Safety Measures
- when exporting overloadings w/ the same number of parameters it is recommended that we try to 
avoid confusion/obscurity between the overloads
    - It is helpful when one of the parameters is "radically different" between the overloadings
        - (this means that "it is clearly impossible to cast any
        non-null expression to both types)
        - this GUARANTEES that the overloading is fully determined at runtime, because the
        "radically different" parameters can't be confused or "matched" at compile-time
        
    - See AutoBoxing.SetList and AutoBoxing.SetListSolution to review
    how/why AutoBoxing presents a challenge 
        - Before Generics and AutoBoxing, the two remove() overloads of
        List were radically different. 
        - Afterwards, they were NO LONGER RADICALLY DIFFERENT. 
        - The point? 
            - adding two very cool and powerful features (Generics and Autoboxing)
            actually "broke" one of the most widely used interfaces in the 
            entire java language. 
            
    - prefer UNRELEATED classes
        - Two distinct classes are set to be UNRELATED if neither class isa  
        descendant of the other. 

## BEST PRACTICES
- avoid overloading when possible. 
    - STATIC type selection is simply not ideal based on demonstration aboves
- "never export 2 overloadings w/ the same number of parameters"
    = safe, conservative policy
- NEVER overload at all when using varargs
    - see Item 53 for the exception to the rule
- overload-by-name is a way to use naming variations to avoid overloading pitfalls
- do NOT overload methods to take different functional interfaces in 
the same argument position. 
    - (different functional interfaces are NOT radically different)


    
