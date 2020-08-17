# Item 68: Adhere to generally accepted naming conventions

## JLS (Java Language Specification)
Java's well-established naming conventions

Don't violate them. Ever. 
- APIs will be hard to use
- impl will be hard to maintain

### Typographical

#### Package Names
- period-separated hierarchy
- reverse internet domain style
    - com.google.<package name>
- lowercase alphabetic chars
    - rarely digits
- meaningful abbreviations encouraged
    - util vs. utilities
- sub-packages are packages within a package
    - java.util (package)
        - java.util.concurrent.atomic (subpackage)
        
#### Class/Interface/Enum/Annotation
- first letter of each word is capitalized
    - Ex. "GoodClassName"
- AVOID abbreviations except:
    - acronyms (FBI, AWT)
        - Capitalize vs TitleCase
            - HTTPURL vs. HttpUrl (I prefer latter)
            - AWT vs. Awt  (I prefer former)
    - common abbreviations (min, max) 
    
#### Method and Field names
- camelCase
- if acronym occurs as first word, it should be lower case
- EXCEPTION:
    - constant fields should be UPPERCASE (SNAKE_CASE)
        - ex. GOOD_CONSTANT_FIELD_NAME
        - (constants are static final fields w/ immutable values)
        - ONLY ACCEPTABLE USE OF UNDER SCORES 
        
#### Local Variables
- same conventions as member names
    - EXCEPTIONS
        - abbreviations are allowed
        - individual characters are allowed
        - short char sequences w/ 'contextual' meaning

#### Parameters
- Input Parameters
    - stricter local vars
    - carefully named because they constitute method documentation
- Type Parameters
    - T = arbitrary type
        - "T, U, V" - sequence of arbitrary types
    - E = element type of a collection
    - K, V = Key, Value types of a amp
    - X = Exception

#### Typographical Table

| Identifier Type | Examples | 
| --- | --- | 
| Package or Module | org.junit.jupiter.api, com.google.common.collect |
| Class or Interface | Stream, FutureTask, LinkedHashMap, HttpClient |
| Method or Field | remove, groupingBy, getCrc |
| Constant Field | MIN_VALUE, NEGATIVE_INFINITY |
| Local Variable | i, denom, houseNum | 
| Type Parameter | T, E, K, V, X, R, U, V, T1, T2 |

### Grammatical
(more flexible and more controversial than typographical conventions)

#### Pacakges
(no grammatical naming conventions)

#### Instantiable Classes (including Enums)
- Singular Nouns/Noun Phrases


    EX: 
        Thread
        PriorityQueue
        ChessPiece
        
#### Non-Instantiable Classes
- Plural Noun 


    EX: 
        Collectors
    Collections
    
#### Interfaces
- named like classes

    
    EX:
        Collection
        Comparator
        
- (or) with an adjective
    - (ending in 'able' or 'ible')
   
   
    EX:
        Runnable
        Iterable
        Accessible
        
#### Annotations
- many uses, so no dominant parts of speech
    - nouns, verbs, prepositions, adjectives are all common
    
    
    EX:
        BindingAnnotation
        Inject
        ImplementedBy
        Singleton
        
#### Methods 
- Methods that perform actions are usually named w/ verb/verb phrases


    EX: 
        run()
        append()
        drawImage()
        
- Methods that return a boolean value usually have names that begin with
    - is (common)
    - has (less common)
    
    
    EX:
        isDigit()
        isProbablePrime()
        isEmpty()
        isEnabled()
        hasSiblings()
        
- Methods that return:
    - non-boolean
    - attribute of object on which they're invoked    
    - use:
        - noun, nounphrase
        - verb phrase starting w/ the word 'get'
       
        
    EX:
        size()
        hashCode()
        getTime()
        
NOTE: 
- the first two (non verb) forms tend to provide more readable code
- (but a lot of folk bitch that the getXXX() form is the most appropriate)

##### The Getter/Setter argument
- in classes where we have methods that both SET AND RETURN, the
two methods are typically named
    - getAttribute() for the method that returns 
    - setAttribute() for the method that does
    
##### Special Case Methods
- Instance methods that CONVERT the type of the an object
    - (returns an independent object of a different type)
    - uses to<Type>
    
    
    EX:
        toString()
        toArray()
        
- methods that return a view (Item 6)
    - (type differs from that of the receiving object)
    - uses as<Type>
    
    
    EX:
        asList()

- methods that return a primitive w/ the SAME VALUE as the object
on which they are invoked
    - called <type><Value>
    

    EX:
        intValue
        
##### Static Factories
- from
- of
- valueOf
- instance
- getInstance
- newInstance
- get<Type>
- new<Type>
        
#### Field Names
- less established
- less important, because field names are VERY RARELY exposed in an API