# Item 17: Minimize Mutability

IMMUTABLE CLASS: <br> 
a class whose instances can't be modified. All of the info contained in 
each instance is fixed for the lifetime of the object
- NO CHANGES.

IMMUTABLE CLASSES IN JAVA PLATFORM LIBS:
- String
- BigInteger
- BigDecimal

## BENEFITS
- easier to design
- easier to implement
- easier to use
- less error-prone

## HOW TO MAKE A CLASS IMMUTABLE
1. Don't provide mutators (methods that modify object's state)
1. ensure that the class can't be extended
    - prevents accidental mutation from subclasses
1. make all fields final
    - (this is also necessary to ensure correct behavior of a ref
    to a newly created instance is passed from one thread to another
    w/o synchronization as spelled out in the MEMORY MODEL)
1. make all fields private
    - prevents client code from accessing mutable objects referred to
    by the fields, and modifying those objects directly
1. ensure exclusive access to any mutable components
    - for any field that refers to mutable objects: 
        - no clients of the class should be able to obtain refs of these objects
        - never initialize a field to a client-provided object reference or
        return the field from an accessor
        - make DEFENSIVE COPIES in
            - constructors
            - accessors
            - readObject
    
    
NOTE: (item 3 above) It is "technically" ok for immutable classes to 
have public final fields w/ primitive values OR refs to immutable objects,
it is NOT recommended: 
- it violates "Minimizing Accessibility and Using Accessors" appropriately
- it prevents changing internal representation at a later time.