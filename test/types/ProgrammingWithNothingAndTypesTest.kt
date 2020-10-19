package types

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import toInt

class ProgrammingWithNothingAndTypesTest {
    @Test fun `conversion to kotlin ints`() {
        assertThat(zero<Int>().toKotlin(), equalTo(0))
        assertThat(one<Int>().toKotlin(), equalTo(1))
        assertThat(two<Int>().toKotlin(), equalTo(2))
    }

    @Test fun `conversion to kotlin booleans`() {
        assertThat(true_<Boolean>().toKotlin(), equalTo(true))
        assertThat(false_<Boolean>().toKotlin(), equalTo(false))
    }

    @Test fun `if function`() {
        assertThat(if_<String>()(true_())("foo")("bar"), equalTo("foo"))
        assertThat(if_<String>()(false_())("foo")("bar"), equalTo("bar"))
    }

    @Test fun `is zero predicate`() {
        assertThat(isZero<Boolean>()(zero()).toKotlin(), equalTo(true))
        assertThat(isZero<Boolean>()(one()).toKotlin(), equalTo(false))
        assertThat(isZero<Boolean>()(three()).toKotlin(), equalTo(false))
    }

    @Test fun `increment`() {
        assertThat(increment<Int>()(zero()).toKotlin(), equalTo(1))
        assertThat(increment<Int>()(one()).toKotlin(), equalTo(2))
        assertThat(increment<Int>()(two()).toKotlin(), equalTo(3))
        assertThat(increment<Int>()(three()).toKotlin(), equalTo(4))

        assertThat(increment<Int>()(increment<Int>()(zero())).toKotlin(), equalTo(2))
    }

    @Test fun `decrement`() {
        assertThat(decrement<Int>()(zero()).toKotlin(), equalTo(0)) // no negative numbers
        assertThat(decrement<Int>()(one()).toKotlin(), equalTo(0))
        assertThat(decrement<Int>()(two()).toKotlin(), equalTo(1))
        assertThat(decrement<Int>()(three()).toKotlin(), equalTo(2))

        assertThat(decrement<Int>()(decrement<(L<Int>)->Int>()(three())).toKotlin(), equalTo(1))
    }

    @Test fun `addition`() {
        assertThat(add<Int>()(zero())(zero()).toKotlin(), equalTo(0))
        assertThat(add<Int>()(zero())(one()).toKotlin(), equalTo(1))
        assertThat(add<Int>()(one())(one()).toKotlin(), equalTo(2))
        assertThat(add<Int>()(two())(three()).toKotlin(), equalTo(5))
    }

    @Test fun `multiplication`() {
        assertThat(multiply<Int>()(zero())(zero()).toInt(), equalTo(0))
        assertThat(multiply<Int>()(zero())(one()).toInt(), equalTo(0))
        assertThat(multiply<Int>()(one())(one()).toInt(), equalTo(1))
        assertThat(multiply<Int>()(three())(five()).toInt(), equalTo(15))
    }

}