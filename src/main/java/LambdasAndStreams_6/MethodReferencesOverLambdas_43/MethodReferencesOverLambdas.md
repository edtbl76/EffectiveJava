# Item 43: Prefer method references to lambdas

The primary advantages of lambdas is their succinctness. 



    EX:
    
        Lambda
        
            map.merge(key, 1, (count, incr) -> count + incr);
            

This example is pretty terse, but there is still some boilerplate. 
- the parameters ('count' and 'incr') don't really add much. 
- if you look at the syntax, the lambda is just telling us that the
function returns the sum of its two arguments. 
    - Integer (and all boxed numerical primitive types) can do the
    same thing w/ the sum() method. 
    
## Method References FTW
Method references eliminate even more boilerplate
- specifically, method references eliminate boilerplate in terms of
parameterization. 
    - the more parameters a method accepts, the mroe valuable a 
    method reference can be. 
    - NOTE: this isn't always desirable. In some cases (especially when
    there are many overloaded versions of a method), the parameters
    provide a self-documentation effect to the code.

    EX: 
    
        map.merge(key, t, Integer::sum);
        

## ADVANTAGES/BEST PRACTICES
- If code inside a lambda gets too complicated, long, hard to read
    - you can extract the code into a new method and then replace
    the code in the lambda with a method reference to that code.
    
## EXCEPTION
- Sometimes, in a rare occasion, method references are longer than the
lambda. 
    - this is most likely to occur when the method is in the same
    class as the lambda
    
    
        EX:
        
        service.execute(SuperLongClassName::action);
        
        service.execute(() -> action());
        

- real world example

    java.util.Function interface provides a generic static factory to
    return the identity function:
    
        Function.identity();
        
    It's usually cleaner/shorter NOT to use that method but to 
    code the equivalent lambda inline:
    
        x -> x
        
## METHOD REFERENCE TYPES

| Method Ref Type | Example | Lambda Equivalent |
| --- | --- | --- |
| Static | Integer::parseInt | str -> Integer.parseInt(str) |
| Bound | Instance.now()::isAfter | Instance then = Instance.now(); t -> then.isAfter(t); |
| Unbound | String::toLowerCase | str -> str.toLowerCase() |
| Class Constructor | TreeMap<K,V>::new | () -> new TreeMap<K,V> |
| Array Constructor | int[]::new | len -> new int[len] |


### STATIC
We've already covered these for the most part. 

### BOUND
the receiving object is specified in the method reference
- similar in nature to static references
- the function object takes the same arguments as the referenced method

### UNBOUND
- the receiving object is specified when the function object is applied
via an additional parameter before the method's declared params. 
- most commonly used as mapping/filter functions in stream pipelines

### CONSTRUCTOR REFERENCES
two kinds, for classes and arrays
- serve as factory objects
