@file:Suppress("UNCHECKED_CAST")

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

typealias L = (Any) -> Any
typealias LL = (Any) -> ((Any) -> Any)
typealias LLL = (Any) -> ((Any) -> ((Any) -> Any))
fun _L(any: Any): L = any as L
fun Any.L() = _L(this)

typealias NUMBER = (L) -> (Any) -> Any
typealias BOOL = (Any) -> (Any) -> Any

val ZERO: NUMBER = { p -> { x -> x } }
val ONE: NUMBER = { p -> { x -> p(x) } }
val TWO: NUMBER = { p -> { x -> p(p(x)) } }
val THREE: NUMBER = { p -> { x -> p(p(p(x))) } }
val FIVE: NUMBER = { p -> { x -> p(p(p(p(p(x))))) } }
val FIFTEEN: NUMBER = { p -> { x -> p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(x))))))))))))))) } }
val HUNDRED: NUMBER = { p -> { x ->
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(x
    )))))))))))))))))))))))))))))))))))))))))
    )))))))))))))))))))))))))))))))))))))))))
    ))))))))))))))))))
}}

fun Any.toInt(): Int = this.L()({ x: Any -> (x as Int) + 1 }).L()(0) as Int


val TRUE: BOOL = { x -> { y -> x } }
val FALSE: BOOL = { x -> { y -> y } }
val IF: (BOOL) -> BOOL = { b -> b }
val IS_ZERO: (NUMBER) -> BOOL = { n -> n({ x -> FALSE })(TRUE) as BOOL }

fun Any.toBoolean(): Boolean =
        // this(true)(false) as Boolean
        IF(this as (Any) -> (Any) -> Any)(true)(false) as Boolean


val INCREMENT: (NUMBER) -> NUMBER = { n -> { p -> { x -> p(n(p)(x)) }}}
val DECREMENT: (NUMBER) -> NUMBER =
        { n -> { f -> { x ->
            n({ g -> { h: L -> h(g.L()(f)) }})({ y: Any -> x }).L()({ y: Any -> y })
        }}}
val ADD: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_L(INCREMENT))(m) as NUMBER }}
val SUBTRACT: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_L(DECREMENT))(m) as NUMBER }}
val MULTIPLY: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_L(ADD(m)))(ZERO) as NUMBER }}
val POWER: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_L(MULTIPLY(m)))(ONE) as NUMBER }}
val IS_LESS_OR_EQUAL: (NUMBER) -> (NUMBER) -> BOOL = { m -> { n -> IS_ZERO(SUBTRACT(m)(n)) }}

val Z = { f: L -> { x: L -> f({ y: Any -> x.L()(x).L()(y) }) }({ x: Any -> f({ y: Any -> x.L()(x).L()(y) }) }) }

val MOD: (NUMBER) -> (NUMBER) -> NUMBER =
    Z({ f -> { m: NUMBER -> { n: NUMBER ->
        IF(IS_LESS_OR_EQUAL(n)(m))(
            { x: Any ->
                f.L()(SUBTRACT(m)(n)).L()(n).L()(x)
            }
        )(
            m
        )
    }}}) as ((NUMBER) -> (NUMBER) -> NUMBER)

val PAIR: (Any) -> (Any) -> LL = { x: Any -> { y: Any -> { f -> f.L()(x).L()(y).L() }}}
val LEFT = { p: LL -> p(TRUE) }
val RIGHT = { p: LL -> p(FALSE) }

val EMPTY = PAIR(TRUE)(TRUE)
val UNSHIFT = { l: Any -> { x: Any ->
    PAIR(FALSE)(PAIR(x)(l))
}}
val IS_EMPTY = LEFT as ((LL) -> BOOL)
val FIRST = { l: Any -> LEFT(RIGHT(l as LL) as LL) }
val REST = { l: Any -> RIGHT(RIGHT(l as LL) as LL) }

fun Any.toList(): List<Any> {
    var list = this as LL
    val result = mutableListOf<Any>()
    while (!IS_EMPTY(list).toBoolean()) {
        result.add(FIRST(list))
        list = REST(list) as LL
    }
    return result
}

val RANGE = Z({ f: Any ->
    { m: NUMBER -> { n: NUMBER ->
        IF(IS_LESS_OR_EQUAL(m)(n))(
            { x: Any ->
                UNSHIFT(f.L()(INCREMENT(m)).L()(n))(m)(x)
            }
        )(
            EMPTY
        )
    }}
}) as LL

val FOLD = Z({ f ->
    { l: LL -> { x: Any -> { g: LLL ->
        IF(IS_EMPTY(l))(
            x
        )(
            { y: Any ->
                g(f.L()(REST(l)).L()(x).L()(g))(FIRST(l))(y)
            }
        )
    }}}
}) as LLL

val MAP = { k: Any -> { f: Any ->
    FOLD(k)(EMPTY)(
        { l: L -> { x: Any -> UNSHIFT(l)(f.L()(x)) }}
    )
}}


val TEN = MULTIPLY(TWO)(FIVE)
val B = TEN
val F = INCREMENT(B)
val I = INCREMENT(F)
val U = INCREMENT(I)
val ZED = INCREMENT(U)

val FIZZ = UNSHIFT(UNSHIFT(UNSHIFT(UNSHIFT(EMPTY)(ZED))(ZED))(I))(F)
val BUZZ = UNSHIFT(UNSHIFT(UNSHIFT(UNSHIFT(EMPTY)(ZED))(ZED))(U))(B)
val FIZZBUZZ = UNSHIFT(UNSHIFT(UNSHIFT(UNSHIFT(BUZZ)(ZED))(ZED))(I))(F)

fun Any.toChar(): Char = "0123456789BFiuz"[this.toInt()]
fun Any.toString_(): String = this.toList().map{ it.toChar() }.joinToString("")

val DIV = Z({ f: Any -> { m: NUMBER -> { n: NUMBER ->
    IF(IS_LESS_OR_EQUAL(n)(m))(
        { x: Any ->
            INCREMENT(f.L()(SUBTRACT(m)(n)).L()(n) as NUMBER).L()(x)
        }
    )(
        ZERO
    )
}}}) as LL

val PUSH = { l: Any -> { x: Any ->
    FOLD(l)(UNSHIFT(EMPTY)(x))(UNSHIFT)
}}

val TO_DIGITS = _L(Z({ f -> { n: NUMBER -> PUSH(
        IF(IS_LESS_OR_EQUAL(n)(DECREMENT(TEN)))(
            EMPTY
        )(
            { x:Any ->
                f.L()(DIV(n)(TEN)).L()(x)
            }
        )
    )(
        MOD(n)(TEN)
    )
}}))

val LIST_OF_FIZZ_BUZZ = MAP(RANGE(ONE)(HUNDRED))({ n: NUMBER ->
    IF(IS_ZERO(MOD(n)(FIFTEEN)))(
        FIZZBUZZ
    )(IF(IS_ZERO(MOD(n)(THREE)))(
        FIZZ
    )(IF(IS_ZERO(MOD(n)(THREE)))(
        BUZZ
    )(
        TO_DIGITS(n)
    )))
})