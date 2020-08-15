# Item 55: Return Optionals Judiciously

## (Pre Java 8 Solutions for methods that might be unable to return a value)
1. throw an exception
1. return null (assuming that the return type was an object reference type)  


    EX: 
        (Exception is thrown)
        
        public static <E extends Comparable<E>> E max(Collection<E> collection) {
            if (collection.isEmpty()) {
                throw new IllegalArgumentException("Empty Collection!");
            }
            
            E result = null;
            for (E e : collection) {
                if (result == null || e.compareTo(result) > 0) {
                    result = Objects.requireNonNull(e);
                }
            }
            return result;
        }    

### DOWNSIDES
- Exceptions should be reserved for "exceptional conditions" (ITEM 69)
- throwing exceptions is expensive, because the entire stack trace is captured when it is created
- returning null forces clients to perform null checks or "special case" code to handle a scenario
when null is returned
- if null does return, and it isn't handled, we get an NPE
    - ignore the problem -> get burned

## Java 8 -> Optionals
Optional\<T>
- immutable container that can hold either a single non-null T reference, or nothing. 
- an Optional w/ nothing in it is "empty" 
- A value in an Optional is called "present"


    EX: 
        (Above example usign an Optional) 
        
        public static <E extends Comparable<E>> Optional<E> max(Collection<E> collection) {
            if (collection.isEmpty()) {
                return Optional.empty():
            }
            
            E result = null;
            for (E e : collection) {
                if (result == null || e.compareTo(result) > 0)  {
                    result = Objects.requireNonNull(e);
                }
            }
            
            return Optional.of(result);
        }   
        
### Optional + Null
Optionals were created specifically to prevent the need to return null, so don't pass NULL to
an Optional. 
    - this will cause an NPE and it completely defeats the purpose of using the Optional
    - May God have mercy on your soul
    
If you want to use Optional to convert a null into an empty, do this: 

    
    Optional.ofNullable(valueThatCouldBeNull);
    
### Streams + Optionals
Just a friendly note that many Streams methods/operators return optionals.


    EX: 
        public static <E extends Comparable<E>> Optional<E> max(Collection<E> collection) {
            return collection.stream().max(Comparator.naturalOrder());
        }
        

(See Helpers below) <br> 
In some cases we need to go from Stream<Optional<T> to Stream<T>

    EX: 
        // Pre Java 9
        streamOfOptionals
            .filter(Optional::isPresent)
            .map(Optional::get)
            
        // Java 9 added stream() method
        // it returns an adapter that turns an Optional into a Stream
        streamOfOptionals
            .flatMap(Optional::stream)
            
- flatMap was brought to you by Item 45
        
### Optionals + Client Code. 
There are several options a client has in terms of choosing how to handle the case where a value 
isn't returned

DEFAULT VALUE

    String lastWordInLexicon = max(words).orElse("No more words.");
    
THROW APPROPRIATE EXCEPTION
- NOTE: Best practices are to provide an Exception Factory
    - the advantage is that the exception is only created if it is actually thrown. 
    - if we provide an exception that isn't thrown, it will still be created (which means 
    it has to unwind the call stack)


    Toy myToy = max(tyoys).orElseThrow(TemperTantrumException::new);

ROLL THE DICE <br>
(if you can prove that the value is nonempty, you can get it directly w/o specifying an option to
handle the null. )
- NOTE: if you're wrong, you'll end up w/ a NoSuchElementException. 

    MyObject object = max(object.EXISTS).get();
    
### Optionals + Supplier<T>: orElseGet();
In some cases, providing a default value could be expensive.
- in order to defer the cost so that it's only invoked when necessary. 
- orElseGet(Supplier<T> supplier)
    - method is similar to compute Map methods.
    - alternative to providing a default value when cost is prohibitive. 
  
### HELPER METHODS
filter(Predicate<? super T> predicate)
- if value is present AND it matches predicate, returns Optional w/ value, else Optional.empty()        
    
map(Function<? super T, ? extends U> mapper)
- if value is present, returns Optional (as if by ofNullable<T>) the result of the given mapping, 
else Optional.empty()
    
    
flatMap(Function<? super T, ? extends Optional<? extends U>> mapper)
- if value is present, returns result of applying the given "Optional-bearing mapping" 
function to the value, else Optional.empty()

ifPresent(Consumer<? super T> action) 
- if present, performs given action on that value, else no-op. 
    
ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
- if present, performs given action, else performs emptyAction
      
or(Suppler<? extends Optional<? extends T>> supplier)
- if present, returns Optional describing the value, else returns Optional 
produced by Supplying function. 

isPresent()
- safety valve. 
- true if present, else false. 
- messy though, see examples below. 

EXAMPLES: <br>
Use of isPresent() -> map() 
- isPresent is nice, but messy


    EX:
    
    Optional<ProcessHandle> parentProcess = ph.parent();
    
    // Kind of long, and uses a ternary operator. Harder to read. 
    System.out.println("Parent PID: " + (parentProcess.isPresent() ? 
        String.valueOf(parentProcess.get().pid()) : "N/A"));
        
    // more concise, and uses Optional methods (idiomatic) 
    System.out.println("Parent PID: " + ph.parent().map(h -> String.valueOf(h.pid())).orElse("N/A"));
    
        
    
### Optionals and Wrappers
- Optionals of boxed primitives is much more (prohibitively) expensive
than returning a primitive type, because the optional has TWO levels
of boxing rather than zero. 

- Workaround
    - OptionalInt, OptionalLong and OptionablDouble for int, long and
    double

### BENEFITS
- similar (in spirit) to checked exceptions
    - FORCES the developer to deal with the fact that no value may be returned
    - (failure to do this allows devs to ignore handling it, resulting in pain-in-the-ass problems
    down the line.)
    - (But checked exceptions require additional boilerplate in the client, which is a pain.)
- methods that return Optional<> objects provide flexibility to the client. 
    - they can choose what to do if the method can't return a value.

### DRAWBACKS
- most containers do NOT benefit from using optionals
    - Collections
    - Maps
    - Streams
    - Arrays
    - Optionals
    
    
    EX: 
    
        Prefer List<T> to Optional<List<T>>
        
- Performance Hits (Item 67)
    - Optionals have allocation/initialization overhead 
    - reads require indirection
    - may not be appropriate for performance critical apps 
        
### BEST PRACTICES
- declare methods to use Optional<T> as a return type when 
    - A. the method may not be able to return a result <br>
    AND
    - clients have to perform special processing if no event is 
    returned. 
- avoid using boxed (major) primitives (int, long , double) 
- it is NEVER appropriate to use an Optional as a key, value or
element in a collection or array. 
    - this introduces a level of complexity that is unwarranted. 
    
