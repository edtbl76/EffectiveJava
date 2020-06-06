# Item 39: Prefer annotations to naming patterns. 

## Naming patterns
A method of indicating that some program or program elements required special
treatment by a tool or framework. 
(EX: JUnit < 4 required test methods to begin with the char sequence 
'test')

Several Major Disadvantages
- typos result in semantic failure
- no way to guarantee that the naming pattern is restricted to appropriate
program elements.
    - not tied to compiler, so everything is discovered at runtime. 
    
## ANNOTATIONS
Solve the problem "quite nicely"
See Test annotation for an example of an annotation


### ANNOTATION PROCESSOR:
- these are used to enforce restrictions that the compiler can't. 
- w/o annotation processors, annotations will still compile, but 
any restrictions or constraints that should be monitored will fail at
runtime. 

### MARKER ANNOTATION:
- an annotation that has no parameters but simply "marks" the 
annotated element.
- if this is MISSPELLED the program won't compile, which is a step up
from named patterns. 

### PARAMETERIZED ANNOTATION
- an annotation that has parameters. 
- in the example code, the parameter is used to test that the passed in 
exception is what is thrown by the sample methods. 


If test programs w/ parameterized compile, it guarantees that the
annotation parameters represent valid input types with ONE CAVEAT
- If annotation parameters are valid at compile time, but the class
representing the specificed input parameter is no longer present at
runtime, the test runner throws a "TypeNotPresentException"

### ARRAY PARAMETERS
This is truly an extension of parameterized annotation. 
- The syntax is flexible
- optimized for single-element arrays. 
- usually requires declaring annotation types w/ array
parameters.
(THIS IS CONSIDERED LAME)

###  REPEATABLE meta-annotation
This provides an easier way to support multi-valued annotations.

It takes a single parameter, called a CONTAINING ANNOTATION TYPE
- The Containing Annotation Type is an array of the annotation type. 

Repeated annotation generates a synthetic annotation of the containing annotation type. 
- getAnnotationsByType() method abstracts this such that it can be used to access both 
repeated and non-repeated annotations of a repated annotation type. 

- isAnnotationPresent() can tell the difference between the annotation type and a containing
annotation type. 
    - if an element has a repeated annotation, 
        - then isAnnotationPresent() won't match the annotation type
        - but it will match containing annotation type.

The primary use case for REPEATABLE meta-annotations was to increase
readability of code that applies multiple instances of the same 
annotation type to a given program element (method, field etc.)
- they DO increase boilerplate. 


## BEST PRACTICES
- There is no reason to use naming conventions when annotations can
be used instead. 
- Most devs will only ever require the use of predefined annotation
types provided by Java

