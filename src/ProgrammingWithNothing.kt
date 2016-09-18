@file:Suppress("UNCHECKED_CAST")

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

typealias λ = (Any) -> Any
typealias λλ = (Any) -> ((Any) -> Any)
typealias λλλ = (Any) -> ((Any) -> ((Any) -> Any))
fun _λ(any: Any) = any as λ
fun <T> Any.λ(any: Any) = _λ(this)(any) as T
fun Any.λλ(any: Any) = _λ(this)(any) as λλ
fun Any.λλλ(any: Any) = _λ(this)(any) as λλλ

typealias NUMBER = (λ) -> (Any) -> Any

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

fun Any.toInt() = this.λλ({ x: Int -> x + 1 })(0) as Int


typealias BOOL = (Any) -> (Any) -> Any

val TRUE: BOOL = { x -> { y -> x } }
val FALSE: BOOL = { x -> { y -> y } }
val IF: (BOOL) -> BOOL = { b -> b }
val IS_ZERO: (NUMBER) -> BOOL = { n -> n({ x -> FALSE })(TRUE) as BOOL }

fun Any.toBoolean() =
        // this(true)(false) as Boolean
        IF(this as (Any) -> (Any) -> Any)(true)(false) as Boolean


val INCREMENT: (NUMBER) -> NUMBER = { n -> { p -> { x -> p(n(p)(x)) }}}
val DECREMENT: (NUMBER) -> NUMBER =
        { n -> { f -> { x ->
            n({ g -> { h: λ -> h(g.λ(f)) }})({ y: Any -> x }).λ({ y: Any -> y })
        }}}
val ADD: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_λ(INCREMENT))(m) as NUMBER }}
val SUBTRACT: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_λ(DECREMENT))(m) as NUMBER }}
val MULTIPLY: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_λ(ADD(m)))(ZERO) as NUMBER }}
val POWER: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(_λ(MULTIPLY(m)))(ONE) as NUMBER }}
val IS_LESS_OR_EQUAL: (NUMBER) -> (NUMBER) -> BOOL = { m -> { n -> IS_ZERO(SUBTRACT(m)(n)) }}

val Z = { f: λ -> { x: λ -> f({ y: Any -> x.λλ(x)(y) }) }({ x: Any -> f({ y: Any -> x.λλ(x)(y) }) }) }

val MOD: (NUMBER) -> (NUMBER) -> NUMBER =
    Z({ f -> { m: NUMBER -> { n: NUMBER ->
        IF(IS_LESS_OR_EQUAL(n)(m))(
            { x: Any ->
                f.λλλ(SUBTRACT(m)(n))(n)(x)
            }
        )(
            m
        )
    }}}) as ((NUMBER) -> (NUMBER) -> NUMBER)

val PAIR: (Any) -> (Any) -> λλ = { x: Any -> { y: Any -> { f -> f.λλλ(x)(y) }}}
val LEFT = { p: λλ -> p(TRUE) }
val RIGHT = { p: λλ -> p(FALSE) }

val EMPTY = PAIR(TRUE)(TRUE)
val UNSHIFT = { l: Any -> { x: Any ->
    PAIR(FALSE)(PAIR(x)(l))
}}
val IS_EMPTY = LEFT as ((λλ) -> BOOL)
val FIRST = { l: Any -> LEFT(RIGHT(l as λλ) as λλ) }
val REST = { l: Any -> RIGHT(RIGHT(l as λλ) as λλ) }

fun Any.toList(): List<Any> {
    var list = this as λλ
    val result = mutableListOf<Any>()
    while (!IS_EMPTY(list).toBoolean()) {
        result.add(FIRST(list))
        list = REST(list) as λλ
    }
    return result
}

val RANGE = Z({ f: Any ->
    { m: NUMBER -> { n: NUMBER ->
        IF(IS_LESS_OR_EQUAL(m)(n))(
            { x: Any ->
                UNSHIFT(f.λλ(INCREMENT(m))(n))(m)(x)
            }
        )(
            EMPTY
        )
    }}
}) as λλ

val FOLD = Z({ f ->
    { l: λλ -> { x: Any -> { g: λλλ ->
        IF(IS_EMPTY(l))(
            x
        )(
            { y: Any ->
                g(f.λλλ(REST(l))(x)(g))(FIRST(l))(y)
            }
        )
    }}}
}) as λλλ

val MAP = { k: Any -> { f: Any ->
    FOLD(k)(EMPTY)(
        { l: λ -> { x: Any -> UNSHIFT(l)(f.λ(x)) }}
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

fun Any.toChar() = "0123456789BFiuz"[this.toInt()]
fun Any.toStr() = this.toList().map{ it.toChar() }.joinToString("")

val DIV = Z({ f: Any -> { m: NUMBER -> { n: NUMBER ->
    IF(IS_LESS_OR_EQUAL(n)(m))(
        { x: Any ->
            INCREMENT(f.λλ(SUBTRACT(m)(n))(n) as NUMBER).λ<Any>(x)
        }
    )(
        ZERO
    )
}}}) as λλ

val PUSH = { l: Any -> { x: Any ->
    FOLD(l)(UNSHIFT(EMPTY)(x))(UNSHIFT)
}}

val TO_DIGITS = _λ(Z({ f -> { n: NUMBER -> PUSH(
        IF(IS_LESS_OR_EQUAL(n)(DECREMENT(TEN)))(
            EMPTY
        )(
            { x:Any ->
                f.λλ(DIV(n)(TEN))(x)
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