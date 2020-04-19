# Singleton
- Enforce singletons w/ an Enum Type or a Private Constructor

SINGLETON = class that is instantiated only once. 
    - usually represents stateless object (i.e. a function)
    - or a system component that is or should be intrinsicially unique
    
    
## IMPL OPTIONS
1. public final member field INSTANCE
    - slightly more clear that the pattern is a singleton because the field is public
    - slightly simpler
2. use Static Factory
    - more flexible
        - you can change the pattern from being a SINGLETON if you need to w/o 
        changing the API. 
        - even though the factory method returns the ONLY instance, it can be
        modified to return a separate instance per thread if necessary
    - you can write a GENERIC SINGLETON FACTORY if the application requires it
    - supports the use of a METHOD REFERENCE as a supplier
    
        
        EX: 
            Elvis::instance is a Supplier<Elvis> 
    

in BOTH cases, the member field INSTANCE is static, so it will be created when 
the class is loaded, and all calls to access the public field or the static 
factory method will return the same object reference    

3 . declare a single-element ENUM
- similar to public field approach but more concise
    - automatically serializable
    - impossible to be instantiated more than once
    - secure from serialization or reflection attacks
- can't use this if Singleton has to extend a superclass other than Enum
    - you CAN declare an Enum to implement Interfaces. 

        EX:
        
            public enum Elvis {
                INSTANCE;
                
                public void leaveTheBuilding() { ... }
            }
            
- This is often the BEST way to implement a Singleton. 

## Making Singletons Serializable
1. must implement Serializable interface
1. all instance fields must be declated TRANSIENT
1. there must be a readResolve() method provided. 

    
    EX: 
        
        private Object readResolve() {
            return INSTANCE;
        }

If these three steps aren't taken, then each time a serialized instance is 
deserialized, a new instance will be created, which violates the definition of
a Singleton. 

    
## PROS
- See Above in Impl

### WHEN TO USE WHICH STYLE

1. Enum first
    - unless you have to extend a superclass OTHER than enum
2. Static Factory
    - unless the advantages of StaticFactory aren't relevant
        - Are you going to change your mind about it being a Singleton? 
        - does the factory need to be a generic singleton factory? 
        - Do you plan on using it as a method reference in a supplier? 
3. Use Public Field

## CONS
- hard to unit test because it is IMPOSSIBLE to substitute a mock impl for a
singleton unless it impls an interface that serves as its type
