import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

typealias NUMBER = ((Any) -> Any) -> (Any) -> Any

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

fun NUMBER.toInteger(): Int = this({ x -> (x as Int) + 1 })(0) as Int



typealias BOOL = (Any) -> (Any) -> Any

val TRUE: BOOL = { x -> { y -> x } }
val FALSE: BOOL = { x -> { y -> y } }
val IF = { b: BOOL -> b }
val IS_ZERO = { n: NUMBER -> n({ x -> FALSE })(TRUE) as BOOL }

fun BOOL.toBoolean(): Boolean =
        // this(true)(false) as Boolean
        IF(this)(true)(false) as Boolean



typealias F = (Any) -> Any
fun Any.asF() = this as F

val INCREMENT: (NUMBER) -> NUMBER = { n -> { p -> { x -> p(n(p)(x)) }}}
val DECREMENT: (NUMBER) -> NUMBER =
        { n -> { f -> { x ->
            n({ g -> { h: F -> h(g.asF()(f)) }})({ y: Any -> x }).asF()({ y: Any -> y })
        }}}
val ADD: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(INCREMENT as F)(m) as NUMBER }}
val SUBTRACT: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(DECREMENT as F)(m) as NUMBER }}
val MULTIPLY: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(ADD(m) as F)(ZERO) as NUMBER }}
val POWER: (NUMBER) -> (NUMBER) -> NUMBER = { m -> { n -> n(MULTIPLY(m) as F)(ONE) as NUMBER }}
val IS_LESS_OR_EQUAL: (NUMBER) -> (NUMBER) -> BOOL = { m -> { n -> IS_ZERO(SUBTRACT(m)(n)) }}

val Z = { f: F -> { x: F -> f({ y: Any -> x(x as Any).asF()(y) }) }({ x: Any -> f({ y: Any -> x.asF()(x).asF()(y) }) }) }

val MOD: (NUMBER) -> (NUMBER) -> NUMBER = Z({ f -> { m: NUMBER -> { n: NUMBER ->
    IF(IS_LESS_OR_EQUAL(n)(m))(
        { x: Any ->
            f.asF()(SUBTRACT(m)(n)).asF()(n)
        }
    )(
        m
    )
}}}) as ((NUMBER) -> (NUMBER) -> NUMBER)