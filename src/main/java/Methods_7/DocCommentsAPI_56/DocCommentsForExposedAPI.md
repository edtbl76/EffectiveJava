# Item 56: Write doc comments for all exposed API Elements

## Javadoc
- generates API documentation from source w/ specially formatted
doc comments

## EXAMPLE

    EX: 
    
    /**
     * Returns the element at the specified position in this list.
     *
     * <p>This method is <i>not</i> guaranteed to run in constant
     * time. In some implementations it may run in time proportional
     * to the element position.
     *
     * @paramindex index of element to return; must be
     *             non-negative and less than the size of this list
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    E get(int index);
    
    

### Exported Classes
precede EVERY exported class, interface, constructor, method and field
declaration w/ a doc comment. 
- if class is serialized, doc its serialized form
- avoid the use of default constructors
    - there is no way to provide comments
- STRONG RECOMMENDATION to precede unexported class, interface, constructor
method and field declaration w/ a doc comment as well
    - less thorough

### Javadoc for Methods
method comments should SUCCINCTLY describe contract between method and 
client
- WHAT vs. the HOW
- include PRECONDITIONS
    - invariants that must be true for the client to invoke method
    - @throws, @params
- include POSTCONDITIONS
    - invariants that should be true AFTER invocation has completed
    successfully
- SIDE EFFECTS
    - observable change in state of the system that is NOT 
    obviously required in order to achieve the postcondition
    - (i.e. the start of a background thread)
- complete tag set
    - @param for every parameter (NOUN)
    - @return for all non-void methods (NOUN) 
    - @throws for all expected exceptions.

### Class Inheritance and Self-Use     
document self-use patterns for classes designed for inheritance
- dictates semantics of overriding methods for developers. 
- @implSpec

### Readability
doc comments should be readable both in source code and in generated 
docs.
- use of HTML tags is helpful, because Javadoc converts doc comments into
HTML
    - HTML elements in the doc will be part of the generated docs
- @code tag
    - causes code fragment to be rendered in "code font"
    - suppresses processing of HTML markup and nested Javadoc tags 
        - (very common for using things like < which is an 
        HTML metacharacter AND a less than sign.) 
- @literal
    - suppresses HTML markup and nested tags. 
    - (basically its like @code, w/o rendering it in code font)
- RULE OF THUMB
    - if you can't make BOTH readable, then favor readability of 
    generated docs
    
### SUMMARY DESCRIPTION
- first few statements of each doc comment
    - NOT complete sentences
    - use 3rd person declarative tense
- this should "stand on its own"
    - NO 2 members or constructors in a class/interface should have the
    same summary description
    - VERY important for overloadings.
- NOTE: 
    - use @literal if a period is "inside" the summary description,
    because periods will terminate the statement. 

- Classes/Interfaces/Fields
    - "Noun Phrase"
        - describe "thing" represented by instance of class/interface/field


    EX: 
        Instant - An instantaneous point on the time-line
        Math.PI - The double value that is closer than any other to pi, 
        the ratio of the circumference of a circle to its diameter
        


### Indexes
API elements are automatically indexed
- classes, methods and fields
    
The @index tag can be used to index additional terms important to the
API. 
- EX: I want to search this {@index important_term} field. 

### Special Cases! (Generics, Enums and Annotations)
GENERICS
- make sure to document all type parameters


    EX: 
    
        /**
         *   An object that maps keys to values. A map cannot contain
         *   duplicate keys; each key can map to at most one value.
         *
         *   (Remainder omitted)
         * 
         *   @param <K> the type of keys maintained by this map
         *   @param <V> the type of mapped values
         */
        public interface Map<K, V> { ... }
        

ENUM TYPES
- document CONSTANTS, TYPE and any PUBLIC METHODS


    EX: 
        
        /**
         *  An instrument section of a symphony orchestra.
         */
        public enum OrchestraSection {
        
            /** Woodwings, such as flute, clarinet and obie. */
            WOODWIND,
            
            /** Brass instruments, such as french horn and trumpet */
            BRASS,
            
            /** Percussion instruments, such as timpani and cymbals */
            PERCUSSION,
            
            /** Stringed instruments, such as violin and cello. */
            STRING;    
        
        } 
        

ANNOTATION TYPES
- document members and the type itself


    EX: 
    
        /**
         *  Indicates that the annotated method is a test method that
         *  must throw the designated exception to pass.
         */
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface ExceptionTest {
            /**
             *  The exception that the annotated test method must throw
             *  in order to pass. (The test is permitted to throw any
             *  subtype of the type described by this class object.)
             */
            Class<? extends Throwable> value();
        }


## Package Level Doc Comments
- stored in package-info.java
    - must also contain a package declaration
    - MAY contain annotations on the package declaration
    
## Module Level Doc Comments
(similar to package) <br> 
- stored module-level.java
    - (same situation as package)
    
## Thread-Safety and Serialization
ALWAYS DOCUMENT THREAD SAFETY (Item 82)

ALWAYS DOCUMENT SERIALIZED FORM (Item 87)

## JavaDoc inheritance
This is NOT class inheritance

JavaDoc can inherit parts of doc comments (from supertypes) with the
{@inheritDoc} tag. 
- useful for inheriting documentation from interfaces being impl'd
- (no copy required) 