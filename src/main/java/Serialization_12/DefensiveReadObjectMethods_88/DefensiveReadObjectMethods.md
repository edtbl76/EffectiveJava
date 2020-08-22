# Item 88: Write readObject methods defensively.

(see ImmutableClassWithDefensiveCopy.Period) for an example of an 
immutable class that uses defensive copying.

## Impact of readObject()
- If we attempt to make the referenced class above into a Serialized
class
    - we CAN use default serialized form, because the physical representation
    mirrors the logical representation
- readObject() has to be added
    - it must check args for validity (Item 49)
    - it must make defensive copies of params where appropriate (Item 50)

### Impossible Object
- readObject() is basically a constructor that takes a byte stream as its
only parameter
- an Impossible Object is an object that could never be created by
using a normal constructor
    - (Example: when we provide a byte stream that is artificially
    constructed to generate an object that violates the invariants
    of the class.)
- (See ImmutableClassWithDefensiveCopy.BogusPeriod) for an example
    
### Fixing Impossible Object problem.
- provide readObject() method that calls defaultReadObject()
- then have it check the validity of the deserialized object
    - throw InvalidObjectException if check fails
    
    
    EX:
        private void readObject(ObjectInputStream s)
                    throws IOException, ClassNotFoundException {
                s.defaultReadObject();
                
                // validity check
                if (start.compareTO(end) > 0) {
                    throw new InvalidObjectException(start + " after " + end);
                }
        }
PRO
- protects against invalid/artificial instance of object we want to 
serialize

CON
- still possible to create a mutable instance of the object we want to 
serialize
    - build a byte stream that begins w/ a valid instance and then
    append extra references to private internal fields.
    - attacker then has access to objects references by the
    extended private fields
        - this allows mutation of the instance.

### Defensive Copying with readObject()
- when an object is deserialized
    - it is critical to defensively copy any field containing an object ref that a client shouldn't possess
    
    
    EX:
        // readObject w/ defensive copying AND validity checking
        private void readObject(ObjectInputStream s) 
                    throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            
            // Defensive copy of mutable components
            start = new Date(start.getTime());
            end = new Date(end.getTIme());
            
            // Check invariants
            if (start.compareTO(end) > 0) {
                throw new InvalidObjectException(start + " after " + end);
            }
        }

- NOTE
    - protect serialized class against attack
        - don't use clone()
        - defensive copying MUST be performed BEFORE validity check
    - defensive copying is not possible for final fields
        - this means in some cases that final fields must be made nonfinal
        - (In the example, this means making 'start' and 'end' nonfinal.)
        
### readObject() and Overrides
- readObject() can't invoke overridable methods
    - if this is attempting, the overriding method will be executed before the subclass's state has
    been deserialized
        
### How to test if readObject() is acceptable
- would you allow adding a public constructor that 
    - took as params the values for each NONtransient field in the object
    - stored the values in the fields w/ no validation
- if NOT
    - provide a readObject()
    - (or) use Serialization Proxy Pattern (Item 90)
    
    