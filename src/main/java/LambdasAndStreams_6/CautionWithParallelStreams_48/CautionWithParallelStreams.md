# Item 48: Use caution when making streams parallel
Parallelization = Performance Optimization Only. 
- test BEFORE and AFTER to make sure that the optimization is worth the effort.

## Brief history of concurrency in java
- 1996 Java is released
    - built-in support for threads
    - synchronization
    - wait()/notify()
- Java 5
    - introduces java.util.concurrent lib
        - concurrent collections
        - executor framework
- Java 7 
    - fork-join package
        - high-performance framework for parallel decomposition.
- Java 8
    - Streams API
        - can be parallelized w/ a single call to paralle() method
        
## LIVENESS FAILURE 
- Safety and liveness problems are still issues in concurrent
programming regardless of the new methods that are created to 
simplify it. 

    
    EX:
        
    (Stream-based program to generate first 20 Mersenne numbers) 
    
    public static void main(String[] args) {
        primes()
            .map(prime -> TWO.pow(prine.intValueExact()).subtract(ONE))
            .filter(mersenne -> mersenne.isProbablePrime(50))
            .limit(20)
            .forEach(System.out::println);
    }
    
    private static Stream<BigInteger> primes() {
        return Stream.iterate(TWO, BigInteger::nextProbablePrime);
    }
    
- trying to call parallel() against this will cause the CPU to spike
indefinitely
    - (this is a liveness failure)
    - will usually crash unless you kill it. 
    
WHY?
- this is because we are sourcing the stream w/ Stream.iterate() and
we have used the limit() intermediate operation. 
    - it will take nearly 2x as long to find each Mersenne number
    - cost of computing a single extra element is ~= to the cost of
    computing all of the previous elements combined. 
        - perf is killed, CPU dies, RIP.
        
NOTE: Even if this completed, it would have suffered from a Safety error. <br>
the primes would have been printed out of order (non ascending) because forEach() doesn't
preserve the order. 
- we would replace this w/ forEachOrdered() 
    - which traverses parallel streams in ENCOUNTER ORDER
    
## Safety Failures
Safety failures are when we have unpredictable behavior or incorrect results. These are commonly
caused by parallelizing pipelines that include:
- mappers
- filters
- programmer-supplied function objects that fail to adhere to their specs. 

## Streams API Specifications (In terms of liveness and safety)
The Streams API places strict requirements on function objects

EX: accumulator and combiner functions passed to reduce() must be
- associative
- non-interfering
- stateless
    
Sequential pipelines will still probably work if you break the rules, but parallel pipelines will
fail miserably.
    
## What data structures work best with parallel()
- ArrayList, HashMap, HashSet, ConcurrentHashMap
- arrays, int ranges, long ranges

WHY?<br>
These structures can all be accurately and cheaply split into 
smaller "subgroups" of any desired sizes
- this is critical, because the cost of dividing tasks into subsets of work is necessary. 

(In rocket science, the biggest challenge to gaining liftoff is that rockets have to lift
their own fuel.) <br>
(In thread parallelization, the biggest challenge is ensuring that the work to divide the
tasks into parallel threads isn't more expensive than doing the work, otherwise we
end up with a net loss... and our rocket crashes.)

## spliterator()
a method on Stream and Iterable that provides an abstraction that divides up work for 
parallelization.

### Custom Stream, Iterable or Colletion impl w/ parallelization
When writing custom implementations for a stream pipeline that will use parallelization, it is
necessary to override the spliterator() method
- TEST parallel performance of the streams EXTENSIVELY
- this is hard. 

## Locality-of-Reference
This is another characteristic shared by the forementioned structures. 
- They all exhibit good-to-excellent locality-of-reference when processed sequentially
   
This means that sequential element references are stored together in memory. 
- objects referred to by those references may not be close to one another in memory, which REDUCES
locality-of-references

Without good Locality Of Reference, threads spend a lot of time idle
- waiting for data to be transferred from mem -> CPU cache. 

Best Locality Of Reference are primitive arrays 
- the data is stored contiguously in memory. 

## Terminal Operation + Parallelization
If most of the work of the pipeline is performed in the terminal operation, then parallelization is
going to have a limited effect. 

### Reductions + Short Circuits
Reductions and Short-circuiting operations are the preferred terminal operations when you are going to
impl parallelized streams.

Reductions are operations where all of the elements emerging from the pipeline are combined using
- Stream's reduce() methods or
- min(), max(), count() and sum()
   
Short-Circuit operations
- anyMatch(), allMatch(), noneMatch()

NOTE: collect() is a "MUTABLE Reduction", which is NOT a good candidate for parallelism, because
the overhead for combining collections is costly. 
- (too much fuel, if we stick w/ the rocket comparison)
   
## Practicality of Parallelism
Don't use parallelism unless there is actually a need to do so. 
- in most cases, you aren't going to see a measurable increase in performance from 
parallelization unless the pipeline is doing enough work to offset the cost of 
splitting up the stream 

A ROUGH estimate
- # of elements in stream x "lines of code" executed per element >= 100,000

- most parallel stream pipelines run in a common Fork-Join pool. 
- 1 bad pipeline can impact performance of the performance of other unrelated parts of the system

## ... But it DOES work. 
While the odds are stacked against you, it IS possible to achieve near-linear speedup in #
of cores simply by adding a parallel() call to a stream pipeline.
- data processing and ML workflows are commonly driven to use parallel pipelines (especially in 
terms of the element to LOC ratio.)


    EX:
        (Prime-counting stream pipeline - no parallelization)
        calculates pi(n)
        
        public static long pi(long n) {
            return LongStream.rangeClosed(2, n)
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
        }
        
- this takes 31 seconds to compute pi(10^8)

    
    EX:
        (parallel version)
        public staic long pi(long n) {
            return LongStream.rangeClosed(2, n)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
        }
 - this takes 9.2 seconds. Not bad. 
 
 
## Parallelization of a Stream of random numbers
- Prefer SplittableRandom() to ThreadLocalRandom()

SplittableRandom() was explicitly designed for the parallelization of streams of 
random numbers
- potential for "linear" speed increases. 

ThreadLocalRandom() was designed for use by a single thread
- it CAN be adapted for parallelism, but the limitations of the underlying implementation will
never be as fast as the alternative. 

Do NOT use Random
- this synchronizes on EVERY operation which would result in 
    - "excessive, parallel-killing contention"
    
        
   
## Best Practices
- Above all else, do NOT parallelize stream pipelines indiscriminately.
    - You must ALWAYS preserve the correctness of the computation 
        - i.e. protect against safety failures
    - it should result in an increase in speed
        - parallelization requires extra work, more code to manage and increased complexity
        - if it doesn't increase performance, then it doesn't need to be there. 
    - the cost of screwing this up can be a DISASTER
- Generally, performance gains from parallelism are best on streams over
    - ArrayList
    - HashMap
    - HashSet
    - ConcurrentHashMap
    - arrays
    - int ranges
    - long ranges
- favor good locality of reference to avoid idle time
- parallelizing a pipeline is unlikely to increase its performance if
    - source is Stream.iterate()
    - intermediate operator limit() is used.
- parallelization can even decrease performance if done carelessly
    - liveness failures
- parallelization can lead to incorrect results/unpredictable behavior
    - safety failures.
- make sure that the work being done is substantial enough to benefit from parallelization
    - will it offset the cost of dividing the work? 
- Use SplittableRandom for parallelization of streams of random numbers.
- test the living shit out of it
- test it again. 
- test test test
- test before and after or "across" the implementation so you can evaluate whether or not 
the juice is worth the squeeze