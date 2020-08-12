# Item 51: Design method signatures carefully

## Choose method names carefully
- obey standard naming conventions (Item 68)

Primary Goal:
- understandable name
- naming is consistent w/ other names in package

Secondary Goal:
- consistent with broader consensus where it exists
    - (i.e platform, company, business domain, whatevs)
    
Additional Goals:
- Avoid long method names
- Use Java lib APIS for guidance when all else fails

## Don't overuse convenience methods
"When it doubt, leave it out."
- too many methods makes code hard to learn/test/maintain

For each action supported by class/interface
- provide ONE fully functional method
- shorthand should only be provided if the methods are used very
often. 

## Avoid long parameter lists
"No more than four"

Even worse are long sequential parameters of the same type
- transposition mistakes become semantic errors, because the
code will compile but misbehave at runtime.

### Three Techniques for solving long parameter lists
#### 1 Subdivision
- break up larger method into smaller methods
- each "shard" method will have a subset of the parameters

PRO
- reduces method count by increasing orthogonality
- EX: java.util.List interface
    - doesn't have methods to find first() or last() index of
    an element in a sublist. 
        - (This would require 3 parameters)
    - provides subList() method
        - takes 2 parameters
        - returns a VIEW of a sublist
        - can be combined w/ indexOf() or lastIndexOf()to get
        the first and last indexes of a sublist
            - (each have single parameter)

CON
- "too many methods" can make it hard to understand

#### 2 Helper Classes
- Helper classes can be used to hold groups of parameters
- usually static member classes (Item 24)

USE CASE:
- this works well if a frequently occurence sequence of paramters
is seen to represent some distinct entity. 
    - EX: a Card game where the parameters are "rank and suit"
        - A "Card" helper class would make this easier
        
#### 3 Builder Pattern!
(Item 2)
- This is especially useful if you have a gaggle of parameters, 
many of which are optional 
    - define an object that represents ALL of the parameters 
    - client makes setter calls on the object that enables the
    desired parameters
    - client invokes an apply/execute method
        - performs final validation checks on parameters
        - performs computation of the "Builder" to construct the object]
        

## For parameter types, favor interfaces over classes
- If there is an appropriate interface to define a parameter, use it instead of the classes that
represent the concrete implementation
    - EX: Use Map > HashMap
        - Follows robustness principle, allowing you to pass in ANY concrete impl 
        

## Prefer 2-element enum types to boolean parameters
(unless the meaning of the boolean is clear from the method name)
ENUM PROS
- code is easier to read and write
- make it easy to add options later. 

    
    EX:
        public enum TemperatureScale {
            FAHRENHEIT, 
            CELSIUS
        }
        
    1.) 
    
        Thermoter.newInstance(TemperatureScale.CELSIUS) 
        
            is easier to read than
            
        Thermometer.newInstance(true) 
        
        
    2.) 
        Adding KELVIN is really easy w/o having to add another static factory to 
        Thermometer

        


