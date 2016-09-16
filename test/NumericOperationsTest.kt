import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class NumericOperationsTest {
    @Test fun `increment`() {
        assertThat(INCREMENT(ZERO).toInteger(), equalTo(1))
        assertThat(INCREMENT(ONE).toInteger(), equalTo(2))
        assertThat(INCREMENT(TWO).toInteger(), equalTo(3))
        assertThat(INCREMENT(THREE).toInteger(), equalTo(4))

        assertThat(INCREMENT(INCREMENT(ZERO)).toInteger(), equalTo(2))
    }

    @Test fun `decrement`() {
        assertThat(DECREMENT(ZERO).toInteger(), equalTo(0)) // no negative numbers
        assertThat(DECREMENT(ONE).toInteger(), equalTo(0))
        assertThat(DECREMENT(TWO).toInteger(), equalTo(1))
        assertThat(DECREMENT(THREE).toInteger(), equalTo(2))

        assertThat(DECREMENT(DECREMENT(THREE)).toInteger(), equalTo(1))
    }

    @Test fun `addition`() {
        assertThat(ADD(ZERO)(ZERO).toInteger(), equalTo(0))
        assertThat(ADD(ZERO)(ONE).toInteger(), equalTo(1))
        assertThat(ADD(ONE)(ONE).toInteger(), equalTo(2))
        assertThat(ADD(TWO)(THREE).toInteger(), equalTo(5))
    }

    @Test fun `subtraction`() {
        assertThat(SUBTRACT(ZERO)(ZERO).toInteger(), equalTo(0))
        assertThat(SUBTRACT(ONE)(ZERO).toInteger(), equalTo(1))
        assertThat(SUBTRACT(ONE)(ONE).toInteger(), equalTo(0))
        assertThat(SUBTRACT(FIVE)(TWO).toInteger(), equalTo(3))
    }

    @Test fun `multiplication`() {
        assertThat(MULTIPLY(ZERO)(ZERO).toInteger(), equalTo(0))
        assertThat(MULTIPLY(ZERO)(ONE).toInteger(), equalTo(0))
        assertThat(MULTIPLY(ONE)(ONE).toInteger(), equalTo(1))
        assertThat(MULTIPLY(THREE)(FIVE).toInteger(), equalTo(15))
    }

    @Test fun `power`() {
        assertThat(POWER(ZERO)(ONE).toInteger(), equalTo(0))
        assertThat(POWER(ZERO)(ZERO).toInteger(), equalTo(1))
        assertThat(POWER(TWO)(THREE).toInteger(), equalTo(8))
        assertThat(POWER(THREE)(FIVE).toInteger(), equalTo(243))
    }

    @Test fun `less or equal`() {
        assertThat(IS_LESS_OR_EQUAL(ONE)(TWO).toBoolean(), equalTo(true))
        assertThat(IS_LESS_OR_EQUAL(TWO)(TWO).toBoolean(), equalTo(true))
        assertThat(IS_LESS_OR_EQUAL(THREE)(TWO).toBoolean(), equalTo(false))
    }

    @Test fun `mod`() {
        assertThat(MOD(THREE)(TWO).toInteger(), equalTo(1))
        assertThat(MOD(POWER(THREE)(THREE))(ADD(THREE)(TWO)).toInteger(), equalTo(2))
    }

    @Test fun `pairs`() {
        val pair = PAIR(THREE)(FIVE)
        assertThat(LEFT(pair).toInteger(), equalTo(3))
        assertThat(RIGHT(pair).toInteger(), equalTo(5))
    }

    @Test fun `lists`() {
        val list =
            UNSHIFT(
                UNSHIFT(
                    UNSHIFT(EMPTY)(THREE)
                )(TWO)
            )(ONE)

        assertThat(FIRST(list).toInteger(), equalTo(1))
        assertThat(FIRST(REST(list)).toInteger(), equalTo(2))
        assertThat(FIRST(REST(REST(list))).toInteger(), equalTo(3))

        assertThat(IS_EMPTY(list).toBoolean(), equalTo(false))
        assertThat(IS_EMPTY(EMPTY).toBoolean(), equalTo(true))

        assertThat(list.toList().map{ it.toInteger() }, equalTo(listOf(1, 2, 3)))
    }

    @Test fun `range`() {
        assertThat(RANGE(ONE)(FIVE).toList().map { it.toInteger() }, equalTo(listOf(1, 2, 3, 4, 5)))
    }

    @Test fun `fold`() {
        assertThat(FOLD(RANGE(ONE)(FIVE))(ZERO)(ADD).toInteger(), equalTo(15))
        assertThat(FOLD(RANGE(ONE)(FIVE))(ONE)(MULTIPLY).toInteger(), equalTo(120))
    }

    @Test fun `map`() {
        assertThat(
            MAP(RANGE(ONE)(FIVE))(INCREMENT).toList().map { it.toInteger() },
            equalTo(listOf(2, 3, 4, 5, 6))
        )
    }

    @Test fun `chars and strings`() {
        assertThat(ZED.toChar(), equalTo('z'))
        assertThat(FIZZBUZZ.toString_(), equalTo("FizzBuzz"))
    }
}
