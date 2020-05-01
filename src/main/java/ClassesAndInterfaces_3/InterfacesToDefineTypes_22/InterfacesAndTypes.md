# Item 22: Use Interfaces Only To define types
When a class implements an interface, the interface serves as a TYPE that can be
used to refer to instances of the class. 
- classes that impl interfaces should say something about what a client can DO w/
those instances. 


## ANTIPATTERNS

### CONSTANT INTERFACE
- interface that consists only of static final fields, each exporting a constant
    - no methods.
    
This is usually done to avoid associating a class name w/ constants. However, the
internal use of a constant is an IMPL DETAIL and it doesn't belong in interfaces. 
- WORSE -> it represents a contract, which must be fulfilled forever in order to 
remain backwards compatible.




## BEST PRACTICES
Avoid using CONSTANT INTERFACES (i.e. interfaces should only be used to define a type)
- if a constant is strongly tied to an existing class/interface, then keep them there. 
    - but don't justify creating a new interface just to hold the constants
- are the constants better defined as members of an enumerated type? 
    - use ENUMS!
- If none of the above works, use a NONINSTANTIABLE UTILITY CLASS
    - remember that the constructor needs to be private to avoid instantiating the utility
    class
    - also remember that it is often easier to read to use STATIC IMPORTS to avoid
    qualifying constants w/ a class.
