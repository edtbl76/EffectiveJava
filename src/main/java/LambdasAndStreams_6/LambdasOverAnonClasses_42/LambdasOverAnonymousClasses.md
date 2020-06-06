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

## ADVANTAGES
- The addition of lambdas makes it practical to use function objects where it wouldn't have
previously made sense. 

