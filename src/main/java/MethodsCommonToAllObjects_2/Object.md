# java.lang.Object
- it is a concrete class designed for extension
- all of its NONFINAL methods have explicit GENERIC CONTRACTS because they were intended to be overridden
    - NONFINAL METHODS
        - equals()
        - hashCode()
        - toString()
        - clone()
        - finalize()

it is the responsibility of ANY overriding class to obey the general contracts to ensure that other classes which 
depend on those contracts function properly. 