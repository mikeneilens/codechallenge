import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate


class MainTest {
    @Test
    fun `hello world`() {
        val result = "hello world"
        assertEquals("hello world", result)
    }

    @Test
    fun `standby claim for 1 day on a week day which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-19"),1,"A")
        val expectedResult = Claim(WEEKDAY_RATE_A,listOf("2021-04-19, Week day rate A, £25"))
        assertEquals(expectedResult, result)
    }

}