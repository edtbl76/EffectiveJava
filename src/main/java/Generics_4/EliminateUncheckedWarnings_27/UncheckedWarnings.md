# Item 27: Eliminate Unchecked Warnings
When coding w/ Generics there are a myriad of warnings
- unchecked cast warnings
- unchecked method invocation warnings
- unchecked parameterized vararg type warnings
- unchecked conversion warnings. 


    EX: 
    
        Set<Object> objectSet = new HashSet();
        
        Blah.java:1 warning: [unchecked] unchecked conversion
            Set<Object> objectSet = new HashSet();
            required: Set<Lark>
            found: HashSet
            
The above example is a reminder of RAW TYPES. HashSet
is a raw type. In order to fix this, you need to
provide the DIAMOND OPERATOR (<>) which allows the
compiler to perform TYPE INFERENCE based on the
entire statement (i.e. the return type specified
at the beginning of the declaration)

    EX:
    
        Set<Object> objectSet = new HashSet<>();
        
## WHY
Eliminating all unchecked warnings assures you that
your code is TYPESAFE
- i.e. you won't get ClassCastExceptions at runtime.

If you can PROVE that your code is typesafe, but can't
eliminate a warning
- then and double only then should you suppress it
- (@SuppressWarnings("unchecked")) <-- annotation

The reason that you do this last is that if you are too 
liberal w/ the annotation, you may inadvertently silence 
legitimate warnings. 

## USING SuppressWarnings ANNOTATION
This should always be used in the smallest scope possible
- short method/constructor or var declaration are the best use cases
- NEVER do this on an entire class. 

If you are using it on a large method, a quick trick might be to place it on 
a variable instead. 

    EX:
    
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                return (T[]) Arrays.copyOf(elements, size, a.getClass());
            }
            
            System.arraycopy(elements, 0, a, 0, size);
            
            if (a.length > size) {
                a[size] = null;
            }
            
            return a;
        }
        
        
The previous code snippet generates the following warning:

    
        ArrayList.java:305: warning: [unchecked] unchecked cast
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
                                        ^
            required: T[]
            found:    Object[]
            


- You can only place annotations on declarations.
    - i.e. you can't put it on a return statement. 
- You could put it on the entire method, but there are 2 stanzas and one lib
call, each of which may result in individual and separate warnings. 


A better method, as suggested, is to create a local variable to reduce the
scope of the SuppressWarnings call
- always add a comment justifying why you used the SuppressWarnings annotation
- (Specifically, defending why you believe that the code is TYPE SAFE)

    EX
        
        public <T> T[] toArray(T[] a) {
            
            if (a.length < size) {
            
                /*
                    This annotation is safe because the array we're
                    creating is of the same type we've passed in 
                    
                        T[]
                        
                */
                @SuppressWarnings("unchecked")
                T result = (T[]) Arrays.copyOf(elements, size, a.getClass());
                return result;
            }
            
            System.arraycopy(elements, 0, a, 0, size);
            if (a.length < size) {
                a[size] = null;
            }
            return a;
        }
        
        