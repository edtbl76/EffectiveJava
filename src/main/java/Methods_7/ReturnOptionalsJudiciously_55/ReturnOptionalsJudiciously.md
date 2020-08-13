# Item 55: Return Optionals Judiciously

## (Pre Java 8 Solutions for methods that might be unable to return a value)
1. throw an exception
1. return null (assuming that the return type was an object reference type)  

### DOWNSIDES
- Exceptions should be reserved for "exceptional conditions" (ITEM 69)
- throwing exceptions is expensive, because the entire stack trace is captured when it is created
- returning null forces clients to perform null checks or "special case" code to handle a scenario
when null is returned
- if null does return, and it isn't handled, we get an NPE

## Java 8 -> Optionals
Optional\<T>
- immutable container that can hold either a single non-null T reference, or nothing. 
- an Optional w/ nothing in it is "empty" 
- A value in an Optional is called "present"