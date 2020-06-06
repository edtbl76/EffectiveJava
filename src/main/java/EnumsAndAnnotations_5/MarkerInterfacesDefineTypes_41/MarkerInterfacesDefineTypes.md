# Item 41: Use marker interfaces to define types

## MARKER INTERFACE
- interface that contains no method declarations but merely
designates/marks a class that impls the interface as having some property

EX: Serializable Interface is used to indicate that instances of a class
can be written to an ObjectOutputStream. (EX: it can be serialized)

## MARKER ANNOTATIONS v. MARKER INTERFACES
- Common, but incorrect sentiment
    - "Marker Annotations make marker interfaces obsolete"
    
### Marker Interface - Advantage 1    
Marker Interfaces define a TYPE that is implemented by instances of
the marker class. 
- Marker Annotations don't do this.
- allows you to catch errors at compile time that would be deferred
to runtime if using Marker Annotations. 
    - Compile-Time checking is the MAIN INTENT of Marker Interfaces

EX: Serializable
- when using methods such as ObjectOutputStream.writeObject() require
implementation of Serializable, because they serialize the objects passed
in. 
- attempts to serialize inappropriate objects would be detected at
compile time (via type-checking)    

### Marker Interface - Advantage 2
Marker interfaces can be targeted more precisely than marker annotations.
- annotation types can be targeted to ElementType.TYPE, but this supports
any class or interface. 
- a marker can be restricted in a manner that ONLY the type specified as
that marker are appropriate. 
    - by extending the marked interface, we guarantee that only 
    sub-types of the marker interface are applicable.
    
EX: Set is a RESTRICTED MARKER INTERFACE
- applicable only to Collection subtypes, but adds no methods beyond
those defined by Collection. 
    - it DOES refine contracts of several methods, but doesn't add any of
    its own, so its classification as a marker interface is debatable.
    
    
### Marker Annotation - Advantage 1
Marker annotations are a part of the entire annotation concept within Java, 
so in annotation based frameworks they do provide a degree of consistency
and uniformity. 
(i.e. Spring)

### Under Which Circumstances Do I Use Each? 
ANNOTATION
- has to be used if the marker applies to any program element OTHER
than a class or interface
- if marker applies only to classes/interfaces
     - and there will NEVER be a case where we will write a method
     that will ONLY accept an interface of this marking type. 
- when working w/ frameworks that are heavily annotation based. 

INTERFACE
- if marker applies only to classes/interfaces
    - if there will be one or more methods that accept only 
    objects that have the given marking
    - this allows the interface to be used as a parameter type
    for the methods in question
    
### BEST PRACTICES
- When using marker annotations w/ ElementType.TYPE target, 
consider using a marker interface
    - think about the use use cases above and choose what fits both
- Pneumonic
    - "If you don't want to define a type, don't use an interface"
    - "If you DO want to to define a type, then use an interface."