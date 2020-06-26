| Interface | Function Signature | Example | 
| --- | --- | --- |
| UnaryOperator<T> | T apply(T t) | String::toLowerCase | 
| BinaryOperator<T> | T apply(T t1, T t2) | BigInteger::add |
| Predicate<T> | boolean test(T t) | Collection::isEmpty |
| Function<T> | R apply(T t) | Arrays::asList | 
| Suppler<T> | T get() | Instant::now |
| Consumer<T> | void accept(T t) | System.out::println |

(6)

## Numeric Variants
There are three variants of each of the 6 Basic FI to operate on
the primitive types int, long and double

- IntUnaryOperator
- LongUnaryOperator
- DoubleUnaryOperator
- IntBinaryOperator
- LongBinaryOperator
- DoubleBinaryOperator
- IntPredicate
- LongPredicate
- DoublePredicate
- IntFunction
- LongFunction
- DoubleFunction
- IntSupplier
- LongSupplier
- DoubleSupplier
- IntConsumer
- LongConsumer
- DoubleConsumer

(18) {24}

## Function Variants
There are nine additional variantsd of Function for use when
result type is primitive

Primitive -> Primitive
- IntToLongFunction
- IntToDoubleFunction
- LongToIntFunction
- LongToDoubleFunction
- DoubleToIntFunction
- DoubleToLongFunction

Primitive -> Object
- IntToObjFunction
- LongToObjFunction
- DoubleToObjFunction

(9) {33}

## Two - Argument Versions

- BiPredicate<T, U>
- BiFunction<T, U, R>
- BiConsumer<T, U>

- ToIntBiFunction<T,U>
- ToLongBiFunction<T,U>
- ToDoubleBiFunction<T,U>

- ObjIntConsumer<T>
- ObjLongConsumer<T>
- ObjDoubleConsumer<T>

(9) {42}

## Boolean Supplier
variant of Supplier that returns booleans

- BooleanSupplier
