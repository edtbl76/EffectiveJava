# Item 70: Use checked exceptions for recoverable conditions and runtime exceptions for programming errors

## Java Throwable Types

### Checked Exceptions (Recoverable)
Checked Exceptions are used for conditions when the caller can be
reasonably expected to recover. 
- The caller is FORCED to handle the exception
    - catch clause
    - propagate the exception outward
- checked exceptions provide API designers a "mandate" to recover from 
failure conditions.

- since checked exceptions are intended to be recoverable they should 
include methods that provide info to help the caller recover from 
the "exceptional conditions"
    - Item 75

### Unchecked Exceptions (Unrecoverable) 
- Runtime Exceptions and Errors
    - identical in behavior
    - need not and SHOULD NOT be caught
- recovery is either impossible, or would do more harm than good.
- failure to catch unchecked exceptions results in a HALT on the
running thread w/ the appropriate error message

#### Use Runtime Exceptions to indicate coding errors
- most runtime exceptions indicate PRECONDITION VIOLATIONS
    - "precondition violations" are failures by the client of an API to
    obey the contract/invariants established by the API spec.

- all unchecked throwables you impl should subclass 'RuntimeException'
    - indirectly OR directly
    

### Checked vs. Unchecked
The above examples are good guidelines, but there isn't always a clear
line between:
- recoverable conditions 
- programming errors.

If it isn't clear
- err on side of caution (use unchecked exception) - Item 71

### Errors
- Technically lumped in w/ Unchecked Exceptions
- unofficial convention is that errors are reserved for use by the JVM
for 
    - indication of resource failures
    - invariant failures
    - any other conditions that make it impossible to continue execution
- Do NOT impl any new Error subclasses
- Do NOT throw any Error subclasses
    - W/ the Exception of AssertionError for test assertions.

## Don't Use String Representations of Exceptions 
- Do NOT pass string representation of exceptions to dig out 
additional information
    - anti-pattern (Item 12)
    - Throwables don't specify details of their string representations (
    which can differ from impl to impl/release to release)
        - nonportable/brittle code
