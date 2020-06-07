# Item 42: Prefer lambdas to anonymous classes


## ANONYMOUS CLASSES

### FUNCTION TYPES
This was an older convention where interfaces (or less commonly, abstract
classes) with a SINGLE ABSTRACT METHOD were used as "fucntion types"

### FUNCTION OBJECTS
Instances of a Function Type, that represented functions or actions. 
- Anonymous classes (now considered obsolete) were the primary means of 
creating a function object 

    EX: 
    
    Collections.sort(words, new Comparator<>() {
        public int compare(String str1, String str2) {
            return Integer.compare(str1.length(), str2.length());   
        }
    }
    
- used for "classic" OOP design patterns
- Very commonly used with STRATEGY pattern.

Comparator interface represents ABSTRACT STRATEGY for sorting
- anonymous class above is a CONCRETE STRATEGY for sorting 

### DOWNSIDES
- heavily verbose, hard to read. 


## FUNCTIONAL INTERFACES (Java 8)
- formalization of the concept that interfaces w/ a single abstract
method are special. 

### LAMBDAS EXPRESSIONS (LAMBDAS)
- concise versions of anonymous classes


    EX:
    
        Collections.sort(words, 
            (str1, str2) -> Integer.compare(str1.length(), str2.length()));
            
            
- the type of the Lambda (Comparator<String>) isn't present in the code
- the types of the parameters (Strings), the return values (int) are also not present

### TYPE INFERENCE
- Very complicated compiler feature that allows the compiler to deduce types based on context
- This makes lambdas possible. 

- This also doubles down on the importance of using generic types and generic methods instead of
raw types. 
    - The compiler can't perform type inference on raw types. 

## COMPARATOR CONSTRUCTOR METHOD
(a.k.a. method expression or method reference) is an even more succinct way of using lambdas. 

    EX:
        Collections.sort(words, comparingInt(String::length));
        
        or
        
        words.sort(comparingInt(String::length));
        
## BEST PRACTICES
- Omit types of ALL Lambda parameters unless their presence makes the program clearer. 
    - If the compiler generates an error, THEN specify it.
- since lambdas lack NAMES and documentation 
    - don't use lambdas if:
        - computation isn't self-explanatory
        - or lambda exceeds a few lines
            - 1 line = IDEAL
            - 3 lines = MAX
- Stick to Lambdas instead of anonymous classes unless you find yourself
in a situation that requires special consideration (i.e. the limitations
listed below.)

## ADVANTAGES
- The addition of lambdas makes it practical to use function objects where it wouldn't have
previously made sense. 

## LIMITATIONS (WHEN ANON CLASSES ARE STILL NECESSARY)
- Lambdas are limited to functional interfaces
    - lambdas can't be used to create instance of an abstract class
        - (You must use an anonymous class)
    - lambdas are intended for interfaces w/ a SINGLE abstract method
        - anon classes can be used to create instance of interfaces
        with MULTIPLE abstract methods. 
- a lambds can NOT contain a reference to itself
    - "this" keyword contains a reference to the enclosing instance
    - (in an anon class), "this" refers to the anon class instance.
    - therefore, if you need an access to the function object from
    inside the function body, you have ot use an anonymous class.
- like anon classes, lambdas can't reliably serialize/deserialize
across implementations
    - DONT DO IT