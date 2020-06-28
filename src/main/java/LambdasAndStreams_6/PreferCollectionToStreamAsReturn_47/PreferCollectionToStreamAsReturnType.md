# Item 47: Prefer Collection to Stream as Return Type
Collection (or an appropriate subtype) is usually the best
option for a return type for a public, sequence-returning method.

## Pre-Stream Return Type Options for Element Sequences
- Collection Interfaces  
    - Collection, Set, and List
- Iterable
- array types

USE CASES
- Collection Interfaces were the norm <br>
- Iterable was used to enable for-each loops, or when 
the returned sequence couldn't implement a Collection method
(i.e. contains(Object))
- arrays were used predominantly for primitive values or where there
were strict performance requirements (like database code)


## Post-Streams
- Good code still requires the use of iteration, streams haven't 
made it extinct quite yet. 

### Stream + Iterable 
- Stream doesn't extend Iterable, so there is no good way 
to use a for-each loop over a stream. 


    EX 
    (doesn't compile) 
    for (ProcessHandle ph : ProcessHandle.allProcesses()::iterator) {
        // Prcoess the ph
    }    
    
    Results in error: 
    error: method reference not expected here 
    (it points out the ProcessHandle.allProcesses()::iterator) line.
    
    
You can force it to work using a cast

    EX:
    
    for (ProcessHandle ph : (Iterable<ProcessHandle>)
                ProcessHandle.allProcesses()::iterator)
                
This is fucking ugly. Using this would cause spontaneous combustion in
any dev who had to maintain this. 


#### Adapter Method Solution
This is a handy dandy little Adapter (Stream<E> -> Iterable<E>)

    
    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }
    
- this adapter allows iteration over a stream w/ a for-each
statement


    EX: 
    
    for (ProcessHandle ph : iterableOf(ProcessHandle.allProcesses())) {
        // do the work
    }    
    

The door swings both ways. There may be cases where an API only 
provides an Iterable, and we want to process the sequence using a 
stream
- Yet another handy dandy Adapter


    public static <E> Stream<E> streamOf(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

## Deciding on Return types
- methods that will return a sequence of objects that will ONLY be
used in stream pipelines should obviously return a STREAM
- methods that will return a sequence of objects that will ONLY be
used for iteration should obviously return an Iterable

Public API Robustness
- flexibility is the goal. 
- provide functionality for users who want to write stream pipelines as
well as those who want to write for-each statements
- The Collection interface is the best way to accomplish this
    - subtype of Iterable
    - has a stream() method.
- PLAN B is Arrays
    - Arrays.asList() -> iteration
    - Stream.of()  -> stream pipeline support. 
    
    
### Arrays vs. Collections
If the sequence is small enough to fit easily in memory use standard
collection impl
- ArrayList or HashSet
- do NOT store large sequence in memory just to return it as collection.
    - consider special-purpose collection
    
#### Power Set Example. 
If a set has 'n' elements, then the power set has 2^n.
- In other words, the power set if very large. 
- We shouldn't ever consider storing power sets in standard collection
impls.  

Power Set of {a,b,c} is:
- {, {}, {a}, {b}, {c}, {ab}, {ac}, {bc}, {abc}}

Impl Custom Collections Using AbstractList
- use index of each element in power set as a bit vector
- nth bit in index, indicates the presence/absence of the nth 
element from the source set.
- there is a "natural mapping" between binary numbers from 
    - 0 -> (2^n) - 1
    - and power set of n-element set. 
    
    
    EX:
    (returns power set of an input set as a custom collection)
    public class PowerSet {
        public static final <E> Collection<Set<E>> of(Set<E> set) {
            List<E> sourceSet = new ArrayList<>(set);
            if (sourceSet.size() > 30) {
                throw new IllegalArgumentException("Set Too Large " + set);
            }
            return new AbstractList<Set<E>>() {
                @Override
                public int size() {
                    return 1 << sourceSet.size();   // 2 to the power of sourceSet.size()
                }
                
                @Override
                public boolean contains(Object obj) {
                    return obj instanceOf Set && sourceSet.containsAll((Set)obj);
                }
                
                @Override
                public Set<E> get(int index) {
                    Set<E> result = new HashSet<>();
                    for (int i = 0; index != 0; i++, index >>= 1) }
                        if ((index & 1) == 1) {
                            result.add(sourceSet.get(i));
                        }
                    }
                    return result;
                }
            }
        }
    }

