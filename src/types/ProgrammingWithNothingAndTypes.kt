@file:Suppress("UNCHECKED_CAST")

package types

import org.junit.Test


typealias L<T> = (T) -> T
typealias LL<T> = L<L<T>>
typealias LLL<T> = L<L<L<T>>>
typealias N<T> = N2<T, T>
typealias NN<T> = N<N<T>>
typealias N2<T,U> = ((T) -> U) -> (T) -> U

fun <T,U> zero(): N2<T,U> = { p -> { x -> x as U }}
fun <T,U> one(): N2<T,U> = { p -> { x -> p(x) }}
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
//fun <T> decrement(): (N<T>) -> N<T> =
//        { n: N<T> -> { f: L<T> -> { x ->
//            n({ g:LL<T> -> { h: LLL<T> -> h(g(f)) }})({ y:T -> x })({ y: T -> y })
//        }}}
fun <T> add(): (N<T>) -> (NN<T>) -> N<T> = { m -> { n -> n(increment())(m) }}
//fun <T> subtract(): (N<T>) -> (N<T>) -> N<T> = { m -> { n -> n(decrement<T>())(m) }}
fun <T> multiply(): (N<T>) -> (N2<NN<T>, N<T>>) -> N<T> = { m -> { n ->
    n(add<T>()(m))(zero())
}}

