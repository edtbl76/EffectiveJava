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
    - fewer states -> fewer errors/problems

IMMUTABLE OBJECTS ARE SIMPLE
- immutable objects can be in ONE state, that which they were in when they were
created. 
- if class constructors establish class invariants, then it is guaranteed that
these invariants will remain true for the life of the created instance

IMMUTABLE OBJECTS ARE INHERENTLY THREAD SAFE
- there can be no multi-thread corruption through concurrent modifications if
the objects can't be mutated
- THIS IS THE EASIEST APPROACH TO THREAD-SAFETY

IMMUTABLE OBJECTS CAN BE SHARED FREELY
- since they can't be changed (by client or by other threads), it is recommended to
reuse existing instances whenever possible. 
- this includes the OBJECT's INTERNALS

YOU NEVER HAVE TO MAKE DEFENSIVE COPIES
- since the object always has a single state, you never have to many any copy 
whatsoever
    - no need to implement clone() or COPY CONSTRUCTOR
    
IMMUTABLE OBJECTS ARE A STABLE FOUNDATION FOR OTHER OBJECTS
- it is easier to maintain invariants of complex objects or interactions when
you know that the components beneath it are stable
- EX: Map keys/Set Elements
    - these structures rely on immutability for their own invariants. 
    
IMMUTABLE OBJECTS PROVIDE FAILURE ATOMICITY
- since the state never changes, temporary inconsistency is impossible

## DISADVANTAGE
The only major disadvantage of immutable objects is that they require a 
separate object for each distinct value. 
- this is expensive if the objects are large. 
    - If I have two objects that store a LONG, and one of them is Long.MAX_VALUE and
    the other is Long.MAX_VALUE - 1, we are storing a considerably large amount of
    information for an incredibly small difference. 
    - (In this example, it might be easier to store an offset....)
- this problem is magnified if you have a multistep/complex operation that
is required to generate a new object at every step
    - especially discarding objects after each step. 
    
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

### RELAXING FINAL MODIFIER FOR PERFORMANCE OPTIMIZATION (LAZY INIT)
It isn't necessary to make all fields FINAL in order to prevent them from being able to 
produce an EXTERNALLY VISIBLE change in state. 

LAZY INITIALIZATION <br> 
- Many immutable objects support lazy initialization, such that they cache the results of 
expensive operations the first time they are needed.
    - if the same value is required, it is returned from the cache as opposed to being calculated
    every time. 
    - this requires the field to be NONFINAL. 
    
 
    

## APPROACHES (FUNCTIONAL PROGRAMMING & IMMUTABILITY)
FUNCTIONAL <br> 
In a functional approach, rather than modifying instance, you create a return a new
instance. 
- methods return the result of applying a function to their operand w/o modifying it
- method names are typically PLUS, MINUS (prepositions), insinuating that they 
don't change states. 

NOTE: The Functional approach, by design, supports immutability. 


PROCEDURAL/IMPERATIVE <br>
- methods apply a procedure to their operand, causing state to change. 
- methods names are typically ADD, SUBTRACT (verbs), insinuating that they DO 
change the states and fields. 

### BEST PRACTICES
Since immutable objects can be shared freely, it is recommended to reuse existing 
instances wherever possible:
- provide public static final constants for commonly used values
- if you are going to use a constructor:
    - it should create a FULLY initialized object w/ ALL of the invariants established. 
    - avoid public init methods besides constructors (or static factories) unless absolutely
    necesssary.
    - avoid re-init methods that treat an object as thought it were created w/ a different 
    intial state. 


    EX:
        public static final Complex ZERO = new Complex(0, 0);
        public static final Complex ONE = new Complex(1, 0);
        public static final Complex I = new Complex(0, 1);
        

- (even better) USE STATIC FACTORIES that cache frequently requested instances
to avoid creating new instances when existing ones would do. 
    - this is what all boxed primitive classes (and BigInteger) do. 
    - results in clients SHARING instances rather than creating new ones
        - reduced memory footprint + GC costs
    - using STATIC FACTORY vs. CONSTRUCTOR allows you to add caching later w/o
    modifying client code.
    - EFFECTIVELY FINAL to external code because its impossible to extend a class that
    comes from another pkg w/o a PUBLIC/PROTECTED CONSTRUCTOR
    - supports flexibility in future releases by improving object-caching support for
    static factories.
- as a general rule, ALL CLASSES SHOULD BE IMMUTABLE UNLESS THERE IS A GOOD REASON TO MAKE
THEM MUTABLE
    - if this is impractical, then make the class as IMMUTABLE as possible. 
- All small VALUE OBJECTS should be immutable
- you should only provide a public mutable COMPANION CLASS for an IMMUTABLE CLASS if it has been 
confirmed NECESSARY to achieve satisfactory performance. 
- declare every field "PRIVATE FINAL" unless there is a good reason to do otherwise

    
### SOLVING PERF. ISSUES DUE TO DISADVANTAGES
1. guess which multistep operations will be commonly required and provide them as 
primitives
    - if multistep operations are provided this way, then the IMMUTABLE object
    doesn't have to create a separate object at each step.
1. Alternatively, you can create a PKG-PRIVATE internal COMPANION CLASS that
will speed up multistep operations
    - EX BigInteger does this for MODULAR EXPONENTIATION
    - This only works if you can accurately predict which complex operations that
    clients will want to perform against your IMMUTABLE CLASS
1. If that doesn't work, the only option is to provide a PUBLIC mutable 
companion class. 
    - String does this w/ StringBuilder
1. relax the FINAL keyword if and only if you need to use LAZY INITIALIZATION to defer
expensive calculations or intializations until first use, then to be cached for future use.
1. If are implementing SERIALIZABLE in an IMMUTABLE CLASS w/ any fields that refer to MUTABLE objects, 
you must provide
    - an explicit "readObject" or "readResolve"  method <br> 
        OR <br>
    - use the ObjectOutputStream.writeUnshared() and ObjectInputStream.writeUnshared() methods. 
    
