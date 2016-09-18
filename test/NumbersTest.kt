import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class NumbersTest {
    @Test fun `conversion to kotlin ints`() {
        assertThat(ZERO.toInt(), equalTo(0))
        assertThat(ONE.toInt(), equalTo(1))
        assertThat(TWO.toInt(), equalTo(2))
        assertThat(THREE.toInt(), equalTo(3))
        assertThat(FIVE.toInt(), equalTo(5))
        assertThat(FIFTEEN.toInt(), equalTo(15))
        assertThat(HUNDRED.toInt(), equalTo(100))
    }
}
