# Item 87: Consider using a custom serialized form.

## API Anecdote
- In many cases, during API development, you sometimes have to 
release a "throwaway" implementation you know you'll replace in the 
future.
- This DOES NOT work for Serializable. 
    - remember.. you are stuck with it. 
    - EX: BigInteger
    
### Default Serialized Form
- a reasonably efficient coding of the Physical representation of the object
graph rooted at the object
    - describes the data contained in the object and in every object
    that is reachable from this object
    - describes the topology by which all objects are interlinked


The IDEAL serialized form contains only the LOGICAL data represented by
the object
- it is independent of the physical representation
- see (GoodCandidateDefaultSerializedForm.Name)
- see (BadCandidateDefaultSerializedForm.StringList)

#### Recommendations
- "Accept the default serialized form only if it represents the same
encoding you would choose if you were creating a custom serialized form"
    - i.e. default serialized form is likely to be appropriate if
    an object's physical representation is IDENTICAL to it's logical
    content
- if default serialized form is appropriate
    - provide a readObject() method
        - ensures invariants
        - ensures security

#### Disadvantages when physical representation differs greatly from logical data content
(see BadCandidateDefaultSerializedForm.StringList)
- permanently ties exported API to current internal representation
- it can consume excessive space
    - In the example, the serialized form will represent each entry in 
    the linked list. 
    - This is an implementation detail, but it leaks into the serialized
    form, making it unnecessarily large
        - persisting the serialized form will be excessively slow
- it can consume excessive time
    - see above
- it can cause stack overflows
    - default serialization performs recursive traversal of object graph
    - this can overflow even for moderate sized object graph

#### Improving Bad Candidates
(see FixingTheBadCandidate.StringList)
- remove "implements Serializable" from inner classes that act as
impl details and do NOT represent logical data representation
- use "transient" modifier to indicate that an instance field is to 
be omitted from the class's default serialized form.
- special methods
    - (the following methods make it possible to add nontransient 
    instance fields in later releases)
        - preserves backward/forward compatibility
    - writeObject()
        - invokes defaultWriteObject()
    - readObject()
        - invokes defaultReadObject()
- nontransient fields MUST BE PART OF THE LOGICAL STATE OF THE OBJECT

#### Transient Modifier Notes
- When deserializing a serialized instance
    - transient fields will be initialized to their DEFAULT VALUES
        - object references: null
        - numeric primitives: 0
        - boolean: false
    _ if default values are unacceptable
        - a readObject() method must be provided that invokes
        defaultReadObject()
            - restores fields to acceptable values (Item 88)
            - can be lazy init'd the first time they are used (Item 83)
            
### Serialization and Synchronization
- you must impose any synchronization on object serialization that you
would otherwise impose on any other method that reads the entire state of the object.
    - for thread-safe objects that synchronize every method and use the default serialized form,
    use the following writeobject()
    
    EX:
        private synchronized void writeObject(ObjectOutputStream s) 
                throws IOException {
            s.defaultWriteOBject();
        }
        
- if synchronized block is placed in writeObject() method
        
### Serial Version UID
- Regardless of default/custom form, ALWAYS declare an explicit serial version UID
    - eliminates UID incompatibilities (Item 86)
    - small performance benefit
        - (auto generation requires cryptohash SHA-1 gen)

        
    EX:
        private static final long serialVersionUID = randomLongValue;
- doesn't have to be unique
- general best practices suggest to run 'serialver' utility on the class. 
    - this ensures that if you mod an existing class that doesn't have a serial version UID
        - that the NEW version will accept existing serialized instances      
- do NOT change the serial version UID
    - breaks compatibility w/ all existing serialized instances of the class.   
