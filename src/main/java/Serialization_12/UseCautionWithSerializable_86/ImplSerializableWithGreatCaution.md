# Item 86: Implement Serializable with great caution

## Serializable Interface
- The main cost of implementing Serializable is that it decreases the 
flexibility to change a class's impl once it has been released. 
    - the byte-stream encoding (Serialized form) of the class becomes
    part of its exported API
    - once it is released/distributed, you are stuck supporting the serialized 
    form forever. 
    - changing it later can result in an incompatible change in the 
serialized form. 
        - Clients trying to serialize an instance using the OLD version of the class and deserialize it using the new one (or vice versa)
            - sad face, no workey. 
- another major cost is that it is error-prone
- another major cost is that it is insecure.
- increased testing burden
    - when classes are revised you must ensure that mismatched
    versions can be serialized/deserialized.
    

### Workaround for mismatched versions
- it is possible to change internal representation while maintaing
the original serialized form
    - ObjectOutputStream.putFields() 
    - ObjectInputStream.readFields()
- challenging to implement
    - leaves bad code smells. 
    
### Better Approach
- Dont accept default serialized form
- CAREFULLY design a "high-quality serialized form"
    - you will have to live w/ it for "the long haul" (Items 87, 90)
- It is harder to develop, but worth the effort. 

#### Evolution Constraints
"Stream Unique Ifentifiers" (a.k.a Serial Version UIDs)
- Every serializable class has a unique ID number associated w/ it. 
    - either auto generated or can be specified manually
        - (manual) - static final long serialVersionUID;
        - value is affected by structure of class
            - (name, interfaces it impls, members, including 
            synthetic members gen'd by compiler)
            - if these components change, so does the UID. 
    - failure to declare it results in
        - InvalidClassException
        
### Serialized Object Creation
Serialization is an "Extralinguistic Mechanism" for creating objects. 
- deserialization is a "hidden constructor"
    - since it isn't explicit, you have to manually ensure that
    all invariants are preserved
    - you must also ensure that it isn't possible to gain access
    to internals of the object under construction
- default deserialization mechanisms leave objects open to
    - invariant corruption
    - illegal access
    
## When to implement Serializable
- if a class/application is going to use/impl a framework that relies on 
Java serialization for object transmissions or persistence
- if there is heavy reliance on a component/class that MUST implement Serializable
- Static Member Classes are ok to impl Serializable (if absolutely necessary)

## When NOT to
- Classes that represent active entities (like thread Pools)
- Classes designed for inheritance
    - EXCEPTION
        - Throwable
            - impls Serializable so RMI can send exceptions from
            server to client
        - Component
            - impls Serializable so GUIs can be sent, saved and restored
- Inner classes (Item 24)
    - they use compiler-generated 'Synthetic Fields' 
        - stores refs to 'enclosing instances'
        - store values of local vars from enclosing scopes. 
    - these fields have an unspecified relationship w/ a class definition
    - (same situation w/ anonymous and local classes)
        - therefore default serialized form is "ill-defined" 
    

### Interfaces and Serializable
- Interfaces should rarely extend Serializable
    - this forces anyone eho extends the class/impls the interface you are writing
- EXCEPTION
    - if the class/interface exists entirely to participate in a framework
    that requires all participants to impl Serializable... then it might make sense
