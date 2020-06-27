# Item 45: Use Streams Judiciously

## Streams API
Streams API was added in Java 8 to ease task of performing bulk operations 
- sequentially <br>
OR IN 
- parallel

### TWO KEY ABSTRACTIONS
1. stream
    - finite or infinite sequence of data elements
1. stream pipeline
    - represents a multistage computation on these elements. 
    
Elements:
- collections
- arrays
- files
- regex pattern matchers
- PRNG
- and more!
    
Data Elements can be:
- object references
- primitives (int, long, double)

### STREAM PIPELINE
SOURCE STREAM 
followed by 0 or more INTERMEDIATE OPERATIONS 
followed by 1 TERMINAL OPERATION
    
#### INTERMEDIATE OPERATION
Each intermediate operation transforms the stream in some way
- mapping element to a function of that element
- filtering out elements that do/don't satisfy some condition

They ALL transform one stream into another, whose element type may (or may not) be
the same as the input stream. 

#### TERMINAL OPERATION
performs a final computation on the stream resulting from the result of the last intermediate
operation (or the source stream if there are no intermediate operations)
- storing elements into a collection
- returning a certain element. 
- printing/displaying all of the elements. 

NOTE: a stream pipeline w/ o a terminal operation is a silent no-op. 

#### LAZY EVALUATION
Stream pipelines are all evaluated lazily. 
- eval doesn't start until TERMINAL operation is invoked
- data elements that aren't required in order to complete the terminal operation are never computed

This is what makes infinite streams possible

## FLUENCY
A Fluent API is designed to allow all of the calls that comprise a pipeline to be chained into
a single expression
- multiple pipelines can also be chained together into a single expression.

## PARALLEL v. SEQUENTIAL
- stream pipelines are all sequential by default.
- parallelized using parallel() method
    - "seldom appropriate to do so"
    
## BEST PRACTICES
Stream API is very powerful
- "just because you can doesn't mean you should

### General Rules
- "when used appropriately, it makes programs SHORTER and CLEARER"
- "when used inappropriately, they make programs difficult to read and maintain"
- Avoid using streams to replace all loops
- "refactor existing code to use streams and use them in new code
only where it makes sense to do so"

### Lambdas
In the absence of explicit types, careful naming of lambda parameters is
essential to the readability of streams pipelines. 

### Helper Methods
Using helper methods is integral to the readability of streams pipelines
- Any "impl"-specific details should be extracted from the pipeline into
their own helper methods. 
- these helper methods should be invoked as lambdas or method references

### Avoid using streams to process char values

    EX: 
        "Hello world!".chars().forEach(System.out::print);
        
        prints out 721011081081113211911111410810033.
                   
    It can be resolved with a cast, but should generally be avoided
    
        "Hello world!".chars().forEach(
            x -> System.out.print((char) x)
        );        
        
## Iterative Code vs. Stream Pipelines
- stream pipelines express repeated computation using function 
objects (lambdas, method references)
- iterative code expresses repeated computation using code blocks

Generically speaking, if you aren't sure which works best:
- try both and pick the winner!

### CodeBlocks Can Do Things That Function Objects Can't
- you can read/modify any local variable in scope
    - lambdas can only read FINAL or EFFECTIVELY FINAL variables
    - lambdas can't modify ANY local vars
- you can RETURN from enclosing method, BREAK/CONTINUE from enclosing loop, 
or THROW any checked exception that method is declared to throw
    - lambdas can't do any of these things.
    
### On the other hand, Streams can do the following very easily
- uniformly transform sequences of elements
- filter sequences of elements
- combine sequences of elements using a single operation
    - add them, concatenate them, compute minimum, etc.
- accumulate sequences of elements into a collection
    - maybe groupingBy a common attribute
- search a sequence of elements for an element satisfying some
criterion

### Accessing Corresponding Elements from multiple stages of a pipeline simultaneously
This is one of the things you often need to do, but is kind of hard
to accomplish inside a pipeline.
- once you map a value to some other value, the original value is
lost

#### Pair Workaround
A common workaround is to map each value to a PAIR object that
contains the original value and the new value. 
- not desirable, especially if the pair objects are required for
multiple stages of a pipeline.
- messy, verbose code (which defeats the main point of streams!)

#### Inversion 
A less-naive workaround is to invert the mapping when you need to 
access the "earlier-stage" value

EXAMPLE:
- We have a program to print the first 20 Mersenne Primes.
    - (Mersenne number is a number of the form 2^(p) - 1)
    - if p is prime, the corresponding Mersenne number MAY be
    prime.
- In the second example, we precede each Mersenne number w/ its
exponent
    - PROBLEM: this value was only made available in the source 
    stream, so it's gone by the time we reach the terminal
    operation. 
    - SOLUTION: we compute the exponent by inverting/reversing
    the mapping that took place in the first intermediate operation

#### Cartesian Product
A task that requires computing all of the pairs of elements that
can be chosen from two sets. 