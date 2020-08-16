# Item 67: Optimize Judiciously

## Great Quotes

    More computing sins are committed in the name of efficiency (without
    necessarily acheiving it) than for any other single reason -- including blind
    stupidity. 
        - William A. Wulf


    We SHOULD forget about small efficiencies, say about 97% of the time: 
    premature optimization is the root of all evil.
        - Donald E. Knuth
 
  
    We follow two rules in the matter of optimization: <br>
        Rule 1. Don't do it. 
        Rule 2. (For experts only). Don't do it yet -- that is, not until you
                have a perfectly clear and unoptimized solution. 
        - M.A. Jackson


- These pre-date Java by ~20 years


## Write "Good" programs rather than "fast" ones
- If a good program isn't fast enough, it's design will allow it to be optimized
- information hiding
    - where possible, design decisions are localized within individual components such that 
    individual decisions can be changed w/o rippling out to other independent areas of the system.
    

## Performance Optimization vs. Performance Consideration
- Do NOT ignore performance during design. 
    - impl can be fixed/tuned later, but bad design that limits performance may be impossible to fix w/o
    a rewrite
- "premature optimization"
    - this is taking sufficent/functional code and attempting to make it "faster" w/o a specific requirement
    or statistical target justifying that improvement
- "performance consideration"
    - this is making a design/dev decision to avoid limitation of performance, so that it MAY BE optimized
    at some point in the future
    
## Strive to avoid design decisions that limit performance
- make decisions that leave doors open in terms of performance. 
- make special considerations for components of systems that are hard to change once they have been written
    - (components of a system that are the hardest change after the fact are those that specify interactions
    between components and the outside world)
        - APIS
        - wire-level protocols
        - persistent data formats
        
## Avoid API Warping
API Warping is the process of making a design decision (often destructive) to achieve good performance
- in many cases the perf issue will be solved 
    - future release
    - underlying software

Cost of API warping/supporting it is LONG LONG term.
    
## Consider performance consequences of API design decisions
- TECHNIQUE EXAMPLES
    - public mutable types
        - needless defensive copying (Item 50)
    - inheritance in public class (instead of composition)
        - locks the class to its superclass
        - artifical perf limits on the subclass (Item 18)
    - impl type rather than interface
        - binds you to specific impl, even if better impls are provided later (item 64)
    - be cognizant of underlying code
        - (EX: java.awt.Component.getSize())
            - returns Dimension, mutable <-- SHIT
                - any impl of getSize() allocates a new Dimension instance on every invocation
                - DOES NOT SCALE (many objects can be created)
        - synchronous vs. asynchronous? 
        - what transitive dependencies
        - how does it check boxes in terms of best practices? 
    - avoid API warping


## When/How to optimize
WHEN the program has a 
- clear and concise design
- a well structured implementation
- AND, the performance isn't already good enough

HOW
- measure performance
- optimize
- measure performance

WITH WHAT
- profiling tools
    - runtime info
        - time spent in each method
        - no. of method invocations
    - helps id the need to make algorithm changes
        - poor temporal/spatial complexity can't be "tuned"
        - algo must be replaced
- MicroBenchmarking Framework
    - provides visibility into detailed performance of code
    
WHY
- Specifically, Java has a greater need to measure performance because it has a weaker PERFORMANCE MODEL 
than other languages (C/C++)
    - relative cost of various operations are less well defined. 
    - greater ABSTRACTION gap between what is written by a coder, and what is executed by the CPU
        - decreases the ability to predict performance consequences of optimizations
- PERF MODEL is in flux
    - changes from version to version, platform to platform etc. 
    