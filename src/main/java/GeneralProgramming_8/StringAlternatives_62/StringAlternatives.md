# Item 62: Avoid strings where other types are more appropriate

## Strings are poor substitutes for other value types

### Input Laziness
- most input comes into a system in String format. 
    - there is a tendency to leave it in that format even if the data
    isn't text-based. 
- yes/no => Boolean
- numerics => use appropriate types

WHY? 
- by transforming the data to appropriate types on input it ensures that
any use of the data in it's "type-specific context" will be correct. 
(i.e. we won't be evaluating comparisons on a string instead of a 
number)

## Strings are poor substitutes for enum types
- Item 34

## Strings are poor substitutes for aggregate types
- If an entity has multiple components, representing it as a string
has several issues


    EX: 
    
        String compound = className + "#" + i.next();
        
        
PROBLEMS:
- if the char separator exists in one of the fields, we'll get
"undesired behavior"
- accessing individual fields requires string parsing
    - slow
    - tedious
    - error-prone. 
- can't use equals(), toString() or compareTo()

### SOLUTION - write a representation of the aggregate
(usually a private static member class) - Item 24

## Strings are poor substitutes for capabilities
(EX: prior to Java 2, trying to instantiate your own
version of a ThreadLocal)


    EX: 
    
        public class ThreadLocal {
            private ThreadLocal() {} // Can't instantiate this
            
            // sets current thread's value for named var
            public static void set(String key, Object value);
            
            // Returns the current thread's value for the named var
            public static Object get(String key);
        }
        
- BROKEN!
    - the string keys represent a shared global namespace for thread-local vars. 
    - if 2 clients independently try to use the same name for their own thread-local var, 
    they'll unintentionally share the same var. 
        - both clients will fail. 
    - TERRIBLE security. 
        - a client could use the same string key as another client to gain access to the other client's
        data. 
    
    
### SOLUTION 1 - Unforgeable Key
- in order for this approach to work, the client-provided string keys have to be UNIQUE
- provide a true CAPABILITY (a.k.a. unforgeable key) 
    
    
    EX: 
    
        public class ThreadLocal {
            private ThreadLocal()  {}   // Can't instantiate
            
            public static class Key {   // Capability
                Key() { }
            }
            
            // Generate UNIQUE, UNFORGEABLE KEY
            public static Key getKey() {
                return new Key();
            }
            
            public static void set(Key key, Object value) {
                public static Object(get Key key)
            }
        
        }   

CONS
- static methods aren't necessary
    - can be replaced w/ instance methods
    - this means the key is no longer a key FOR a thread-local variable, it IS a thread-local variable
    
### SOLUTION 2 - Refactor Based on Solution 1 CONS

    EX: 
    
        public final class ThreadLocal {
            public ThreadLocal();
            public void set(Object value);
            public Object get();
        } 

CONS:
- This isn't typesafe
    - it is IMPOSSIBLE to make the original String-based API typesafe
    - it is CHALLENGING to make the Key-based API typesafe
    - it is SIMPLE to make THIS API type-safe by making ThreadLocal a parameterized class
    
    
### SOLUTION 3 - Make it a parameterized class

    EX:
        
        public final class ThreadLocal<T> {
            public ThreadLocal();
            public void set(T value);
            public T get();
        }
        
