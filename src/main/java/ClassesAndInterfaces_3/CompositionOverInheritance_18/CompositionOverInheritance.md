# Item 18: Prefer composition over inheritance

## TYPES OF INHERITANCE
IMPLEMENTATION INHERITANCE:
- when one class extends another

INTERFACE INHERITANCE: 
- when a class impls an interface or one interface extends another


## INHERITANCE VIOLATES ENCAPSULATION, METHOD INVOCATION DOES NOT
- subclasses depend on the impl details of the superclass.
    - changes in superclass can break subclass without even touching the code.
        - this results in brittle/fragile subclasses
        - introduces security flaws
- many classes have a SELF-USE implementation which makes extending difficult
    - (Ex: the use of addAll() in HashSet is built on top of add())
    - difficult
    - time-consuming
    - error-prone
    - performance reducing
    
### AVOIDING FRAGILITY BY AVOIDING OVERRIDING
this is safer, but you can run into compilation issues and collisions
- If you create a subclass method w/ the same signature and return type as a superclass
method
- likely to create indirection surrounding contracts (i.e. subclass methods that
predate superclass methods w/ the same name)


## OBJECT COMPOSITION
creation of a private field that references an instance of an existing class. 
- existing class becomes a component of the new class. 
- each instance method in new class invokes corresponding in the contained instance and
returns the results

FORWARDING: <br> 
an attribute of COMPOSITION, such that the methods of a composed class are invoked by
the container class. 

FORWARDING METHODS: <br. 
methods of a container class that exist for the sole purpose of invoking methods of
a composition class. 

WRAPPER: <br>
(AKA DECORATOR). Wrapper classes can be implementations of the Decorator design pattern.
Typically, wrappers implement another type of class by Composition for the purposes of
adding functionality. When they DO add functionality, they are decorators. 

DELEGATION:<br>
A common reference to the combination of COMPOSITION and FORWARDING.

### BENEFITS OF COMPOSITION
FLEXIBILITY
- no dependencies on impl details of existing class
- adding new methods has no impact on new class
- can support many different implementations
    - (inheritance only supports a single concrete class)
- only requires a single constructor 
    - (inheritance requires a separate constructor for each supported
    constructor in superclass)

Composition/Forwarding classes allow us to transform one type of object into another,
adding new functionality

### DISADVANTAGES OF WRAPPERS
- not well-suited for CALLBACK FRAMEWORKS 
    - (where objects pass self-references to other objects for subsequent invocations)
    - "THE SELF PROBLEM"
        - wrapped objects don't know about their wrapper, so it passes a 
        reference to itself (via this) and callbacks "elude" the wrapper.
        
## WHEN IS INHERITANCE APPROPRIATE
When the subclass is really a SUBTYPE of the superclass. 
- Class Z should extend class A, if and only if, there is an "IS-A" relationship
between the two classes. 

Otherwise (if composition is more appropriate), the side effects of using 
inheritance are:
- needlessly exposing implementation details
- clients have direct access to internals
- indirection
- increases surface area of corner cases and loopholes for invariants. 
- inheritance carries any flaws with it to its subclasses, composition can block these.
