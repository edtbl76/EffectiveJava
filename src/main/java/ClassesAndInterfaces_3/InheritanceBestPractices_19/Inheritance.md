# Item 19: Design and document for inheritance or else prohibit it.

## CLASS MUST DOCUMENT ITS SELF USE OF OVERRIDABLE METHODS
Each Public/Protected method should indicate
- which overridable methods it invokes
- the sequence of invocation
- how results of each invocation impact subsequent processing.
- where those invocations might originate
    - static initializers? 
    - bg threads? 
    
This should be in a special section of the API specification labeled
"Implementation Requirements".
- (Generated using @implSpec javadoc tag)


## PREVENTING PAIN FOR DEVS
A class may have to provide hooks into its internal workings in the form of judiciously
chosen protected methods (or in RARE INSTANCES, protected fields)
- An example from Java Platform Library is the removeRange() method in List.
    - it provides a performance optimization for using the clear() method which is
    quadratic b ydefault. 
    
## TESTING FOR INHERITANCE
The only way to test a class designed for inheritance is to write subclasses

## WORDS OF CAUTION
 If you are designing for inheritance, you are commiting FOREVER to the self-use
patterns that you document. 
- You must TEST TEST TEST before releasing it. 

Constructors should NEVER invoke overridable methods directly or indirectly
- superclass constructor always runs before subclass constructor
- any overriding methods int eh subclass will get invoked before the subclass constructor 
has run

It is NOT recommended to design classes for inheritance if the class will
implement Cloneable or Serializable
- clone() and readObject() methods behave similarly to constructors and they are
    treated as such
    - they should NEVER invoke an overridable method (directly or indirectly)
    - NOTE: in the case of clone(), program failures in this situation can damage
    the original object 
        - i.e. if the overriding method assumes it is modifying clone's copy of
        object's DEEP structure before the copy has been created.
- when using Serializable you must make readResolve() and writeReplace() methods 
protected rather than private
    - this prevents them from being ignored by subclasses. 
    - this leaks this functionality into the API
    
    
## BEST PRACTICES
SKELETAL IMPLEMENTATIONS
- abstract classes/interfaces

prohibit subclassing in classes that are not designed and documented to be safely
subclassed
1. declare the class final
1. make all constructors private or default(package-private)
    - requires the use of public STATIC FACTORIES to build the objects. 
   
AUGMENT through Decorator/Wrapper rather than through subclasses. 
   


