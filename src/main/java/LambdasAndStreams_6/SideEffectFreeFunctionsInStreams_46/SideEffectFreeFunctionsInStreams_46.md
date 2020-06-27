# Item 46: Prefer side-effect-free functions in streams

Streams API is an API + a paradigm derived from Functional Programming
- in order to obtain the expressiveness, speed ( and maybe parallelizability)
you have to adopt the paradigm as well as the API

## Structure
Structure your computation as a sequence of transformations where the
result of each stage is as close as possible to a PURE FUNCTION of the
previous stage
- this means that any function objects passed in to stream operations
should be free from side-effects

### PURE FUNCTION
A pure function is a function whose result depends ONLY on its
input. 
- no mutable state required
- doesn't update any state.

### BAD EXAMPLE
Some code initializing a frequency table

    Map<String, Long> frequency = new HashMap<>();
    try (Stream<String> words = new Scanner(file).tokens()) {
        words.forEach(word -> {
            frequency.merge(word.toLowerCase(), 1L, Long::sum);
        });
    }
    
Why is this bad?
- "iterative code, masquerading as streams code"

How do I tell?
- all of the work is being done in the terminal operation. 
- a forEach() operation that does more than present the result usually
suggests a "bad smell in the code"

### BETTER EXAMPLE
Still some code initializing a frequency table

    Map<String, Long> frequency;
    try (Stream<String> words = new Scanner(file).tokens()) {
        frequency = words
            .collect(groupingBy(String::toLowerCase, counting()));
    }
    
### for-each vs. forEach()
- for-each loops are one of the most pervasive utilities in computer
science (let alone Java)
- forEach() is a similar terminal operation, BUT
    - it is one of the least powerful terminal operations (if not THE
    weakest)
    - it is DEFINITELY the least stream-friendly

Think about what forEach() does. 
- It is by definition an iterative method, which means its not really
"amenable" to parallelization.

## COLLECTORS
Collectors API has 39 methods
- some have up to 5 parameters. 

### What is a Collector?
Ignoring the Collector interface, we can consider a collector generically
as an "opaque" object that encapsulates a REDUCTION strategy

REDUCTION:
- combing the elements of a stream into a single object. 

In terms of a Collector, the reduction is usually a collection. 
(naming isn't THAT hard)

### Collectors (Stream -> Collection)
There are three collectors that gather stream elements into a "true"
Collection
- toList()
- toSet()
- toCollection(collectionFactory) 
    - returns a dev-specific collection type. 
    
    
    EX:
    
    /* 
        pipeline to build a Top 10 list from our previously
        initialized frequency table. 
    */
    List<String> topTens = frequency.keySet().stream()
        .sorted(comparing(frequency::get).reversed())
        .limit(10)
        .collect(toList());

- the source stream is all of the keys from the frequency table
- we then sort it based on a provided comparator
- we limit the stream to 10 elements
- we collect the stream to a List        
    - toList() assumes that we have a static import for Collectors.


#### Explanation of Comparator
(comparing(frequency::get).reversed())

comparing() is a comparator construction method that takes a key
extraction function. 
- in this case, frequency.get() provides a "word" from our 
frequency table.
- the "extraction" is a lookup to our table, returning the number
of occurrences of the provided word (key) 

reversed() is a method that changes the sorting from most to
least frequent. 
    
### Other Collectors
(There are 36 more)
- Most of them are for collecting streams -> maps. 
    - (harder than collecting to collections)
    - each stream element is associated w/ a KEY and a VALUE
    - multiple stream elements may be associated w/ the same KEY
- simplest is toMap(keyMapper, valueMapper)
    - (params are functions that map stream elements respectively
    to a key or value.)
    - terminates w/ IllegalStateException if multiple stream elements
    map to the same Key.  
     
    
    EX:
    
    (Using a toMap collector to make a map from string to enum)
    private static final Map<String, Operation> stringToEnum =
        Stream.of(Values()).collect(
            toMap(Object::toString, e -> e));
       
#### Handling Key Collisions and the 3-argument toMap() versions
- more detailed forms of toMap(), especially when used w/ groupingBy()
method, provide multiple strategies for handling key collisions

Example 1: Merge Functions<br>
A merge function is an additional argument to toMap in addition to the
keyMapper and valueMapper. 
- merge function is a BinaryOperator<V>
    - <V> is the value type of the map
    - additional values associated w/ a key are combined
    w/ existing value using merge function. 
        - EX: sum or product of all values associated w/ key

Example 2: three-arg form of toMap <br>
make a map from a key to a chosen elemennt associated with that key


    EX
    (Collector to generate a map from a key to the chosen
    element for th ekey) 
    
    Map<Artist, Album> topHits = albums.collect(
        toMap(Album::artist, album->album, maxBy(comparing(Album::sales)))
    );
    
- the 3rd argument is the maxBy() static factory, which is statically imported
from BinaryOperator.
    - converts a Comparator<T> into a BinaryOperator<T> that computes the max implied by the
    specified comparator.
    - in this case the Comparator<T> is created by comparing() (which constructs a 
    comparator), which we pass in Album::sales, which is a key extractor. 
    - (this more or less filters out the values provided by the 2nd argument)
    
Example 3: last-write-wins policy <br>
This is one way to handle key collisions. 

    EX: 
    (Collector to impose a last-write-wins policy to handle key collisions)
    toMap(keyMapper, valueMapper, (oldVal, newVal) -> newVal))
    
