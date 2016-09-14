import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

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
