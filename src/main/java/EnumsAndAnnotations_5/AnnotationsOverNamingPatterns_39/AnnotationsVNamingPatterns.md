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


ANNOTATION PROCESSOR:
- these are used to enforce restrictions that the compiler can't. 
- w/o annotation processors, annotations will still compile, but 
any restrictions or constraints that should be monitored will fail at
runtime. 

MARKER ANNOTATION:
- an annotation that has no parameters but simply "marks" the 
annotated element.
- if this is MISSPELLED the program won't compile, which is a step up
from named patterns. 

PARAMETERIZED ANNOTATION
- an annotation that has parameters. 
- in the example code, the parameter is used to test that the passed in 
exception is what is thrown by the sample methods. 


If test programs w/ parameterized compile, it guarantees that the
annotation parameters represent valid input types with ONE CAVEAT
- If annotation parameters are valid at compile time, but the class
representing the specificed input parameter is no longer present at
runtime, the test runner throws a "TypeNotPresentException"



