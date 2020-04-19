# Item5: Prefer dependency injection to hardwriting resources
addresses dependencies of underlying resources
- desire is to have MULTIPLE instances of a class, each of which uses one of N
possible options desired by the CLIENT.
- increase reusability, testability and flexibility of the code.

DO NOT
- use Static Factories
- use Singletons
- create resources directly

DO
- Dependency Injection (pass resources (or factories that create them) into the constructor 
(or static factory or builder)) 

## COMMON BAD EXAMPLES
Noninstantiable Companion Classes and Singletons assume that there is only ONE
dependency option worth using. 
- usually multiple options (even if we only use one today!)
- flexibility provides the ability for special test cases. 


STATIC UTILITY CLASSES & SINGLETONS are not good choices for classes
whose behavior is parameterized by an underlying resource.


### STATIC UTILITY EXAMPLE
Inflexible/Untestable

    EX:
        public class SpellChecker {
            
            private static final Lexicon dictionary = ...;
            
            private SpellChecker() {} // NONINSTANTIABLE
            
            public static boolean isValid(String word) { ... }
            public static List<String> suggestions(String typo) { ... }
        
        }
        

### SINGLETON
Inflexible/Untestable

    
    EX:
    
        public class SpellChecker {
            
            private final Lexicon dictionary = ...;
            
            private SpellChecker(...) { }
            public static SpellChecker INSTANCE = new SpellChecker(...);            
                       
            public static boolean isValid(String word) { ... }
            public static List<String> suggestions(String typo) { ... }
        }
        

## DEPENDENCY INJECTION 
- one way to do this is to pass the RESOURCE (dependency) into the constructor
when creating a new instance.

   
    EX:
    
        public class SpellChecker {
            private final Lexicon dictionary;
            
            public SpellChecker(Lexcion dictionary) {
                this.dictionary = Objects.requireNonNull(dictionary);            
            }
            
            public boolean isValid(String word) { ... }
            public static List<String> suggestions(String typo) { ... }
        }
        
### PROS
- works with an arbitrary number of resources and dependency graphs
- preserves immutability by keeping the field FINAL
    - allows clients to share dependent objects if they are using the 
    same resource
- works equally with
    - CONSTRUCTORS
    - STATIC FACTORIES
    - BUILDERS
- IMPROVES FLEXIBILITY AND TESTABILITY

VARIANT
- instead of passing a resource dependency, we can pass a resource FACTORY
- Methods that take a Supplier<T> on input should constrain the factor's type
param w/ a BOUNDED WILDCARD TYPE
    - this allows the client to pass in a factory that can create ANY subtype
    of a specified type. 
    
    EX: 
    
        Mosaic create(Supplier<? extends Tile> tileFactory) { ... }

### CONS
- can clutter up large projects that have 1000s of dependencies


## DEPENDENCY INJECTION FRAMEWORKS
- frameworks that help limit the clutter associated w/ DIY Dependency Injection
    - EX:
        - Dagger, Guice, Spring.
