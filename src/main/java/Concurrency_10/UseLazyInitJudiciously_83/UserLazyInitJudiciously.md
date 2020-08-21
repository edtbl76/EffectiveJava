# Item 83: Use Lazy Initialization Judiciously

## Lazy Initialization
The act of deferring initialization of a field until its value is needed
- if field is never needed, we never waste resources initializing it
- primarily an optimization technique, BUT
    - can break harmful "circularities" in class and instance initialization
- the benefits of lazy initialization are tied to:
    - the no. of fields that eventually need to be init'd
    - how expensive it is to init them.
    - how often each field is accessed once init'd
    - (It is possible for lazy init to HARM performance)
    
### PROS
- decreases cost of init'ing a class/creating an instance

### CONS
- increases the cost of accessing a lazy init'd field
    
### Lazy Init + MultiThread
- requires synchronization when 2 or more thread will share a lazy initialized field
    
### Best Practices
- Don't do it unless you need to (Item 67)
    - in MOST cases, normal initialization is preferred to lazy init
- usually only worthwhile if
    - a field is accessed only on a fraction of the instances of a class
    - AND the cost of initialization is very expensive.
- MEASURE the performance 
    - with and without lazy init
    - only DAMN SURE WAY TO KNOW


### Examples 1
(The following examples can be used for static and non-static fields, but only non-static versions
are shown)

#### Normal Initialization

    EX: 
        /* 
            normal init of an instance field
            - should be final (immutable) when possible (Item 17)
        */
        private final FieldType field = computeFieldValue(); 

#### Using Lazy Initialization to break an Initialization Circularity
- uses a Synchronized Accessor 
- (simplest, clearest alternative)


    EX: 
        // Lazy initialization of instance field - synchronized accessor
        private FieldType field;
        
        private synchronized FieldType getField() {
            if (field == null) {
                field = computeFieldValue();
            }
            return field;
        }    
        

### Examples 2 - "Lazy Init Holder Class"
- Using lazy initialization for performance on a STATIC field
    - when getField() is called the first time, it reads FieldHolder.field the first time
    - BENEFIT
        - getField() isn't synchronized and it is constrained to a single field access
        - lazy init cost is negligible.

    EX: 
    
        // lazy init holder class for static fields
        private static class FieldHolder {
            static final FieldType field = computeFieldValue();
        } 
        
        private static FieldType getField(0 {
            return FieldHolder.field;
        }
        
### Examples 3 - "DoubleCheck"
- used for lazy init performance on an INSTANCE field
    - defers cost of locking when accessing a field after initialization (Item 79)
    - checks value of field twice
        - once w/o locking
        - then a second time WITH locking if the field appears to be uninitialized
            - if the field is still uninitialized we'll initialize it.
- field MUST be declared "volatile" because there is NO locking once the field is init'd


    EX: 
        
        private volatile FieldType field;
        
        private FieldType getField() {
            FieldType result = field;
            
            // First Check (no locking)
            if (result == null) {
                synchronized(this) {
                
                    // second check (w/ locking)
                    if (field == null) {
                        field = result = computeFieldValue();
                    }
                }
            }
            return result;
        }    
        
- Looks kind of convoluted, but it's much more graceful than low-level concurrent programming
- NOTE: This DOES work w/ static fields as well, but the "lazy init holder" is preferred. 

#### Single-Check Variant
- If you are in a situation where the lazy init can tolerate repeated initialization, then you
can remove the second check. 


    EX: 
        private volatile FieldType field;
        
        private FieldType getField() {
            FieldType result = field;
            
            if (result == null) {
                field = result = computeFieldValue();
            }
            return result;
        }    

#### Racy Single-Check Variant
- This is a variant w/ special conditions that allows you to remove the VOLATILE keyword.
    - considered an "exotic technique"
- CONDITIONS
    - it isn't necessary for EVERY thread to recalculate the value of the field
    - the type of the field is primitive (other than long or double)
- BENEFITS
    - it can speed up field access w/ the tradeoff of additional initializations
    - (up to 1 init per thread that accesses the field)
