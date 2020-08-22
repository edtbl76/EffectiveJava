# Item 85: Prefer alternatives to Java serialization

## Java Serialization
- Object serialization is Java's framework for
    - serializing (encoding objects as byte streams)
    - deserializing (reconstructing objects from their encodings)
- Once objects have been serialized, the encoding can be
    - sent from one VM to another
    - stored on disk for later deserialization
    
### Insecure
- has an Attack Surface that is too big to protect
    - includes:
        - java platform libraries
        - third party libraries
        - applications itself.
- even ALL relevant best practices w/ serializable classes that SHOULD be 
invulnerable can result in vulnerable application.

### Gadgets
- A gadget is a method invoked during deserialization of an object that
performs potentially dangerous activities.
    - A "Gadget Chain" is a set of multiple gadgets that can be used together
    - These have been the sources of many attacks due to gadgets and gadget chains
    
    
### Deserialization Bombs
- A deserialization bomb is a denial-of-service attack by causing the deserialization that requires a long
time to deserialize. 
    - doesn't even require a gadget
    
    
    EX:
        
        static byte[] bomb() {
            Set<Object> root = new HashSet<>();
            Set<Object> set1 = root;
            Set<Object> set2 = ne HashSet<>();
            for (int i = 0; i < 100; i++) {
            
                Set<Object> innerSet1 = new HashSet<>();
                Set<Object> innerSet2 = new HashSet<>();
                
                // Make innerSet1 unequal innerSet2
                innerSet1.add("foo");    
                
                set1.add(innerSet1);
                set1.add(innerSet2);
                
                set2.add(innerSet1);
                set2.add(innerSet2);
                
                set1 = innerSet1;
                set2 = innerSet2;
            }
            return serialize(root);
        }
        
- deserializing this is going to take some time. 
    - deserializing a HashSet requires computing hashCode() of all of its elements
    - 2 elements of root hash are each has sets containing 2 hashSet elements, etc. 100 levels deep 
    - this means that hashCode is going to be invoked over 2^100 times. 
    
## Avoiding Serialization Exploits
- Don't serialize anything!
    - "There is no reason to use Java Serialization in any new system you write"
- When dealing with legacy systems that require Java Seralization
    - NEVER DESERIALIZE UNTRUSTED DATA. 
    
### Object Deserialization Filtering
- java.io.ObjectInputFilter
     - allows a filter to be applied to data streams before they're deserialized. 

#### Whitelisting vs. Blacklisting
- blacklisting
    - accepting classes by default, and rejecting a list of potentially dangerous classes
- whitelisting (preferred)
    - rejecting classes by default, and accepting a list of "presumed" safe classes
- SWAT (Serial Whitelist Application Trainer)
    - tool that can auto prepare whitelists for your application
    - protects against excess memory usage
    - protects against excessively deep object graphs
    - does NOT protect against serialization bombs
    
## Cross Platform Data Representations
- (a fancy word for alternative serialization systems)

### PROS
- much simpler than Java serialization
- no serialization/deserialization of arbitrary object graphs
- simple structured data-objects consisting of a collection of key-value pairs. 
    - limited support for data types
    
### Examples
- Protocol Buffers (Protobuf)
    - designed by Google for storing/interchanging structured data among its servers
    - "language-neutral", originally designed for C++
    - binary, very very fast
        - much more efficient than JSON
    - supports pbtxt for human-readability
    - offers schemas(types) for documenting/enforcing appropriate usage. 
- JSON (JavaScript Object Notation)
    - designed by Doug Crockford for browser-server communication
    - "language-neutral", originally designed for JS
    - human-readable/test-based
    - "exclusive" data representation
