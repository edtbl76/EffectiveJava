# Item 54: Return empty collections or arrays not nulls. 

## Example of "null" used to represent an empty collection

    
    // Returns nul to indicate an empty collection (BAD)
    private final List<Cheese> cheesesInStock = ...;
    
    /**
        @return a list containing all of the cheeses in the shop, 
            or null if no cheeses are available for purchase.
    */ 
    public List<Cheese> getCheeses() {
        return cheesesInStock.isEmpty() ? null : new ArrayListt<>(cheesesInStock);
    }

## Consequences
The reason this is bad, is that we end up having to use "circumlocution" in almost every use of the
method that returns null in stead of an empty collection/array

    EX: 
        List<Cheeses> cheeses = shop.getCheeses();
        if (cheeses != null && cheeses.contains(Cheese.STILTON)) {
            System.out.println("Jolly good, just the thing!");
        }
        
- Error Prone. 
    - if dev doesnt' remember to write that the null check, then we can get an NPE
- complicates the impl of the method that returns the container. 

### "The Cost Argument"
There is some debate that returning a null is preferred to empty containers because it avoids the
expense of allocating the empty container. 

REBUTTLE
1. This is a good example of silly optimization. I'll quote "it is inadvisable to worry about
performance at this level unless measurements have shown that the allocation in question is a
real contributor to performance problems" (ITEM 67) 
1. it IS possible to return empty collections and arrays w/o allocating them. 


    EX: 
        public List<Cheese> getCheeses() {
            return new ArrayList<>(cheesesInStock);
        }    
        
    
    
### Pigs Fly, and Performance is an Issue. 
If either of these happens, allocations can be avoided by returning the same IMMUTABLE
empty collection repeatedly
    - (immutable objects can be shared freely) ITEM 17
    
    
    EX: 
        // Insanely picky performance optimization
        public List<Cheese> getCheeses() {
            return cheesesInStock.isEmpty() ? Collections.emptyList() : new ArrayList<>(cheesesInStock);    
        }
        

- use Collections.emptySet() for sets
- use Collections.emptyMap() for maps. 


    (The Array Way) 
    
        private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];
        
        public Cheese[] getCheeses() {
            return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);    
        }

NOTE: This is an optimization. If you are going to use it... DOCUMENT IT


    
## The Right Way (Yep, we've already seen how to do this...)
Collections

    public List<Cheese> getCheeses() {
        return new ArrayList<>(cheesesInStock);
    }
    
Arrays
    
    public Cheese[] getCheeses() {
        return cheesesInStock.toArray(new Cheese[0]);
    }
        

NOTE: Studies (dude named Shipilev) have shown that trying to preallocate toArray() to
improve performance actually degrades it

    EX: 
        return cheesesInStock.toArray(new Cheese[cheesesInStock.size()]);
    