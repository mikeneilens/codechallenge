import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate


class MainTest {
    @Test
    fun `standby claim for 1 day at A Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-19"),1.0,"A")
        val expectedResult = Claim(MON_TO_THU_RATE_A,listOf("2021-04-19, Week day rate A, £25"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at A Level on Friday which is not a holiday is weekend rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-16"),1.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A,listOf("2021-04-16, Weekend rate A, £40"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at A Level on Saturday or Sunday is twice the weekend rate`() {
        val saturdayResult = calcStandbyClaim(LocalDate.parse("2021-04-17"),1.0,"A")
        val expectedSaturdayResult = Claim(WEEKEND_RATE_A * 2,listOf("2021-04-17, Weekend rate A, £40","2021-04-17, Weekend rate A, £40"))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(LocalDate.parse("2021-04-18"),1.0,"A")
        val expectedSundayResult = Claim(WEEKEND_RATE_A * 2,listOf("2021-04-18, Weekend rate A, £40","2021-04-18, Weekend rate A, £40"))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for 1 day at A Level on a bankholiday is twice the bankholiday rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-02"),1.0,"A")
        val expectedSaturdayResult = Claim(BANK_HOLIDAY_RATE_A * 2,listOf("2021-04-02, Bank holiday rate A, £80","2021-04-02, Bank holiday rate A, £80"))
        assertEquals(expectedSaturdayResult, result)
    }
@Test
    fun `standby claim for 1 day at B Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-19"),1.0,"B")
        val expectedResult = Claim(MON_TO_THU_RATE_B,listOf("2021-04-19, Week day rate B, £15"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at B Level on Friday which is not a holiday is weekend rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-16"),1.0,"B")
        val expectedResult = Claim(WEEKEND_RATE_B,listOf("2021-04-16, Weekend rate B, £25"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at B Level on Saturday or Sunday is twice the weekend rate`() {
        val saturdayResult = calcStandbyClaim(LocalDate.parse("2021-04-17"),1.0,"B")
        val expectedSaturdayResult = Claim(WEEKEND_RATE_B * 2,listOf("2021-04-17, Weekend rate B, £25","2021-04-17, Weekend rate B, £25"))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(LocalDate.parse("2021-04-18"),1.0,"B")
        val expectedSundayResult = Claim(WEEKEND_RATE_B * 2,listOf("2021-04-18, Weekend rate B, £25","2021-04-18, Weekend rate B, £25"))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for 1 day at B Level on a bankholiday is twice the bankholiday rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-02"),1.0,"B")
        val expectedSaturdayResult = Claim(BANK_HOLIDAY_RATE_B * 2,listOf("2021-04-02, Bank holiday rate B, £50","2021-04-02, Bank holiday rate B, £50"))
        assertEquals(expectedSaturdayResult, result)
    }

    @Test
    fun `standby claim for 2 day at A Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-19"),2.0,"A")
        val expectedResult = Claim(MON_TO_THU_RATE_A * 2,listOf("2021-04-19, Week day rate A, £25","2021-04-20, Week day rate A, £25"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 2 day at A Level starting on Friday which is not a holiday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-16"),2.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3,
            listOf("2021-04-16, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 4 day at A Level starting on Friday which is not a holiday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(LocalDate.parse("2021-04-16"),4.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 5 + MON_TO_THU_RATE_A,
            listOf("2021-04-16, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40",
                "2021-04-18, Weekend rate A, £40",
                "2021-04-18, Weekend rate A, £40",
                "2021-04-19, Week day rate A, £25",
            ))
        assertEquals(expectedResult, result)
    }
}