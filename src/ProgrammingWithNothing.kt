@file:Suppress("UNCHECKED_CAST")

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

typealias _F = (Any) -> Any
typealias FF = (Any) -> ((Any) -> Any)
typealias FFF = (Any) -> ((Any) -> ((Any) -> Any))

typealias NUMBER = (_F) -> (Any) -> Any
typealias BOOL = (Any) -> (Any) -> Any

fun Any.toInt(): Int = this.asF()({ x: Any -> (x as Int) + 1 }).asF()(0) as Int
fun Any.toBoolean(): Boolean =
        // this(true)(false) as Boolean
        IF(this as (Any) -> (Any) -> Any)(true)(false) as Boolean
fun L(any: Any): _F = any as _F
fun Any.asF() = L(this)


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


val TRUE: BOOL = { x -> { y -> x } }
val FALSE: BOOL = { x -> { y -> y } }
val IF: (BOOL) -> BOOL = { b -> b }
val IS_ZERO: (NUMBER) -> BOOL = { n -> n({ x -> FALSE })(TRUE) as BOOL }


val INCREMENT: (NUMBER) -> NUMBER = { n -> { p -> { x -> p(n(p)(x)) }}}
val DECREMENT: (NUMBER) -> NUMBER =
        { n -> { f -> { x ->
            n({ g -> { h: _F -> h(g.asF()(f)) }})({ y: Any -> x }).asF()({ y: Any -> y })
        }}}
val ADD: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(L(INCREMENT))(m) as NUMBER }}
val SUBTRACT: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(L(DECREMENT))(m) as NUMBER }}
val MULTIPLY: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(L(ADD(m)))(ZERO) as NUMBER }}
val POWER: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(L(MULTIPLY(m)))(ONE) as NUMBER }}
val IS_LESS_OR_EQUAL: (NUMBER) -> (NUMBER) -> BOOL = { m -> { n -> IS_ZERO(SUBTRACT(m)(n)) }}

val Z = { f: _F -> { x: _F -> f({ y: Any -> x.asF()(x).asF()(y) }) }({ x: Any -> f({ y: Any -> x.asF()(x).asF()(y) }) }) }

val MOD: (NUMBER) -> (NUMBER) -> NUMBER =
    Z({ f -> { m: NUMBER -> { n: NUMBER ->
        IF(IS_LESS_OR_EQUAL(n)(m))(
            { x: Any ->
                f.asF()(SUBTRACT(m)(n)).asF()(n).asF()(x)
            }
        )(
            m
        )
    }}}) as ((NUMBER) -> (NUMBER) -> NUMBER)

val PAIR: (Any) -> (Any) -> FF = { x: Any -> { y: Any -> { f -> f.asF()(x).asF()(y).asF() }}}
val LEFT = { p: FF -> p(TRUE) }
val RIGHT = { p: FF -> p(FALSE) }

val EMPTY = PAIR(TRUE)(TRUE)
val UNSHIFT = { l: Any -> { x: Any ->
    PAIR(FALSE)(PAIR(x)(l))
}}
val IS_EMPTY = LEFT as ((FF) -> BOOL)
val FIRST = { l: Any -> LEFT(RIGHT(l as FF) as FF) }
val REST = { l: Any -> RIGHT(RIGHT(l as FF) as FF) }

fun Any.toList(): List<Any> {
    var list = this as FF
    val result = mutableListOf<Any>()
    while (!IS_EMPTY(list).toBoolean()) {
        result.add(FIRST(list))
        list = REST(list) as FF
    }
    return result
}

val RANGE = Z({ f: Any ->
    { m: NUMBER -> { n: NUMBER ->
        IF(IS_LESS_OR_EQUAL(m)(n))(
            { x: Any ->
                UNSHIFT(f.asF()(INCREMENT(m)).asF()(n))(m)(x)
            }
        )(
            EMPTY
        )
    }}
}) as FF

val FOLD = Z({ f ->
    { l: FF -> { x: Any -> { g: FFF ->
        IF(IS_EMPTY(l))(
            x
        )(
            { y: Any ->
                g(f.asF()(REST(l)).asF()(x).asF()(g))(FIRST(l))(y)
            }
        )
    }}}
}) as FFF

val MAP = { k: Any -> { f: Any ->
    FOLD(k)(EMPTY)(
        { l: _F -> { x: Any -> UNSHIFT(l)(f.asF()(x)) }}
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
            INCREMENT(f.asF()(SUBTRACT(m)(n)).asF()(n) as NUMBER).asF()(x)
        }
    )(
        ZERO
    )
}}}) as FF

val PUSH = { l: Any -> { x: Any ->
    FOLD(l)(UNSHIFT(EMPTY)(x))(UNSHIFT)
}}

val TO_DIGITS = L(Z({ f -> { n: NUMBER -> PUSH(
        IF(IS_LESS_OR_EQUAL(n)(DECREMENT(TEN)))(
            EMPTY
        )(
            { x:Any ->
                f.asF()(DIV(n)(TEN)).asF()(x)
            }
        )
    )(
        MOD(n)(TEN)
    )
}}))

val FIZZ__BUZZ = MAP(RANGE(ONE)(HUNDRED))({ n: NUMBER ->
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