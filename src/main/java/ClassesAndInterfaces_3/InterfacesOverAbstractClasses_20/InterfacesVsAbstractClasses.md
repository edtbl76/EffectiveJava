# Item 20: Prefer interfaces to abstract classes

## INTERFACES vs. ABSTRACT CLASSES

INTERFACES:
- can provide default impl using DEFAULT keyword (Java 8)
- supports multiple inheritance
- existing classes are much easier to be retrofitted to interfaces than ABC
    - add impl clause
    - add required methods


ABSTRACT CLASSES
- supports default impl by removing ABSTRACT keyword from signature
- implementation requires inheritance (subclassing)
    - single inheritance
- challenging to be retrofitted (if possible at all)
    - breaks class hierarchy
    
### INTERFACES ARE IDEAL FOR MIXINS
MIXIN: <br>
a type that a class can implement in addition to its primary type for the purpose of
declaring that it provides some optional behavior
- EX: Comparable is a mixin interface that allows a class to declare that its
instances are ordered w/ respect to other mutually Comparable objects. 

ABCS can't be mixins because they only support single inheritance

### INTERFACES ALLOW NON-HIERARCHICAL TYPE FRAMEWORKS
Interfaces are slightly more flexible w/ respect to types. ABCs are inheritance-based
and therefore rigidly bound to hierarchical constructs

COMBINATORIAL EXPLOSION: <br>
- bloated class hierarchies containing a separate class for every 
supported combination of attributes. 
    - for N attributes, there will be 2^N possible combinations
    - results in many methods that differ only in type of args because there are no types in
    the class hierarchy that can capture common behaviors
    
## INTERFACES enable SAFE POWERFUL FUNCTIONALITY ENHANCEMENTS
- interfaces support WRAPPER CLASSES (i.e. Decorator design pattern)
- ABCs can only add functionality through inheritance. 
    - more fragile/less powerful/flexible than decorators.

## DEFAULT METHODS
- When there is an obvious impl of an interface method, it makes sense to help bruthas
(and sistahs) out by providing a default implementation. 
    - NOTE: they should be documented with the @implSpec Javadoc tag.
    
    
## DISADVANTAGES OF INTERFACES
- can't contain instance fileds
- they don't support nonpublic static members 9other than private static methods)
- can't add default methods to interfaces you don't own.

## ABSTRACT SKELETON + INTERFACE
(This is the Template Method design pattern)<br>
This is combines the advantages of ABCs and Interfaces to overcome some of the 
disadvantages of interfaces
- interface defines type, provides SOME default methods
- SKELETAL IMPL impls remaining non-primitive interface methods on top of the
primitive interface methods. 

The advantage is that extending the skeletal impl takes most of the work out of
impl'ing an interface
- skeletal ABCs provide the IMPL help of ABCs w/o imposing the constraints introduced
by ABCs (or other classes) when used as type definitions.


### SIMPLE IMPLEMENTATION
A variation on a SKELETAL IMPL
- impls an interface
- designed for inheritance
- it isn't abstract, it is the simplest possible working implementation

### SIMULATED MULTIPLE INHERITANCE
In most cases, implementors of interfaces /w skeletal IMPLs will use the skeletal
implementation, however this is optional.
- you still have access to any of the default methods in the interface
- to gain access to the ABC, you use SIMULATED MULTIPLE INHERITANCE
    - class that implements interface forwards invocations of interface methods
    to a contained INSTANCE of a PRIVATE INNER CLASS that extends the SKELETAL
    IMPL
    - (this is a similar approach to using a decorator.)

## WRITING SKELETAL IMPLS
1. decide which methods in the interface are "primitives" in terms of 
which the others can be impl'd
    - these become the ABSTRACT METHODS in the SKELETAL IMPL
1. provide default methods in the interface for ALL methods that can be impl'd
directly on top of the primitives
    - you can't provide defaults for Object methods 
        - equals, hashCode, toString, clone
1. if primitives default methods fulfill the interface, you're done. 
    - there is no need for skeletal impl. 
1. ...else write a class declared to impl the interface
    - should implement all of the remaining interface methods
    - may contain nonpublic members as necessary


## BEST PRACTICES
- when implementing a SkeletalImplementation the standard naming convention is
    - [InterfaceName] -> the name of the interface 
    - Abstract[InterfaceName] -> Skeletal implementation that impls the interface
    
EX:

| Interface | Skeletal Implementation |
| --- | --- |
| Collection | AbstractCollection |
| Set | AbstractSet | 
| List | AbstractList | 
... and so on.

- good documentation is essential to skeletal implementation.

    
       
