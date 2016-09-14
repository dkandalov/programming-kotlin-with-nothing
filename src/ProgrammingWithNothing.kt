import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

val ZERO = { p: (Any) -> Any -> { x: Any -> x } }
val ONE = { p: (Any) -> Any -> { x: Any -> p(x) } }
val TWO = { p: (Any) -> Any -> { x: Any -> p(p(x)) } }
val THREE = { p: (Any) -> Any -> { x: Any -> p(p(p(x))) } }
val FIVE = { p: (Any) -> Any -> { x: Any -> p(p(p(p(p(x))))) } }
val FIFTEEN = { p: (Any) -> Any -> { x: Any -> p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(x))))))))))))))) } }
val HUNDRED = { p: (Any) -> Any -> { x: Any ->
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(
    p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(x
    )))))))))))))))))))))))))))))))))))))))))
    )))))))))))))))))))))))))))))))))))))))))
    ))))))))))))))))))
} }

fun (((Any) -> Any) -> (Any) -> Any).toInteger(): Int = this({ x -> (x as Int) + 1 })(0) as Int

class NumbersTest {
    @Test fun `conversion to kotlin ints`() {
        assertThat(ZERO.toInteger(), equalTo(0))
        assertThat(ONE.toInteger(), equalTo(1))
        assertThat(TWO.toInteger(), equalTo(2))
        assertThat(THREE.toInteger(), equalTo(3))
        assertThat(FIVE.toInteger(), equalTo(5))
        assertThat(FIFTEEN.toInteger(), equalTo(15))
        assertThat(HUNDRED.toInteger(), equalTo(100))
    }
}


val TRUE = { x: Any -> { y: Any -> x } }
val FALSE = { x: Any -> { y: Any -> y } }
val IF = { b: (Any) -> (Any) -> Any -> b }
val IS_ZERO = { n: ((Any) -> Any) -> (Any) -> Any -> n({ x -> FALSE })(TRUE) as ((Any) -> (Any) -> Any) }

fun ((Any) -> (Any) -> Any).toBoolean(): Boolean =
        // this(true)(false) as Boolean
        IF(this)(true)(false) as Boolean

class BooleansTest {
    @Test fun `conversion to kotlin booleans`() {
        assertThat(TRUE.toBoolean(), equalTo(true))
        assertThat(FALSE.toBoolean(), equalTo(false))
    }

    @Test fun `if function`() {
        assertThat(IF(TRUE)("foo")("bar").toString(), equalTo("foo"))
        assertThat(IF(FALSE)("foo")("bar").toString(), equalTo("bar"))
    }

    @Test fun `if zero predicate`() {
        assertThat(IS_ZERO(ZERO).toBoolean(), equalTo(true))
        assertThat(IS_ZERO(ONE).toBoolean(), equalTo(false))
        assertThat(IS_ZERO(THREE).toBoolean(), equalTo(false))
    }
}


val INCREMENT: (((Any) -> Any) -> (Any) -> Any) -> (((Any) -> Any) -> (Any) -> Any) = { n -> { p -> { x -> p(n(p)(x)) }}}
//val DECREMENT: (((Any) -> Any) -> (Any) -> Any) -> (((Any) -> Any) -> (Any) -> Any) =
//        { n -> { f -> { x -> n({ g -> { h -> { h(g(f))}}})({y -> {x}})({y-> {y}}) }}}

class NumericOperationsTest {
    @Test fun `increment`() {
        assertThat(INCREMENT(ZERO).toInteger(), equalTo(1))
        assertThat(INCREMENT(ONE).toInteger(), equalTo(2))
        assertThat(INCREMENT(TWO).toInteger(), equalTo(3))
    }
}