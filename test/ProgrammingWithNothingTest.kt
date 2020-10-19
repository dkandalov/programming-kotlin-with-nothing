import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ProgrammingWithNothingTest {
    @Test fun `conversion to kotlin ints`() {
        assertThat(ZERO.toInt(), equalTo(0))
        assertThat(ONE.toInt(), equalTo(1))
        assertThat(TWO.toInt(), equalTo(2))
        assertThat(THREE.toInt(), equalTo(3))
        assertThat(FIVE.toInt(), equalTo(5))
        assertThat(FIFTEEN.toInt(), equalTo(15))
        assertThat(HUNDRED.toInt(), equalTo(100))
    }

    @Test fun `conversion to kotlin booleans`() {
        assertThat(TRUE.toBoolean(), equalTo(true))
        assertThat(FALSE.toBoolean(), equalTo(false))
    }

    @Test fun `if function`() {
        assertThat(IF(TRUE)("foo")("bar").toString(), equalTo("foo"))
        assertThat(IF(FALSE)("foo")("bar").toString(), equalTo("bar"))
    }

    @Test fun `is zero predicate`() {
        assertThat(IS_ZERO(ZERO).toBoolean(), equalTo(true))
        assertThat(IS_ZERO(ONE).toBoolean(), equalTo(false))
        assertThat(IS_ZERO(THREE).toBoolean(), equalTo(false))
    }

    @Test fun `increment`() {
        assertThat(INCREMENT(ZERO).toInt(), equalTo(1))
        assertThat(INCREMENT(ONE).toInt(), equalTo(2))
        assertThat(INCREMENT(TWO).toInt(), equalTo(3))
        assertThat(INCREMENT(THREE).toInt(), equalTo(4))

        assertThat(INCREMENT(INCREMENT(ZERO)).toInt(), equalTo(2))
    }

    @Test fun `decrement`() {
        assertThat(DECREMENT(ZERO).toInt(), equalTo(0)) // no negative numbers
        assertThat(DECREMENT(ONE).toInt(), equalTo(0))
        assertThat(DECREMENT(TWO).toInt(), equalTo(1))
        assertThat(DECREMENT(THREE).toInt(), equalTo(2))

        assertThat(DECREMENT(DECREMENT(THREE)).toInt(), equalTo(1))
    }

    @Test fun `addition`() {
        assertThat(ADD(ZERO)(ZERO).toInt(), equalTo(0))
        assertThat(ADD(ZERO)(ONE).toInt(), equalTo(1))
        assertThat(ADD(ONE)(ONE).toInt(), equalTo(2))
        assertThat(ADD(TWO)(THREE).toInt(), equalTo(5))
    }

    @Test fun `subtraction`() {
        assertThat(SUBTRACT(ZERO)(ZERO).toInt(), equalTo(0))
        assertThat(SUBTRACT(ONE)(ZERO).toInt(), equalTo(1))
        assertThat(SUBTRACT(ONE)(ONE).toInt(), equalTo(0))
        assertThat(SUBTRACT(FIVE)(TWO).toInt(), equalTo(3))
    }

    @Test fun `multiplication`() {
        assertThat(MULTIPLY(ZERO)(ZERO).toInt(), equalTo(0))
        assertThat(MULTIPLY(ZERO)(ONE).toInt(), equalTo(0))
        assertThat(MULTIPLY(ONE)(ONE).toInt(), equalTo(1))
        assertThat(MULTIPLY(THREE)(FIVE).toInt(), equalTo(15))
    }

    @Test fun `power`() {
        assertThat(POWER(ZERO)(ONE).toInt(), equalTo(0))
        assertThat(POWER(ZERO)(ZERO).toInt(), equalTo(1))
        assertThat(POWER(TWO)(THREE).toInt(), equalTo(8))
        assertThat(POWER(THREE)(FIVE).toInt(), equalTo(243))
    }

    @Test fun `less or equal`() {
        assertThat(IS_LESS_OR_EQUAL(ONE)(TWO).toBoolean(), equalTo(true))
        assertThat(IS_LESS_OR_EQUAL(TWO)(TWO).toBoolean(), equalTo(true))
        assertThat(IS_LESS_OR_EQUAL(THREE)(TWO).toBoolean(), equalTo(false))
    }

    @Test fun `mod`() {
        assertThat(MOD(THREE)(TWO).toInt(), equalTo(1))
        assertThat(MOD(POWER(THREE)(THREE))(ADD(THREE)(TWO)).toInt(), equalTo(2))
    }

    @Test fun `pairs`() {
        val pair = PAIR(THREE)(FIVE)
        assertThat(LEFT(pair).toInt(), equalTo(3))
        assertThat(RIGHT(pair).toInt(), equalTo(5))
    }

    @Test fun `lists`() {
        val list =
            UNSHIFT(
                UNSHIFT(
                    UNSHIFT(EMPTY)(THREE)
                )(TWO)
            )(ONE)

        assertThat(FIRST(list).toInt(), equalTo(1))
        assertThat(FIRST(REST(list)).toInt(), equalTo(2))
        assertThat(FIRST(REST(REST(list))).toInt(), equalTo(3))

        assertThat(IS_EMPTY(list).toBoolean(), equalTo(false))
        assertThat(IS_EMPTY(EMPTY).toBoolean(), equalTo(true))

        assertThat(list.toList().map{ it.toInt() }, equalTo(listOf(1, 2, 3)))

        assertThat(PUSH(list)(FIVE).toList().map{ it.toInt() }, equalTo(listOf(1, 2, 3, 5)))
    }

    @Test fun `range`() {
        assertThat(RANGE(ONE)(FIVE).toList().map { it.toInt() }, equalTo(listOf(1, 2, 3, 4, 5)))
    }

    @Test fun `fold`() {
        assertThat(FOLD(RANGE(ONE)(FIVE))(ZERO)(ADD).toInt(), equalTo(15))
        assertThat(FOLD(RANGE(ONE)(FIVE))(ONE)(MULTIPLY).toInt(), equalTo(120))
    }

    @Test fun `map`() {
        assertThat(
            MAP(RANGE(ONE)(FIVE))(INCREMENT).toList().map { it.toInt() },
            equalTo(listOf(2, 3, 4, 5, 6))
        )
    }

    @Test fun `chars and strings`() {
        assertThat(ZED.toChar(), equalTo('z'))
        assertThat(FIZZBUZZ.toStr(), equalTo("FizzBuzz"))
    }

    @Test fun `division`() {
        assertThat(DIV(ONE)(ONE).toInt(), equalTo(1))
        assertThat(DIV(TEN)(TWO).toInt(), equalTo(5))
        assertThat(DIV(TEN)(THREE).toInt(), equalTo(3))
    }

    @Test fun `to digits`() {
        assertThat(TO_DIGITS(FIVE).toStr(), equalTo("5"))
        assertThat(TO_DIGITS(POWER(FIVE)(THREE)).toStr(), equalTo("125"))
    }

    @Test fun `fizz buzz`() {
        assertThat(
            LIST_OF_FIZZ_BUZZ.toList().joinToString(", ") { it.toStr() },
            equalTo("1, 2, Fizz, 4, 5, Fizz, 7, 8, Fizz, 10, 11, Fizz, 13, 14, FizzBuzz, " +
                    "16, 17, Fizz, 19, 20, Fizz, 22, 23, Fizz, 25, 26, Fizz, 28, 29, FizzBuzz, " +
                    "31, 32, Fizz, 34, 35, Fizz, 37, 38, Fizz, 40, 41, Fizz, 43, 44, FizzBuzz, " +
                    "46, 47, Fizz, 49, 50, Fizz, 52, 53, Fizz, 55, 56, Fizz, 58, 59, FizzBuzz, " +
                    "61, 62, Fizz, 64, 65, Fizz, 67, 68, Fizz, 70, 71, Fizz, 73, 74, FizzBuzz, " +
                    "76, 77, Fizz, 79, 80, Fizz, 82, 83, Fizz, 85, 86, Fizz, 88, 89, FizzBuzz, " +
                    "91, 92, Fizz, 94, 95, Fizz, 97, 98, Fizz, 100")
        )
    }
}
