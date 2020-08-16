# Item 66: Use native methods judiciously

## JNI (Java Native Interface)
- used to allow java programs to call "native methods"
    - (methods written in "native programming languages" such as C/C++)
    
### Three Main Uses of Native Programming Languages
1. provide access to platform-specific facilities
    - e.g. registries
1. provide access to existing libs of native code
    - (including legacy libs that provide access to legacy data)
1. used to write "performance-critical" parts of apps in native languages
for improved performance

### When to use them
- less and less as java matures and provides access to host/OS level 
features
- when no equivalent libs are available in Java, go native. 

### When NOT to use them
- performance optimization
    - since java 3, JVM performance has improved to the point where it
    is now possible to obtain COMPARABLE performance in MOST tasks. 
    
    
## Native Methods Disadvantages
- unsafe (Item 50)
    - apps that use native methods are vulnerable to memory corruption errors
- native methods are platform dependent
    - less portable (which is one of the main purposes of Java)
- hard to debug
- native methods can DECREASE performance
    - GC can't automate/track native memory usage (Item 8)
    - performance hit going in/out of native code
- "glue code"/integrations
    - these are very challenging to write/read/maintain
