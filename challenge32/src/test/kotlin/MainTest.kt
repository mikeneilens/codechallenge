import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `standby claim for 1 day at A Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),1.0,"A")
        val expectedResult = Claim(MON_TO_THU_RATE_A,listOf("2021-04-19, Week day rate A, £25"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at A Level on Friday which is not a holiday is weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),1.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A,listOf("2021-04-16, Weekend rate A, £40"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at A Level on Saturday or Sunday is twice the weekend rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2021-04-17"),1.0,"A")
        val expectedSaturdayResult = Claim(WEEKEND_RATE_A * 2,
            listOf(
                "2021-04-17, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40"))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2021-04-18"),1.0,"A")
        val expectedSundayResult = Claim(WEEKEND_RATE_A * 2,
            listOf(
                "2021-04-18, Weekend rate A, £40",
                "2021-04-18, Weekend rate A, £40"))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for 1 day at A Level on a bank holiday is twice the bank holiday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-02"),1.0,"A")
        val expectedSaturdayResult = Claim(BANK_HOLIDAY_RATE_A * 2,
            listOf(
                "2021-04-02, Bank holiday rate A, £80",
                "2021-04-02, Bank holiday rate A, £80"))
        assertEquals(expectedSaturdayResult, result)
    }
@Test
    fun `standby claim for 1 day at B Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),1.0,"B")
        val expectedResult = Claim(MON_TO_THU_RATE_B,listOf("2021-04-19, Week day rate B, £15"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at B Level on Friday which is not a holiday is weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),1.0,"B")
        val expectedResult = Claim(WEEKEND_RATE_B,listOf("2021-04-16, Weekend rate B, £25"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at B Level on Saturday or Sunday is twice the weekend rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2021-04-17"),1.0,"B")
        val expectedSaturdayResult = Claim(WEEKEND_RATE_B * 2,
            listOf(
                "2021-04-17, Weekend rate B, £25",
                "2021-04-17, Weekend rate B, £25"))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2021-04-18"),1.0,"B")
        val expectedSundayResult = Claim(WEEKEND_RATE_B * 2,listOf(
            "2021-04-18, Weekend rate B, £25",
            "2021-04-18, Weekend rate B, £25"))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for 1 day at B Level on a bank holiday is twice the bank holiday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-02"),1.0,"B")
        val expectedSaturdayResult = Claim(BANK_HOLIDAY_RATE_B * 2,listOf(
            "2021-04-02, Bank holiday rate B, £50",
            "2021-04-02, Bank holiday rate B, £50"))
        assertEquals(expectedSaturdayResult, result)
    }

    @Test
    fun `standby claim for 2 day at A Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),2.0,"A")
        val expectedResult = Claim(MON_TO_THU_RATE_A * 2,
            listOf(
                "2021-04-19, Week day rate A, £25",
                "2021-04-20, Week day rate A, £25"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 2 day at A Level starting on Friday which is not a holiday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),2.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3,
            listOf("2021-04-16, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 4 day at A Level starting on Friday which is not a holiday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),4.0,"A")
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

    @Test
    fun `standby claim for 4 day at A Level starting on Friday which is a holiday is bank holiday rate X 4 and weekend rate X 4`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-02"),4.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 4 + BANK_HOLIDAY_RATE_A * 4,
            listOf("2021-04-02, Bank holiday rate A, £80",
                "2021-04-02, Bank holiday rate A, £80",
                "2021-04-03, Weekend rate A, £40",
                "2021-04-03, Weekend rate A, £40",
                "2021-04-04, Weekend rate A, £40",
                "2021-04-04, Weekend rate A, £40",
                "2021-04-05, Bank holiday rate A, £80",
                "2021-04-05, Bank holiday rate A, £80",
            ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 5 days at A Level starting on Thursday which is before good Friday is week day rate  plus bank holiday rate X 4 plus weekend rate X 4`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-01"),5.0,"A")
        val expectedResult = Claim(MON_TO_THU_RATE_A + WEEKEND_RATE_A * 4 + BANK_HOLIDAY_RATE_A * 4,
            listOf(
                "2021-04-01, Week day rate A, £25",
                "2021-04-02, Bank holiday rate A, £80",
                "2021-04-02, Bank holiday rate A, £80",
                "2021-04-03, Weekend rate A, £40",
                "2021-04-03, Weekend rate A, £40",
                "2021-04-04, Weekend rate A, £40",
                "2021-04-04, Weekend rate A, £40",
                "2021-04-05, Bank holiday rate A, £80",
                "2021-04-05, Bank holiday rate A, £80",
            ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for 1 and a half days at A Level starting on Friday which is not a holiday is weekend rate spread over 2 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),1.5,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 2, listOf(
            "2021-04-16, Weekend rate A, £40",
            "2021-04-17, Weekend rate A, £40"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 and a half days at A Level starting on Saturday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-17"),1.5,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3, listOf(
            "2021-04-17, Weekend rate A, £40",
            "2021-04-17, Weekend rate A, £40",
            "2021-04-18, Weekend rate A, £40"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day for an invalid call out Level of C on Mon to Thursday which is not a holiday is zero`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),1.0,"C")
        val expectedResult = Claim(0,listOf("2021-04-19, Week day rate C, £0"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for an invalid duration of 1 and a third day at A Level on Mon to Thursday which is not a holiday is rounded up to 2 X weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),1.3,"A")
        val expectedResult = Claim(MON_TO_THU_RATE_A * 2,listOf(
            "2021-04-19, Week day rate A, £25",
            "2021-04-20, Week day rate A, £25" ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for an invalid duration of 1 and a third day at A Level on Weekend is rounded up to 3 X weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-17"),1.3,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3, listOf(
                "2021-04-17, Weekend rate A, £40",
                "2021-04-17, Weekend rate A, £40",
                "2021-04-18, Weekend rate A, £40" ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for a complete week at A level is 300 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),7.0,"A")
        val expectedResult = Claim(300, listOf(
            "2021-04-19, Week day rate A, £25",
            "2021-04-20, Week day rate A, £25",
            "2021-04-21, Week day rate A, £25",
            "2021-04-22, Week day rate A, £25",
            "2021-04-23, Weekend rate A, £40",
            "2021-04-24, Weekend rate A, £40",
            "2021-04-24, Weekend rate A, £40",
            "2021-04-25, Weekend rate A, £40",
            "2021-04-25, Weekend rate A, £40" ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for a complete week that includes one bank holiday at A level is 435 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-03"),7.0,"A")
        val expectedResult = Claim(435, listOf(
            "2021-04-03, Weekend rate A, £40",
            "2021-04-03, Weekend rate A, £40",
            "2021-04-04, Weekend rate A, £40",
            "2021-04-04, Weekend rate A, £40",
            "2021-04-05, Bank holiday rate A, £80",
            "2021-04-05, Bank holiday rate A, £80",
            "2021-04-06, Week day rate A, £25",
            "2021-04-07, Week day rate A, £25",
            "2021-04-08, Week day rate A, £25",
            "2021-04-09, Weekend rate A, £40",
             ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for a complete week that includes one bank holiday at B level is 435 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-03"),7.0,"B")
        val expectedResult = Claim(270, listOf(
            "2021-04-03, Weekend rate B, £25",
            "2021-04-03, Weekend rate B, £25",
            "2021-04-04, Weekend rate B, £25",
            "2021-04-04, Weekend rate B, £25",
            "2021-04-05, Bank holiday rate B, £50",
            "2021-04-05, Bank holiday rate B, £50",
            "2021-04-06, Week day rate B, £15",
            "2021-04-07, Week day rate B, £15",
            "2021-04-08, Week day rate B, £15",
            "2021-04-09, Weekend rate B, £25",
        ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for a complete calendar year is 16690 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-01-01"), 365.0, "A")
        assertEquals(16690, result.amount)
    }

    //---- Tests for calculating bank holidays dynamically //
    @Test
    fun `when christmas day is a Wednesday then there is a bank holiday on 25th december and 26th december`() {
        assertTrue(isChristmasDayHoliday(ClaimDate("2019-12-25")))
        assertTrue(isBoxingDayHoliday(ClaimDate("2019-12-26")))

    }
    @Test
    fun `when christmas day is a Friday then there is a bank holiday on 25th december and 28th december but not 26th December`() {
        assertTrue(isChristmasDayHoliday(ClaimDate("2020-12-25")))
        assertTrue(isBoxingDayHoliday(ClaimDate("2020-12-28")))
        assertFalse(isBoxingDayHoliday(ClaimDate("2020-12-26")))
    }
    @Test
    fun `when christmas day is a Saturday then there is a bank holiday on 27th december and 28th december but not on 25th or 26th December`() {
        assertTrue(isChristmasDayHoliday(ClaimDate("2021-12-27")))
        assertTrue(isBoxingDayHoliday(ClaimDate("2021-12-28")))
        assertFalse(isChristmasDayHoliday(ClaimDate("2021-12-25")))
        assertFalse(isBoxingDayHoliday(ClaimDate("2021-12-26")))
    }
    @Test
    fun `when christmas day is a Sunday then there is a bank holiday on 26th december and 27th december but not on 25th December`() {
        assertTrue(isChristmasDayHoliday(ClaimDate("2022-12-26")))
        assertTrue(isBoxingDayHoliday(ClaimDate("2022-12-27")))
        assertFalse(isChristmasDayHoliday(ClaimDate("2022-12-25")))
        assertFalse(isBoxingDayHoliday(ClaimDate("2022-12-26")))
    }

    @Test
    fun `when new years day is on a week day then`() {
        assertTrue(isNewYearsDayHoliday(ClaimDate("2021-01-01")))
    }
    @Test
    fun `when new years day is on a saturday then 3rd Jan is a holiday but 1st of Jan is not a holiday`() {
        assertTrue(isNewYearsDayHoliday(ClaimDate("2022-01-03")))
        assertFalse(isNewYearsDayHoliday(ClaimDate("2022-01-01")))
    }
    @Test
    fun `when new years day is on a sunday then 2nd Jan is a holiday but 1st of Jan is not a holiday`() {
        assertTrue(isNewYearsDayHoliday(ClaimDate("2023-01-02")))
        assertFalse(isNewYearsDayHoliday(ClaimDate("2023-01-01")))
    }
    @Test
    fun `when 1st may is on a Monday then 1st May is a bank holiday`() {
        assertTrue(isMayDayHoliday(ClaimDate("2023-05-01")))
    }
    @Test
    fun `when 1st may is on a Tuesday then 7th May is a bank holiday but 1st of may is not a holiday`() {
        assertTrue(isMayDayHoliday(ClaimDate("2018-05-07")))
        assertFalse(isMayDayHoliday(ClaimDate("2018-05-01")))
    }
    @Test
    fun `when 25th may is on a Monday then 25th May is a bank holiday`() {
        assertTrue(isSpringHoliday(ClaimDate("2020-05-25")))
    }
    @Test
    fun `when 31st may is on a Monday then 31st May is a bank holiday`() {
        assertTrue(isSpringHoliday(ClaimDate("2021-05-31")))
    }
    @Test
    fun `when 25th august is on a Monday then 25th august is a bank holiday`() {
        assertTrue(isSummerHoliday(ClaimDate("2014-08-25")))
    }
    @Test
    fun `when 31st august is on a Monday then 31st august is a bank holiday`() {
        assertTrue(isSummerHoliday(ClaimDate("2020-08-31")))
    }
    @Test
    fun `Easter sunday in 2014 is 20 April`() {
        assertEquals(ClaimDate("2014-04-20"), easterSunday(2014) )
    }
    @Test
    fun `Easter sunday in 2021 is 4 April`() {
        assertEquals(ClaimDate("2021-04-04"), easterSunday(2021) )
    }
    @Test
    fun `Easter sunday in 2016 is 27 March`() {
        assertEquals(ClaimDate("2016-03-27"), easterSunday(2016) )
    }

    @Test
    fun `21st April 2014 is an Easter Monday holiday`() {
        assertTrue(isEasterMondayHoliday(ClaimDate("2014-04-21")))
    }
    @Test
    fun `28th March 2016 is an Easter Monday holiday`() {
        assertTrue(isEasterMondayHoliday(ClaimDate("2016-03-28")))
    }
    @Test
    fun `18th April 2014 is a Good Friday holiday`() {
        assertTrue(isGoodFridayHoliday(ClaimDate("2014-04-18")))
    }
    @Test
    fun `25th March 2016 is a Good Friday holiday`() {
        assertTrue(isGoodFridayHoliday(ClaimDate("2016-03-25")))
    }

    @Test
    fun `check that all bank holidays in 2021 return as true when checking if they are holidays`() {
        assertTrue(ClaimDate("2021-01-01").isBankHoliday())
        assertFalse(ClaimDate("2021-01-02").isBankHoliday())
        assertTrue(ClaimDate("2021-04-02").isBankHoliday())
        assertTrue(ClaimDate("2021-04-05").isBankHoliday())
        assertTrue(ClaimDate("2021-05-03").isBankHoliday())
        assertTrue(ClaimDate("2021-05-31").isBankHoliday())
        assertTrue(ClaimDate("2021-08-30").isBankHoliday())
        assertTrue(ClaimDate("2021-12-27").isBankHoliday())
        assertTrue(ClaimDate("2021-12-28").isBankHoliday())
    }
}