# Item 13: Override clone judiciously

## INTRO (WHAT CLONEABLE DOESN'T DO)
Initial intent of CLONEABLE interface was to provide a MIXIN interface 
for classes to advertise that they support cloning. 
- doesn't work, because there is no clone() method
- Object's clone() method is protected
    - you can't invoke it w/o resorting to reflection
    - this still might fail because there is no guarantee that the
    calling object has an accessible clone method.
    
CLONEABLE (AND SERIALIZABLE) are incompatible with the NORMAL use
of final fields referring to mutable objects. 
- EXCEPTION is when mutable objects may be safely shared between 
an object and its clone

    

Determines the behavior of Object's protected clone() method
- if a class impls Cloneable
    - Object's clone() method returns a FIELD-BY-FIELD copy of the 
    object
    - else, throws CloneNotSupportedException
    
NOTE: this is considered a special case for using interfaces <br>
NORMAL INTERFACE:
- defines a contract about what a class can/should do for its clients 

CLONEABLE:
- modifies the behavior of a protected method on a superclass. 

## (IMPLIED) GENERAL CONTRACT
- a class implementing the Cloneable interface is expected to 
provide a properly functioning public clone method 

- clone() should create and return a copy of the calling object
    - "copy" depends on the class of the object
    
1. For any object x, 
    1. x.clone() != x
        - should be TRUE
    1. x.clone().getClass() == x.getClass() 
        - should be TRUE, but it is not an absolute requirement
    1. x.clone().equals(x)
        - should be TRUE, but it is not an absolute requirement
1. By convention, the object returned by the method should be
obtained by calling super.clone()
    - if classes (and all superclasses besides Object) do this, then
    case ii above will be true.
1. By convention, the returned object should be INDEPENDENT of 
the object being cloned
    - It MAY BE necessary to modify 1+ fields of object returned
    by super.clone before returning it.
    - NOT WELL ENFORCED: 
        - if clone() returns an instance that is obtained by calling a
        constructor instead of calling super.clone...
            - the resulting object has the wrong class
            - this breaks clone() for all subclasses
            - compiler doesn't complain
    - this can be safely ignored for final classes
        - (Final classes have no subclasses)

## CONS
- the implementing class (and ALL of it superclasses), must obey 
the GENERAL CONTRACT
    - poorly documented
    - fragile, dangerous
    - EXTRALINGUISTIC
        - objects are created w/o calling a constructor

## BEST PRACTICES
- if your superclass doesn't provide a well-behaved clone() method, 
your subclass' clone() method will likewise not work. 

1. always call super.clone first.
    - this returns a FULLY FUNCTIONAL replica of the original
    - all fields in your class will be identical to the original
        - if fields are primitives or object references to immutable object
        references, it is likely that no further processing is required.
        - EXCEPTION: unique fields (i.e. UUID, SerialNumbers) may still need to be fixed 
        even if they are primitive or immutable
    
1. don't clone immutable classes
    - it's a wasteful copy. 
 
1. USE COVARIANT RETURN TYPES 
    - a class that implements Cloneable and overrides clone()
    should use itself as the return type rather than Object
        - it should also be a public method (this seems fairly obvious)
    - as long as the return type of the overriding method is a
    subclass of the overridden method's return type this will work
        - all classes are subclasses of Object
    - this prevents the need for clients to have to cast object
    types
    
1. Always surround super.clone w/ TRY-CATCH
    - Object.clone can throw a CHECKED EXCEPTION (CloneNotSupportedException)
        - this exception is only thrown if the class doesn't implement Cloneable
    - NOTE: this is a "boilerplate" error in the implementation of Object
        - if this had been an unchecked exception, we wouldn't have to surround it
        with try/catch
        
1. DO NO HARM TO ORIGINAL OBJECT
    - clone() should properly establish invariants on clone
    - See 'Handling Mutable Fields w/ Clone below'
    
1. public clone methods should OMIT the throws clause. 
    - overriding methods aren't required to throw CloneNotSupportedException
    - methods that don't throw checked exceptions are easier to use. 
   
1. forget all of this and just use COPY CONSTRUCTOR/FACTORY
    - On RARE occasions, Cloneable can be impl'd on final 
    classes as a performance optimization

### HANDLING MUTABLE FIELDS w/ CLONE (Fixing State in Cloned Objects)
If an object's field starts its life empty and is subject to state
changes, a field-by-field clone is going to ignore state and copy
that field from its base (empty) state.
- This is effectively behaving like a constructor which breaks the
weak-ass covenant of the clone() method.

1. For collections that have been populated w/ elements, the simplest
way to retain invariants is to recursively call clone() on each 
of the elements of the array

1. if clone() creates a shallow copy (i.e. if it contains object
references), you have to use a DEEP COPY
    - Do NOT use recursion in your deepCopy()
        - this is very slow/memory inefficient because it consumes
        one entire STACK FRAME for each element in the list. 
            - long lists can cause a StackOverflow
    - Do USE ITERATION
        - this is more memory efficient
        
1. for very complex mutable objects
    - call super.clone
    - set all fields in resulting object to initial state
    - call higher-level methods to regenerate/replay the state of the object
        - i.e, using a put() on the new array w/ a get() from the old array
            - should be final or private in order to preserve state 
    - NOT RECOMMENDED
        - blindly overwrites field-by-field
  
1. clone should NEVER invoke overridable methods on the clone being constructed
    - constructors shouldn't do this either.
    - if clone invokes a method that is overridden in a subclass, that method will execute before the
    subclass has had a chance to replay/regenerate state 
        - this can corrupt the clone and the original object. 
        

## ARRAYS AND CLONE
Arrays are the primary compelling use of clone()
1. calling clone on an array returns an array whose RUNTIME and 
COMPILE-TIME types are identical to those of the array being cloned
    - no explicit cast required

1. SIDE NOTE... using clone() is PREFERRED to duplicating an array.

## CLONING and DESIGNING CLASSES FOR INHERITANCE
NOTE: in any situation, the class should NOT implement Cloneable.

1. mimic the behavior of Object
    - implement a properly functioning clone() method that throws CloneNotSupportedException
    - gives subclasses the flexibility to implement Cloneable in the same manner if they were to extend
    'Object' directly
1. prevent subclasses from implementing clone by blocking subclasses using a DEGENERATE clone() implementation

## CLONING and THREAD-SAFETY
Clone isn't thread-safe by default
- if thread safety is required, then any clone method implemented via Cloneable should be properly SYNCHRONIZED


## COPY CONSTRUCTOR/COPY FACTORY > CLONE
Copy Constructors/Factories are simple constructor/factories that take a single argument whose class type is
the class containing the constructor

COPY CONSTRUCTOR:

    
    EX: public MyClass(MyClass myClass) { ... };
    
    
COPY FACTORY

    EX: public static MyClass newInstance(MyClass myClass) { ... };
    
### ADVANTAGES
- doesn't rely on risk-prone EXTRALINGUISTIC object creation
- doesn't depend on weak contract
- doesn't conflict w/ proper use of final fields
- doesn't throw unnecessary checked exceptions
- don't require casting
- can take an argument whose type is an interface implemented by 
the class. 
    - EX: all general-purpose collection impls provide a constructor
    whose argument is of type Collection or Map. 
- can be a CONVERSION constructor/factory (see below)
    - allows client to choose impl type of the copy rather
    than forcing the client to accept the impl type of the
    original.
    - MUCH more flexible than clone, which can't do this
    
    
CONVERSION CONSTRUCTORS/CONVERSION FACTORIES: <br>
- interface-based copy constructors/factories