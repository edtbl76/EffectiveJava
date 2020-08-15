# Item 58: Prefer for-each loops to traditional for loops.

## Traditional ForLoop Examples
Collection

    EX: 
    
        for (Iterator<Element> iterator = collection.iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();
            .... // Do unto element
        }
        

Array

    EX:
        for (int i = 0; i < array.length; i++) {
            ..// Do unto array[i]
        }

### UPSIDES
- better than while loops!

### DOWNSIDES
- iterator and index variables are just additional structural code. 
    - (business logic is concerned w/ the elements themselves)
- error prone
    - it's easy to accidentally use the index/iterator variable instead of the element. 
- the type of container (array or collection) has different mechanics
    - goes back to a distracting focus on the structural aspects of the code, as opposed to the
    problem-solving logic. 

## For-Each
- a.k.a. "enhanced for statement"
- SOLVES classic for problems
    - hides iterator/index variables
    - uniform syntax regardless of the container type (array vs. collection)
  
  
    EX: 
        for (Element element : elements) {
            ..// Do unto element
        }  
        
- clearer, more flexible
- less error-prone
- NO PERFORMANCE PENALTY
    - (This is one of the few cases in Java where something helps you for FREE)
        

## Nested Iteration (Classic For Loops)

### Loops of Different Sizes
There is a bug in this code. 
- the i.next() call is going to happen too many times. 
    - if this were called in the outer loop, it would be called once per suit
    - however, since it's called in the INNER loop, it's called once per CARD. 
    
- This is a classic case where the outer loop is associated w/ an iterator that has fewer elements than the 
iterator associated w/ the inner loop
    - by calling next on both structures in the inner loop, as soon as we reach iteratorOuter.size(), it
    throws "NoSuchElementException"

    EX: 
    
        enum Suit { CLUB, DIAMOND, HEART, SPADE }
        enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, 
                    NINE, TEN, JACK, QUEEN, KING }
        static Collection<Suit> suits = Arrays.asList(Suit.values());
        static Collection<Rank> ranks = Arrays.asList(Rank.values()));
        
        List<Card> deck = new ArrayList<>();
        for (Iterator<Suit> i = suits.iterator(); i.hasNext()); ) {
            for (Iterator<Rank> j = ranks.iterator(); j.hasNext()); ) {
                /*
                    The bug lives HERE!
                */
                deck.add(new Card(i.next(), j.next()));
            }
        }
    
### Synchronized Inner/Outer
- Same scenario/bug as above, but we're using the same collection/iteration in each loop, so it
will have a different outcome. 

- Presumably, this code would intend to create 1 of the 36 possible double dice rolls. 
    - however, since the loops move in unison together in the inner loop, we'll only get 6 outcomes.


    EX: 
        enum Face { ONE, TWO, THREE, FOUR, FIVE, SIX }
        ...
        Collection<Face> faces = EnumSet.allOf(Face.class);
        
        for (Iterator<Face> i = faces.iterator(); i.hasNext();) {
            for (Iterator<Face> j = faces.iterator(); j.hasNext();) {
                System.out.println(i.next() + " " + j.next());
            }
        } 
    
### Crappy Solution 
- This relocates the i.next() call to live ONLY in the outer loop, and then we re-reference the
variable in the inner loop (where it is still in scope)
    - the solution is functionally sound, but it is easy to screw up and it is an abomination to look at.
    

    EX: 
    
        for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
            Suit suit = i.next();
            for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
                deck.add(new Card(suit, j.next());
            }
        }


## Nested Iteration - ForEach (The GOOD Solution)
This a better solution to the problem(s) above
- the details of the loops and containers are abstracted away, so that we have a nice terse
solution focusing on the business logic. 
    
    
    EX:
    
        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }
        
        
## When For-Each CAN NOT BE USED

### Destructive Filtering
- when traversing a collection in a manner that will remove SELECTED elements, you need to use
an explicit ITERATOR so that you can call its remove() method. 
    - NOTE: Java 8 added removeIf() to Collection which provides a workaround for explicit traversal

### Transformation 
- if some/all values of a list/array's elements need to be updated/replaced then:
    - (list) iterator is required
    - (array) index variable is required
    
### Parallel Iteration
- traversal of MULTIPLE collections in parallel requires explicit control over the iterator/index vars
so that ALL iterator/index vars can be advanced in lockstep

## ForEach + Iterable
- ForEach can iterate over collections, arrays and ANY object that implements Iterable interface. 
    - requires that you write your own Iterator from scratch
    - STRONGLY recommended that any custom TYPE that represents a GROUP of elements should impl
    Iterable interface (even if it doesn't impl Collection)
    
    

    EX: (Iterable<E> interface) 
    
        public interface Iterable<E> {
            // returns an iterator over the elements in this iterable
            Iterator<E> iterator();
        }