# Item 37: Use EnumMap instead of Ordinal Indexing. 
Sometimes, code will use the ordinal() method to index into an index-based
collection

## Ordinal Indexing
Look at BasicArrays.OrdinalIndexedExample AND
        MultiDimensionalArrays.Phase
- Don't do this (i.e. don't use ordinal() to index into an array)

PROBLEMS
- the first issue is that arrays aren't compatible w/ generics
    - this leads to an unchecked cast that will compile uncleanly with
    warnings. 
- array doesn't know what the index represents, so we have to label the
output manually
    - error prone, hard to manage
- WORST PROBLEM: when accessing arrays indexed by ordinals, its our
responsibility to use the correct int value. 
    - THERE IS NO TYPE SAFETY
    - this is a clear cut case of "Unexpected Output" which means silent
    semantic failures/bugs. This is berry berry bad. 
    - BEST case scenario is that the value is out of bounds and it just
    throws an Exception.
    
    
### Multidimensional Arrays
Look at MultiDimensionalArrays.PhaseOrdinal.
- this has all of the same limitations of a single dimension array.
    
## EnumMap
The array we are trying to index into is effectively a map from the 
Enum to some value. 
- so why aren't we using a map??

EnumMap!
- part of java.util.
- very fast, specifically designed to use Enums as keys. 
- takes a BOUNDED TYPE TOKEN as a parameter (remember those!)


Look at BasicArrays.EnumMapExample.
- Shorter, clearer, safer, and comparable in speed to original version. 
- removes the unsafe cast. 
- no need to label output manually
    - map keys are enums that have toString() methods. 
- impossible to compute array indices incorrectly

### MultiDimensionalArray
Look at MultiDimensionalArray.PhaseEnumMap for a solution with
multidimensional arrays. 
(same basic concept as single dimension arrays)

In our example we create a Map of Maps. 
Map 1 maps the Source Phase to Map 2
Map 2 maps the Destination Phase to the Transition. 

Despite the complexity of the initialization code, if you look at
MultiDimensionalArray.PhaseEnumMapV2, we demonstrate the value of that
complexity, because the enum is very extensible (and with very little
effort)

- This combines the StrategyEnum approach. 

### SPEED COMPARISON
EnumMap actually uses an ordinal indexed array internally, but this
is abstracted from the developer as an implementation detail. 
- Naturally a Map is slower than the array, but we are getting the 
"richness" and type safety of the Map. 


## STREAM-BASED APPROACH
SOLUTION 1: 
This is considered naive/brute force because it is unlikely to produce
an enumMap

    
    System.out.println(Arrays.stream(garden)
        .collect(groupingBy(plant -> plant.lifeCycle)));
        
This is choosing its OWN map implementation, and it isn't going to be
an EnumMap. 
- doesn't match the time/space explicit to EnumMap. 

SOLUTION 2:
Use the overloaded version of groupingBy() that allows us to specify the
map implementation (via the mapFactory parameter)

    System.out.println(Arrays.stream(garden)
        .collect(groupingBy(plant -> plant.lifecycle, 
            () -> new EnumMap<>(LifeCycle.class), toSet())));
            
NOTE: This is an optimization that is most likely reserved for heavy use
of the map being created. 


### Stream vs. EnumMap
In the stream-based versions, a nested map is only created if the "garden"
contains 1 or more plants w/ that lifecycle. 

In the EnumMap version, a nested Map is eagerly created for each plant
lifecycle regardless of whether or not they are used. 

