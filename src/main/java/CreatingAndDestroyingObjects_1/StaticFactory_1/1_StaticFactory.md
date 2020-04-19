# 1. Static Factory Methods > Constructors
This is NOT the same thing as Factory Method or AbstractFactory 
- can be instead of or in addition to Constructors

## PRE JAVA 8
For an INTERFACE named "Device", static factory methods are usually placed in a NONINSTANTIABLE COMPANION class
called "Devices"
    - ex Collections Framework has 45 impls of all of its interfaces. 
    - almost all of these are exposed via static factory methods in a single noninstantiable class.

## JAVA 8 AND LATER
Interfaces can contain static methods, so there is less reason to use NONINSTANTIABLE COMPANION
- however, interface methods are public, so it may be necessary to place impl code behind static methods in a separate
package-private class, 

## ADVANTAGES
1. has a name (unlike a constructor)
    - class can only have 1 constructor per given signature
    - constructors can simply have separate names for different use cases
1. static factory isn't required to create a new object each time they're invoked
    - supports immutable classes to use preconstructed instances
    - allows caching of instances as they are constructed
        - dispense same instances repeatedly to reduce creation of duplicate objects
    - improves performance if equivalent objects are requested often
        - (especially if they are expensive to create)
    - allows a class to have strict control over what instances exist at any given time
        - (these are called INSTANCE-CONTROLLED classes)
        - allows guarantees that a class is a singleton (or that it can't be instantiated)
    - allows immutable value class to guarantee that no 2 equal instances exist
        (i,e, via a.equals(b))
        - Flyweight Guarantee
1. unlike constructor, can return an object of ANY SUBTYPE of the return type
    - flexibility in object type
    - API can return objects w/ making classes public
    - useful in INTERFACE-BASED FRAMEWORKS
    - allows client to refer to returned object by INTERFACE rather than the IMPL CLASS
        - considered good practice
1. class of returned object can vary from call to call/release to release
    - any subtype of declared return type is allowed
1. the class of the returned object doesn't have to exist when the class containing the method is written
    - this is the basis of SERVICE PROVIDER FRAMEWORKS
        - ex JDBC (Java Database Connectivity API)
        - these are systems which providers impl a service and the system makes the impls available to clients
            - decouples clients from implementation
       
    
SERVICE PROVIDER FRAMEWORKS
1. SERVICE INTERFACE
    - represents an impl
2. SERVICE ACCESS API
    - clients use this to get instances of service
3. PROVIDER REGISTRATION API
    - providers use this to register implementations
4. (Optional) - SPI = Service Provider Interface
    - describes a factory object that produces instances of 
    the service interface.
    - w/o this impls are instantiated reflectively
    
BULK vs. CONCEPTUAL WEIGHT
- bulk is the actual size of an API
- conceptual weight is the # + difficulty of concepts that devs must master in order to actually use it

## DISADVANTAGES
1. classes without public or protected constructors can't be subclassed
1. hard for developers to find.
    - they dont't stand out in API documentation the way constructors do
1. doesn't scale well w/ large number of parameters
    - But neither does a constructor

## COMMON NAMES FOR STATIC FACTORY METHODS

from
- TYPE CONVERSION METHOD that takes a single parameter and 
returns a corresponding instance of this type


    EX: Date d = Date.from(instance);
    
of
- AGGREGATION METHOD that takes multiple params and returns 
an instance of "this" type that incorporates them


    Ex: List.of(1, 2, 3);
    
valueOf
- more verbose alternative to "from" and "of" 


    EX: BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
    
instance/getInstance
- returns an instance described by its parameters (if they exist) 


    EX:  SomeObject someObject = SomeObject.getInstance(options);
    
create/newInstance
- like 'instance/newInstance', but the method guarantees the creation of a new instance on each call

    
    EX: SomeObject someObject = SomeObject.newInstance(options);
    
get\<Type>
- like getInstance, but used if the factory method is in a different class. \<Type> is the type of
object returned by the factory method. 


    EX: FileStore fs = Files.getFileStore(path);
    
new\<Type>
- like newInstance, but used if the factory method is in a different class. \<Type> is the type of object 
returned by the factory method.


    EX: BufferedReader br = Files.newBufferedReader(path);
    
\<type>
- concise alternative to get\<Type> and new\<Type> 


    EX: List<Complaint> list = Collections.list(otherList);

    

