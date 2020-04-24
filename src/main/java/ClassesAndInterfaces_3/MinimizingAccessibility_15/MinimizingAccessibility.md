# Item 15: Minimize the accessibility of classes and members
"The single most important factor that distinguishes a well-defined 
component from a poorly designed one is the degree to which the component
hides its internal data and other impl details from other components"

## INVARIANT
a property in mathematics (and/or) computer science that remains unchanged. 

## ENCAPSULATION (INFORMATION HIDING)
The impl details are separated from the interface
- DECOUPLES components that build up a system
- allows flexible isolated operations
    - development
    - test
    - optimization
    - usage
    - understanding
    - modifications
- isolation enables parallel development
- debugging/support is faster, because individual components are
easier to understand.
- simplifies optimization, because tuning of one component will not
impact the CORRECTNESS of another. 
- decreases risk 

### ACCESS CONTROL (CLASSES/INTERFACES)
Specifies ACCESSIBILITY of classes, interfaces  
1. top-level/non-nested classes
    - PUBLIC = "exported API"
        - Once it is public, you are "obligated to support it forever
        to maintain compatibility"
    - PKG PRIVATE = "part of the implementation"
        - can be modified, replaced, removed altogether w/o 
        impacting clients. 
1. if PKG-PRIVATE top level class/interface is used by only ONE class
    - consider making it a private static nested class of that ONE class
    - reduces accessibility from all the classes in the package to the
    ONE class actually using it.

### ACCESS CONTROL (Members)
Here, members are defined as 
- fields
- methods
- nested classes/interfaces

PRIVATE: <br>
accessible only from the TOP-LEVEL CLASS where it is declared

PACKAGE-PRIVATE: <br>
(a.k.a. PACKAGE ACCESS, or DEFAULT ACCESS) <br>
Accessible from ANY CLASS in the package where it is declared. This is the
access level achieve when no ACCESS MODIFIER is specified (with the 
exception of interfaces which are public by default)

PROTECTED: <br>
Accessible from subclasses of the class where it is declared AND from any 
class in the package where it is declared.

PUBLIC: <br>
No holds barred. 



#### WHICH TO CHOOSE (API + IMPLEMENTATION)
1. (API)    Design the public API for the class. 
2. (IMPL) all other members should be private. 
3. (IMPL) only if another class in the same package REALLY needs access to a member
should PRIVATE be removed. 
    - first choice should be PACKAGE access
    - NOTE Package Private fields can "leak" into the API if it impls Serializable
4. (BACK TO API) Going from Package -> Protected is the big leap, because these fields represent 
a "Public commitment to an implementation detail" 

## BEST PRACTICES
1. make each class or member as inaccessible as possible 
1. if a method overrides a superclass method, it CANNOT have a more restrictive access level 
in the subclass than in the superclass.
    - per LISKOV SUBSTITUTION PRINCIPLE, this ensures that the instances of the subclass is usable anywhere that an
    instance of the superclass is usable.
    - this is a COMPILE-TIME requirement (i.e. you can't build if you violate it.)
1. instance fields of public classes should rarely be public
    - if instances fields are NONFINAL or are references to mutable object, making them public prevents limiting the
    values that can be stored by that field. 
    - sacrifice the ability to enforce invariants involving the field
1. public mutable fields are not generally thread-safe
    - we sacrifice the ability to take action when a field is modified
    - NOTE: even PUBLIC final (immutable) objects give up the flexibility
    to switch to a new internal data representation in which the field
    doesn't exist. 
1. it is considered WRONG for a class to have a public static final 
array field (or an accessor that returns such a field)
    - NONZERO-length arrays are ALWAYS mutable
    - NOTE: This is a very common security hole. 
    
    
    public static final thing[] VALUES = {...};
        

SOLUTION 1:  <br>
Make the member private and create a public immutable List object

    private static final thing[] PRIVATE_VALUES = { ... };
    public static final List<Thing> VALUES = 
        Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
        
SOLUTION 2: <br>
Make array private and create a method that returns a copy of it. 

    private static final Thing[] PRIVATE_VALUES = { ... };
    public static final List<Thing> values() {
        return PRIVATE_VALUES.clone();
    }
    
HOW TO CHOOSE: <br> 
- what is the client likely to do w/ the result ? 
- which return type is more convenient ? 
- which will give better performance ?

## MODULE SYSTEM
(Java 9) introduces the module system which adds 2 further implicit
levels of access. 

MODULE: <br> 
a grouping of packages, such that each package is a grouping of classes. The 
purpose of the MODULE SYSTEM is to share classes among packages without making
them visible to the entire world. 
- modules may EXPLICITLY export some of its packages via EXPORT DECLARATIONS
in its MODULE DECLARATION
    - module-info.java
    - export declarations have no impact on accessibility within the module
- PUBLIC and PROTECTED members of UNEXPORTED packages in a module are
accessible outside that module. 

NOTE: the need for this sharing is considered rare, and can usually be
eliminated by rearranging the classes inside the packages. 
- personally this de-complicates things in my opinion.
