# Item 89: For instance control, prefer enum types to readResolve

## Singleton Example/Review

    EX:
        
        public class Singleton {
            public static final Singleton INSTANCE = new Singleton();
            private Singleton() { ... }
        }
        
- Singleton classes ensure one instance is created by restricting access
to the class' constructor

### Seralization + Singleton
- implements Serializable on a Singleton class prevents it from being
a Singleton
    - regardless of
        - default/custom serialized form (Item 87)
        - explicit readObject (Item 88)
            - (any readObject method returns a brand new instance)
            
### readResolve
- feature that allows the substitution another instance for the 
one created by readObject
- this method is executed on newly deserialized objects if the class
of the deserialized instance defines a proper readResolve() method
    - the newly created object is swapped out for the object
    referenced defined by the readResolve method
    - old object is immediately GC'd
- this allows readResolve to preserve the properties of a Singleton

    
    EX:
    
        readResolve Singleton - meh.
        private Object readResolve() {
        
            // return the Singleton
            // GC the deserialized instance
            return INSTANCE;
        }
  
#### NOTES
- using readResolve for instance control REQUIRES
    - instance fields w/ object reference types MUST be declared transient 
      
#### CONS
- if the transient modifier isn't used, it's possible for an attacker
to obtain a reference to deserialized objects before the readResolve
method is exec'd

### Use Enum Singleton instead. 

    public enum Singleton {
        INSTANCE;
    }