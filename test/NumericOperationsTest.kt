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
        assertThat("negative numbers are not supported", DECREMENT(ZERO).toInteger(), equalTo(0))
        assertThat("negative numbers edge case", INCREMENT(DECREMENT(ZERO)).toInteger(), equalTo(1))

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
}