#### Four Argument toMap(), the MapFactory
- the 4th argument is a MapFactory, to use when you want to specify the particular map
impl (i.e. EnumMap, TreeMap, etc.)

#### Concurrency
- There are variants of all of the non-4 argument versions that are were designed for 
parallel execution
- the return types are ConcurrentHashMap() instances. 

### groupingBy()
This is the lesser known method provided by the Collectors API
- it returns collectors to produce maps that group elements into categories based on a 
CLASSIFIER FUNCTION
    - the CLASSIFIER takes an element and returns the category that it belongs to or matches with
    - the category becomes the element's map key. 
- the simplest version of groupingBy() takes only a classifier and returns a map whose values
are lists of all the elements in each category (key) 


    EX: 
        in item 45, this is the collector we used in the Anagram program to 
    generate a map from alphabetized words. 
    
    words.collect(groupingBy(word -> alphabetize(word));
    
#### DOWNSTREAM COLLECTOR
- argument provided to groupingBy() in addition to classifier.
- produces a value from a stream containing all of the elements in a category.
- simplest example
    - pass in "toSet()", which results in the creation of a map whose values are sets of
    elements rather than lists. 
- customizable example
    - pass in "toCollection(collectionFactory)" which lets you create collections into which the
    category of elements are placed. 
    - more flexible, but naturally you have to write the Collections yourself. 
- frequency collector
    - pass in "counting()" as the downstream collector associates each category w/ the 
    number of elements in the category rather than a collection that contains the 
    elements. 
    
   
    EX:
       
       (Building a frequency table using a downstream collector)
       Map<String, Long> frequency = words
            .collect(groupingBy(String::toLowerCase, counting()));
            
NOTE: collectors returned by the counting() method were developed ONLY for use as 
downstream collectors. 
- Stream provides the same functionality via the count() method
- this means there is NEVER A REASON TO DO THIS -> collect(counting())
            
#### Map Factory
- in addition to classifier and downstream collector,groupingBy() can take a third argument
that allows you to specify a map factory
    - NOTE: This breaks the standard Telescoping Argument List pattern, because the MapFactory is
    "inserted" as the 2nd argument, displacing the downstream collector
- gives control over the containing map as well as the contained collections
    - i.e. a collector can be specified that returns a TreeMap, whose values are TreeSets. 
    
#### groupingByConcurrent()
overloads the previous examples
- designed for efficient parallelization
- produce ConcurrentHashMap instances. 

### PartitioningBy()
This is groupingBy()'s cousin. 
- takes a PREDICATE instead of a classifier function
- returns a map w/ a key that is a Boolean. 
- also supports overloaded versions that include a downstream collector

## minBy() and maxBy()
- each of these take a comparator and return the min or max element in 
the stream determined by the comparator.
- similar to min() and max() in the Streams API
- they return collectors in the same manner that BinaryOperator.minBy()
and BinaryOperator.maxBy() methods return binary operators.


## Special Methods for use with as Downstream Collectors
(see note on counting())
There are 16 methods that produce collectors intended ONLY for use as downstream collectors
- counting()
- summingInt(), summingLong(), summingDouble()
- averagingInt(), averagingLong(), averagingDouble()
- summarizingInt(), summarizingLong(), summarizingDouble()
- reducing()
- filtering()
- mapping()
- flatMapping()
- collectingAndThen()

So Why Are They There? 
- These all "partially" duplicate functionality that exists in the
Streams API, within the Collectors API. 
- this was intended to "empower" the use of downstream collectors
to create ministreams, internal streams more easily. 

## joining()
operates only on streams of CharSequence() instances such as 
strings. 
- parameterless version that just concatenates all elements
- 1-param version takes a delimiter that concatenates the
elements w/ the specified delimiter. 
- 3-param version takes a prefix and suffix in addition to the 
delimiter
    - generates "bookend" strings like [ this, example, sucks ]


## Best Practices
- forEach() operation should be used only to REPORT the result of a
stream computation
- forEach() should NOT actually perform computation.
    - note, however, taking the results of a stream computation and
    adding them to a pre-existing collection is ok, because we the
    RESULTS of the stream-computation were computed by some preceding
    intermediate operator.
- when using Collectors, it is usually cleaner to import all members
of the Collectors interface, because it makes streams pipelines more
readable.
    - NOTE: (this can be a pain in the ass if your surefire 
    configuration prevents the use of .*)
- the most important collector factories to know
    - toList()
    - toMap()
    - groupingBy()
    - joining()


    

