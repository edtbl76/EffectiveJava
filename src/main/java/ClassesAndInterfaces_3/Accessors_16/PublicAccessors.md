# ITEM 16: In public classes, use accessor methods, not public fields
Accessing fields directly violate the OOP principle of ENCAPSULATION

## SIDE EFFECTS OF VIOLATING ENCAPSULATION
1. cannot change representation w/o changing API
1. can't enforce invariants
1. can't take auxiliary action when a field is accessed. 

## JAVA LIB EXAMPLES WHERE ENCAPSULATION IS VIOLATED
- java.awt.Point, java.awt.Dimension

## BEST PRACTICES
1. if a class is accessible outside its package, provide accessors 
    1. use ACCESSORS (getters) to get field data 
    1. use MUTATORS (setters) to initialize or modify fields
    - (Provides flexibilty to change class's internal representation)
1. if a package is PACKAGE-PRIVATE or a PRIVATE NESTED CLASS, there is
nothing wrong w/ exposing its data fields
    - assumes that the class does an adequate job of describing 
    abstraction provided by the class. 
    - less visual clutter and indirection
    - this code will be tied to the package that contains the class (PKG
    PRIVATE) or the enclosing class (PRIVATE NESTED CLASS)
        - any required/desired changes in representation can be made
        w/o impacting code external to the package
1. While it is considered bad form for fields to be directly exposed...
    - it is technically less of an issue w/ immutable fields because the
    side effects induced through the violation of encapsulation are less
    of an issue
        - you STILL can't change representation w/o changing API
        - you STILL can't take aux actions when a field is accessed
        - you CAN enforce invariants