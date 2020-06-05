# Item 33: Consider Typesafe Heterogeneous Containers.

## Common Uses for Generics
1. Collections
    - Set\<E>
    - Map\<K,V>
1. Single-Element Containers
    - ThreadLocal\<T> 
    - AtomicReference\<T>
    
In all of these cases, the container is what is parameterized. 
- limited to a fixed number of type parameters per containers. 
- (Usually you want this...)

### ... But Sometimes You Don't. 
More Flexible Use Case
- EX: Database row can have an arbitrary number of columns, making type
safety hard to achieve (becausethe columns are of varying types)

## KEY vs. CONTAINER
To achieve flexibility in typesafe use cases
- we parameterize the KEY instead of the CONTAINER.
- present parameterized KEY to container to insert/retrieve a value. 
- Generic Type system is used to guarantee (or provide type safety) that
the type of value agrees with the key.

See Code in Favorites package for example.

NOTES:
- Allows a client to get()/put() a favorite instance of many types. 
- the parameterized key is associated w/ a Class object. 
    - Class\<T> is a generic. 
    - i.e. String.class is of type Class\<String>
    - i.e. Integer.class is of type Class\<Integer>
    
### TYPE TOKEN
This is a class literal that is passed among methods to communicate
both compile-time AND runtime type information. 

Type tokens may be unbounded or bounded. 
- the examples in Favorites are all unbounded, which means that the 
mutators will take any type of class. 

#### BoundedType Tokens -> Annotations
The Annotations API makes extensive use of bounded type tokens. 

(Annotated elements are typesafe heterogeneous containers whose
keys are annotation types)

    EX: 
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationType);

The annotationType arg is a bounded type token representing an 
annotation type. 
- the method returns the element's annotation of that type (or null
if it doesn't have one)

However, what if  the type isn't known at compile time? 
i.e. Class<?>. 
- If we try to cast this as Class<? extends Annotation>, the cast is 
unchecked so it generates a compile-time warning. 

SOLUTION -> use asSubclass(), an instance method of Class that can 
dynamically perform the type of cast required. 
- it casts the Class object on which it is called to 
represent a subclass of the class represented by its arguments. 
- if it succeeds - it returns the argument
- if it fails - ClassCastException

See BoundedTypeTokenExamples#Example for more information 

### HETEROGENEOUS
This just means "of different types". Heterogeneous type containers, unlike
normal collections/containers, contain objects of many different types.

We achieve this in the example, by providing an unbounded wildcard for the
KEY parameter inside the Map. 
- this works around the problem we had with unbounded wildcards as a 
type parameter (i.e. in Item 31)

NOTE: Java.util.Collections has many methods used for similar purposes
to promote type safety on the put(), rather than waiting for the get(). 
- this is especially useful when data won't be read for quite some time
after it was written. 
- we want write time guarantees that the data is in good shape. 


