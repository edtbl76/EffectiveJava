# Item 64: Refer to objects by their interfaces: 

## Interfaces vs. Concrete Classes
- (Item 51)
    - use interfaces rather than classes as parameter types
- (Generally)
    - If appropriate interface types exists, the following structures should
    always be declared using interface types
        - parameters
        - return values
        - variables
        - fields
        

    EX:
    
        YES
        
            Set<Son> sonSet = new LinkedHashSet<>();
        
        NO
        
            LinkedHashSet<Son> sonSet = new LinkedHashSet<>();
            
            
WHY:
- using interfaces, makes code more flexible. 
    - changing an impl only requires changing class name in 
    constructor (or different static factory)
    
    
    EX: 
    
        Set<Son> sonSet = new HashSet<>();
        
        
- Note that only the impl changed. The Return Type is still Set<Son>, 
which means any other code that uses this won't have to change.


### Downside
There is ONE downside of this approach. 
- some impls have special case code that isn't available in other 
impls. 
    - EX (looking at examples above), if code using Set<Son> relied
    on LinkedHashSet ordering policy, then the substitution of
    HashSet<> would break the code. 
         
 
### WHY CHANGE IMPL? 
Based on the down side, what are the reasons for swapping out impls? 
- performance boost
- feature/functionality provided by new impl that isn't present in
current impl. 


    EX: 
        HashMap -> EnumMap
        - better performance
        - iter order is consistent w/ natural order of
        the keys
        - key type has to be ENUM 
        
        HashMap -> LinkedHashMap
        - same perf
        - predictable iter order
        - NO special constraints on key type. 
        
## EXCEPTIONS

### When No Appropriate Interface Exists
- If no appropriate interface exists, then you must refer to an object
by a class. (3 examples below)

VALUE CLASSES
- most value classes (i.e. String, BigInteger) aren't written w/
multiple impls in mind. 
- usually final
- rarely have interfaces

CLASS-BASED FRAMEWORKS
- some frameworks use an abstract base class as a root
- in these situations, it is appropriate to refer to the abstract base
class in lieu of an appropriate interface. 

EXTRA FUNCTIONALITY
- there are cases where you may rely on a method found in classes that
impl the interface that aren't found in the interface. 
- EX:
    - PriorityQueue has a comparator() method that isn't provided by
    the Queue interface. 
- This is only appropriate if you REQUIRE the functionality in the
implementing class

### General Rule
"If there is no appropriate interface, just use the LEAST SPECIFIC
class in the class hierarchy that provides the required functionality"
