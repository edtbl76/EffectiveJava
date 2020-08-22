# Item 90: Consider Serialization proxies instead of serialized instances

- Serialization SUCKS
    - error prone
    - insecure
    
## Serialization Proxy Pattern
- reduces risks of serialization

### Pattern
- 1 (Serialization Proxy) design private static nested class that concisely represents logical 
state of an instance of the enclosing class
    - single constructor
        - parameter type = enclosing class
        - copies data from argument
            - no consistency checking
            - no defensive copy
    - enclosing class AND proxy must impl Serializable
- 2 add writeReplace() method to the enclosiing class
    - translates an instance of the enclosing class to its
    serialization proxy prior to serialization
    - prevents serialization system from generating a serialized
    instance of enclosing class
        - (but an attacker can create one to violate class invariants)
    
   
    EX:
    
        private Object writeReplace() {
            return new SerializationProxy(this);
        }      
        
- 3 add readObject() method to enclosing class
    - protects class from writeReplace() attack
    
    
    EX:
        private void readObject(ObjectInputStream stream) 
                    throws InvalidObjectException {
                throw new InvalidObjectException("Proxy required");
        }
- 4 add readResolve() method
    - creates an instance of enclosing class using ONLY the public API
    
    
    EX:
        private Object readResolve() {
            // uses public constructor
            return new Period(start, end);
        }
    
### Value of the Pattern
- eliminates "extralinguistic" characteristics of serialization
    - instance is created using
        - same constructors, static factories, methods as any other
        instance
    - free from separately ensuring that deserialized instances obey
    class invariants. 
    - if the class's static factories/constructors establish invariants and
    the instance methods maintain them
        - then serialization will to