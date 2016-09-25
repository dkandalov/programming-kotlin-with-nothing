package types

import org.junit.Test


typealias L<T> = (T) -> T
typealias LL<T> = L<L<T>>
typealias LLL<T> = L<L<L<T>>>
typealias LLLL<T> = L<L<L<L<T>>>>
typealias N<T> = ((T) -> T) -> (T) -> T
typealias NN<T> = N<N<T>>

fun <T> zero(): N<T> = { p -> { x -> x }}
fun <T> one(): N<T> = { p -> { x -> p(x) }}
fun <T> two(): N<T> = { p -> { x -> p(p(x)) }}
fun <T> three(): N<T> = { p -> { x -> p(p(p(x))) }}
fun <T> five(): N<T> = { p -> { x -> p(p(p(p(p(x))))) }}

fun N<Int>.toKotlin() = this({ x: Int -> x + 1 })(0)

typealias B<T> = (T) -> (T) -> T
fun <T> true_(): B<T> = { x -> { y -> x } }
fun <T> false_(): B<T> = { x -> { y -> y } }
fun <T> if_(): L<B<T>> = { b -> b }
fun <T> isZero(): (N<B<T>>) -> B<T> = { n -> n({ x -> false_() })(true_()) }

fun B<Boolean>.toKotlin(): Boolean = this(true)(false)


fun <T> increment(): (N<T>) -> N<T> = { n -> { p -> { x -> p(n(p)(x)) }}}
fun <T> decrement(): (N<(L<T>)->T>) -> N<T> =
        { n -> { f: L<T> -> { x: T ->
            val function: L<(L<T>) -> T> = { g -> { h -> h(g(f)) } }
            n(function)({ y -> x })({ y: T -> y })
        }}}
fun <T> add(): (NN<T>) -> (N<T>) -> N<T> = { n -> { m -> n(increment<T>())(m) }}
//fun <T> subtract(): (N<T>) -> (N<T>) -> N<T> = { m -> { n -> n(decrement())(m) }}
fun <T> multiply(): (NN<T>) -> (NN<T>) -> N<T> = { n -> { m ->
    n(add<T>()(m))(zero())
}}

