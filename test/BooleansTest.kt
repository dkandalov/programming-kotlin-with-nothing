import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

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
