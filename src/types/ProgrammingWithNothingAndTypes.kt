@file:Suppress("UNCHECKED_CAST")

package types

import org.junit.Test


typealias L<T> = (T) -> T
typealias LL<T> = L<L<T>>
typealias LLL<T> = L<L<L<T>>>
typealias N<T> = ((T) -> T) -> (T) -> T

fun <T> zero(): N<T> = { p -> { x -> x }}
fun <T> one(): N<T> = { p -> { x -> p(x) }}
fun <T> two(): N<T> = { p -> { x -> p(p(x)) }}
fun <T> three(): N<T> = { p -> { x -> p(p(p(x))) }}

fun N<Int>.toKotlin() = this({ x: Int -> x + 1 })(0)

typealias BB<T> = (T) -> (T) -> T
fun <T> true_(): BB<T> = { x -> { y -> x } }
fun <T> false_(): BB<T> = { x -> { y -> y } }
fun <T> if_(): L<BB<T>> = { b -> b }
fun <T> isZero(): (N<BB<T>>) -> BB<T> = { n -> n({ x -> false_() })(true_()) }

fun BB<Boolean>.toKotlin(): Boolean = this(true)(false)


fun <T> increment(): (N<T>) -> N<T> = { n -> { p -> { x -> p(n(p)(x)) }}}
//fun <T> decrement(): (N<T>) -> N<T> =
//        { n: N<T> -> { f: L<T> -> { x ->
//            n({ g:LL<T> -> { h: LLL<T> -> h(g(f)) }})({ y:T -> x })({ y: T -> y })
//        }}}
fun <T> add(): (N<T>) -> (N<N<T>>) -> N<T> = { m -> { n -> n(increment())(m) }}
//fun <T> subtract(): (N<T>) -> (N<T>) -> N<T> = { m -> { n -> n(decrement<T>())(m) }}
//fun <T> multiply(): (N<T>) -> (N<N<T>>) -> N<T> = { m: N<T> -> { n: N<N<T>> ->
//    n(add<T>(m))(types.zero<T>())
//}}