### Disadvantages of Collection
In the example above PowerSet.of() throws an exception if the input set has more than 30 elements
- Collection has an int-returning size() method, which bounds the length of the 
returned sequence
    - Integer.MAX_VALUE or (2^31) - 1
- This means that we can't return values that are larger or infinite, which is often desirable in
stream-based workflows. 

## Writing Collection impl on top of AbstractCollection
Requires implementation of the following methods
- get() (this is a transitive requirement from the Iterable interface)
- contains()
- size()

Usually easy/straightforward, but there are cases where it isn't. 
- contents of sequence aren't predetermined before iteration takes a place
    - return a stream or iterable instead of Collection
    - maybe even overload them and return both (stream and iterable)

## Choosing return type based on LOE (Level of Effort)
Example 1:
- writing a method that returns all of the contiguous sublists of an input list
    - this takes 2 lines of code to generate the lists and store them in a standard collection
        - memory requirements are quadratic compared to the size of the source list.
            - Yes, that is a lot
            - No, we shouldn't implement this regularly. 
    - a custom collection (like w/ Power Set) 
        - possible, but quite a bit harder because there isn't a skeletal Iterator impl to build on. 
    - fairly straightforward to impl a stream of all sublists of an input list
    
Impl Stream of Sublists Of An Input List
- sublist that contains first element of a list = "prefix" of the list. 
    - EX: prefixes of (a, b, c) are (a), (a, b), and (a, b, c)
- sublist that contains last element of a list = "suffix" of the list
    - EX: suffixes of (a, b, c) are (a, b, c), (b, c), and (c)
    
This should help you deduce an insight:
- the sublists of a list are sipmly the suffices of the prefixes (or the prefixes of the suffixes)
and the empty list. 


    EX: 
        public class SubLists {
        
            public static <E> Stream<List<E>> of(List<E> list) {
                return Stream.concat(Stream.of(Collection.emptyList()),
                    prefixes(list).flatMap(SubLists::suffixes));
            }
            
            private static <E> Stream<List<E>> prefixes(List<E> list) {
                return IntStream.rangeClosed(1, list.size())
                    .mapToObj(end -> list.subList(0, end));
            }
            
            private static <E> Stream<List<E>> suffixes(List<E> list) {
                return IntStream.rangeClosed(0, list.size())
                    .mapToObj(start -> list.subList(start, list.size()));
            }
        }
       
- Stream.concat() adds the empty list into the returned stream
-  flatMap()
    - takes prefix element as an input, and creates a stream from it. 
    - the intermediate operation of that inner stream is to generate the suffixes of that
    prefix
    - it's "terminal" piece is to flatten the individual stream into a stream of consistengin of
    all of the suffixes of ALL of the prefixes
- prefixes/suffixes are generated by mapping a stream of consecutive int values
    - IntStream.range(), IntStream.rangeClosed()
    - these are "roughly" equivalent to a for-loop on int indices. 

    
    EX:
    
    (subList) impl is similar to a nested for-loop
    
    for (int start = 0; start < source.size(); start++) {
        for (int end = start + 1; end <= source.size(); end++) }
            System.out.println(source.subList(stard, end));
        }
    }
    
- This could be translated to a stream, but its probably going to be less readable.
    - its similar to the stream code in Cartesian Product example in Item 45
    
    
        EX:
        
        public static <E> Stream<List<E>> of(List<E> list) {
            return IntStream.range(0, list.size())
                .mapToObj(start ->
                    IntStream.rangeClosed(start + 1, list.size()) 
                        .mapToObj(end -> list.subList(start, end)))
                .flatMap(x -> x);               
        }
        
NOTE: neither the stream nor the for-loop prior actually emit the empty list. 
- we'd need to use concat (like we did w/ the best practices example before it) or...
- replace 1 by (int) Math.signum(start) in the rangeClosed() call. 

### Performance Considerations of LOE-based approach
Either of these will work, but it isn't very flexible. 
- users may need to use Stream->Iterable adaptations to use a stream in places where iteration
is better. 
    - clutters up client code
    - it is 2-3x slower 
    
In comparison:
- while a purpose-built Collection impl is going to be much more verbose, it will run about
1-2x as fast as the stream-based impl on average.
    

## Best Practices
- Always try to accommodate both users who want to process sequences of elements as streams and
those who want to iterate over them
    - Standard Collection is the first choice 
        - if elements are already in a collection
        - if the memory overhead is light to create one
    - Custom Collection (i.e. PowerSet example)
    - If Collection not feasible, then return stream or iterable
        - whichever appears more natural
