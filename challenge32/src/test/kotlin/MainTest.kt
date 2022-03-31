import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `when the shift is on a week day end date of shift is the day after start date of shift `() {
        val result = datesOfShifts(ClaimDate("2022-03-30"),3.0)
        val expectedReslut = listOf(
            DateRange(start=ClaimDate("2022-03-30"), end=ClaimDate("2022-03-31")), //weds
            DateRange(start=ClaimDate("2022-03-31"), end=ClaimDate("2022-04-01")), //thurs
            DateRange(start=ClaimDate("2022-04-01"), end=ClaimDate("2022-04-02")), //friday
        )
        assertEquals(expectedReslut, result)
    }
    @Test
    fun `when the shift is on a weekend date the first shift starts and finnshes on the same day and the second shift finishes on the day after start date of shift `() {
        val result = datesOfShifts(ClaimDate("2022-04-02"),2.0)
        val expectedReslut = listOf(
            DateRange(start=ClaimDate("2022-04-02"), end=ClaimDate("2022-04-02")), //sat am
            DateRange(start=ClaimDate("2022-04-02"), end=ClaimDate("2022-04-03")), //sat pm
            DateRange(start=ClaimDate("2022-04-03"), end=ClaimDate("2022-04-03")), //sun am
            DateRange(start=ClaimDate("2022-04-03"), end=ClaimDate("2022-04-04")), //sun pm
        )
        assertEquals(expectedReslut, result)
    }
    @Test
    fun `when the shift runs over a bank holiday weekend the week days have one shift but weekends and bank holidays have two shifts for each day`() {
        val result = datesOfShifts(ClaimDate("2022-04-14"),6.0) // Maundy Thursday to Easter Tuesday, inclusive
        val expectedReslut = listOf(
            DateRange(start=ClaimDate("2022-04-14"), end=ClaimDate("2022-04-15")), //thurs
            DateRange(start=ClaimDate("2022-04-15"), end=ClaimDate("2022-04-15")), //good friday pm
            DateRange(start=ClaimDate("2022-04-15"), end=ClaimDate("2022-04-16")), //good friday am
            DateRange(start=ClaimDate("2022-04-16"), end=ClaimDate("2022-04-16")), //sat am
            DateRange(start=ClaimDate("2022-04-16"), end=ClaimDate("2022-04-17")), //sat pm
            DateRange(start=ClaimDate("2022-04-17"), end=ClaimDate("2022-04-17")), //sun am
            DateRange(start=ClaimDate("2022-04-17"), end=ClaimDate("2022-04-18")), //sun pm
            DateRange(start=ClaimDate("2022-04-18"), end=ClaimDate("2022-04-18")), //easter monday am
            DateRange(start=ClaimDate("2022-04-18"), end=ClaimDate("2022-04-19")), //easter monday pm
            DateRange(start=ClaimDate("2022-04-19"), end=ClaimDate("2022-04-20")), //tues
        )
        assertEquals(expectedReslut, result)
    }
    @Test
    fun `standby claim for 1 day at A Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),1.0,"A")
        val expectedResult = Claim(MON_TO_FRI_RATE_A,listOf("2021-04-19, Week day rate A, £${MON_TO_FRI_RATE_A}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at A Level on Friday which is not a holiday is weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),1.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A,listOf("2021-04-16, Weekend rate A, £${WEEKEND_RATE_A}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at A Level on Saturday or Sunday is twice the weekend rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2021-04-17"),1.0,"A")
        val expectedSaturdayResult = Claim(WEEKEND_RATE_A * 2,
            listOf(
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}"))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2021-04-18"),1.0,"A")
        val expectedSundayResult = Claim(WEEKEND_RATE_A * 2,
            listOf(
                "2021-04-18, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-18, Weekend rate A, £${WEEKEND_RATE_A}"))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for 1 day at A Level on a bank holiday is twice the bank holiday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-02"),1.0,"A")
        val expectedSaturdayResult = Claim(BANK_HOLIDAY_RATE_A * 2,
            listOf(
                "2021-04-02, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
                "2021-04-02, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}"))
        assertEquals(expectedSaturdayResult, result)
    }
@Test
    fun `standby claim for 1 day at B Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),1.0,"B")
        val expectedResult = Claim(MON_TO_FRI_RATE_B,listOf("2021-04-19, Week day rate B, £${MON_TO_FRI_RATE_B}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at B Level on Friday which is not a holiday is weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),1.0,"B")
        val expectedResult = Claim(WEEKEND_RATE_B,listOf("2021-04-16, Weekend rate B, £${WEEKEND_RATE_B}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 day at B Level on Saturday or Sunday is twice the weekend rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2021-04-17"),1.0,"B")
        val expectedSaturdayResult = Claim(WEEKEND_RATE_B * 2,
            listOf(
                "2021-04-17, Weekend rate B, £${WEEKEND_RATE_B}",
                "2021-04-17, Weekend rate B, £${WEEKEND_RATE_B}"))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2021-04-18"),1.0,"B")
        val expectedSundayResult = Claim(WEEKEND_RATE_B * 2,listOf(
            "2021-04-18, Weekend rate B, £${WEEKEND_RATE_B}",
            "2021-04-18, Weekend rate B, £${WEEKEND_RATE_B}"))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for 1 day at B Level on a bank holiday is twice the bank holiday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-02"),1.0,"B")
        val expectedSaturdayResult = Claim(BANK_HOLIDAY_RATE_B * 2,listOf(
            "2021-04-02, Bank holiday rate B, £${BANK_HOLIDAY_RATE_B}",
            "2021-04-02, Bank holiday rate B, £${BANK_HOLIDAY_RATE_B}"))
        assertEquals(expectedSaturdayResult, result)
    }

    @Test
    fun `standby claim for 2 day at A Level on Mon to Thursday which is not a holiday is weekday rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),2.0,"A")
        val expectedResult = Claim(MON_TO_FRI_RATE_A * 2,
            listOf(
                "2021-04-19, Week day rate A, £${MON_TO_FRI_RATE_A}",
                "2021-04-20, Week day rate A, £${MON_TO_FRI_RATE_A}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 2 day at A Level starting on Friday which is not a holiday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),2.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3,
            listOf("2021-04-16, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 4 day at A Level starting on Friday which is not a holiday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),4.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 5 + MON_TO_FRI_RATE_A,
            listOf("2021-04-16, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-18, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-18, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-19, Week day rate A, £${MON_TO_FRI_RATE_A}",
            ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 4 day at A Level starting on Friday which is a holiday is bank holiday rate X 5 and weekend rate X 3`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-02"),4.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3 + BANK_HOLIDAY_RATE_A * 5,
            listOf("2021-04-02, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //good friday am
                "2021-04-02, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //good friday pm
                "2021-04-03, Weekend rate A, £${WEEKEND_RATE_A}", //sat am
                "2021-04-03, Weekend rate A, £${WEEKEND_RATE_A}", //sat pm
                "2021-04-04, Weekend rate A, £${WEEKEND_RATE_A}", //sun am
                "2021-04-04, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //sun pm paid at bankholiday rate because it ends on bank holiday
                "2021-04-05, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //easter sun am
                "2021-04-05, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //easter sun pm
            ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 5 days at A Level starting on Thursday which is before good Friday is bank holiday rate X 6 plus weekend rate X 3`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-01"),5.0,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3 + BANK_HOLIDAY_RATE_A * 6,
            listOf(
                "2021-04-01, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //thursday now paid at bank holiday rate because it ends on good friday
                "2021-04-02, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //good friday am
                "2021-04-02, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //good friday pm
                "2021-04-03, Weekend rate A, £${WEEKEND_RATE_A}", //saturday am
                "2021-04-03, Weekend rate A, £${WEEKEND_RATE_A}", //saturday pm
                "2021-04-04, Weekend rate A, £${WEEKEND_RATE_A}", //sunday am
                "2021-04-04, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //sunday pm now paid at bank holiday rate because it ends on good friday
                "2021-04-05, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //easter monday am
                "2021-04-05, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //easter monday pm
            ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for 1 and a half days at A Level starting on Friday which is not a holiday is weekend rate spread over 2 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-16"),1.5,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 2, listOf(
            "2021-04-16, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}"))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for 1 and a half days at A Level starting on Saturday is weekend rate spread over 3 shifts`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-17"),1.5,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3, listOf(
            "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-18, Weekend rate A, £${WEEKEND_RATE_A}"))
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
        val expectedResult = Claim(MON_TO_FRI_RATE_A * 2,listOf(
            "2021-04-19, Week day rate A, £${MON_TO_FRI_RATE_A}",
            "2021-04-20, Week day rate A, £${MON_TO_FRI_RATE_A}" ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for an invalid duration of 1 and a third day at A Level on Weekend is rounded up to 3 X weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-17"),1.3,"A")
        val expectedResult = Claim(WEEKEND_RATE_A * 3, listOf(
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-17, Weekend rate A, £${WEEKEND_RATE_A}",
                "2021-04-18, Weekend rate A, £${WEEKEND_RATE_A}" ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for a complete week at A level is 420 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-19"),7.0,"A")
        val expectedResult = Claim(420, listOf(
            "2021-04-19, Week day rate A, £${MON_TO_FRI_RATE_A}",
            "2021-04-20, Week day rate A, £${MON_TO_FRI_RATE_A}",
            "2021-04-21, Week day rate A, £${MON_TO_FRI_RATE_A}",
            "2021-04-22, Week day rate A, £${MON_TO_FRI_RATE_A}",
            "2021-04-23, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-24, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-24, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-25, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-04-25, Weekend rate A, £${WEEKEND_RATE_A}" ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for a complete week that includes one bank holiday at A level is 609 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-03"),7.0,"A")
        val expectedResult = Claim(665, listOf(
            "2021-04-03, Weekend rate A, £${WEEKEND_RATE_A}", // saturday am
            "2021-04-03, Weekend rate A, £${WEEKEND_RATE_A}", // staturday pm
            "2021-04-04, Weekend rate A, £${WEEKEND_RATE_A}", // sunday am
            "2021-04-04, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", // sunday pm
            "2021-04-05, Bank holiday rate A, £$BANK_HOLIDAY_RATE_A", //monday am
            "2021-04-05, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}", //monday pm
            "2021-04-06, Week day rate A, £${MON_TO_FRI_RATE_A}", //tuesday
            "2021-04-07, Week day rate A, £${MON_TO_FRI_RATE_A}", //wednesday
            "2021-04-08, Week day rate A, £${MON_TO_FRI_RATE_A}", //thursday
            "2021-04-09, Weekend rate A, £${WEEKEND_RATE_A}", //friday
             ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for a complete week that includes one bank holiday at B level is 270 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-04-03"),7.0,"B")
        val expectedResult = Claim(413, listOf(
            "2021-04-03, Weekend rate B, £${WEEKEND_RATE_B}",  //sat am
            "2021-04-03, Weekend rate B, £${WEEKEND_RATE_B}",  //sat pm
            "2021-04-04, Weekend rate B, £${WEEKEND_RATE_B}",  //sun am
            "2021-04-04, Bank holiday rate B, £${BANK_HOLIDAY_RATE_B}",  //sun pm paid at bank holiday rate
            "2021-04-05, Bank holiday rate B, £${BANK_HOLIDAY_RATE_B}",  //bank holiday monday am
            "2021-04-05, Bank holiday rate B, £${BANK_HOLIDAY_RATE_B}", //bank holiday monday pm
            "2021-04-06, Week day rate B, £${MON_TO_FRI_RATE_B}", //tue
            "2021-04-07, Week day rate B, £${MON_TO_FRI_RATE_B}", //wed
            "2021-04-08, Week day rate B, £${MON_TO_FRI_RATE_B}", //thur
            "2021-04-09, Weekend rate B, £${WEEKEND_RATE_B}", //fri
        ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim when Christmas day is on a Saturday or a Sunday is calcualted using bank holiday rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2021-12-25"),1.0,"A")
        val expectedSaturdayResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2021-12-25, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2021-12-25, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2022-12-25"),1.0,"A")
        val expectedSundayResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2022-12-25, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2022-12-25, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim when boxing day is on a Saturday or a Sunday is calcualted using bank holiday rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2020-12-26"),1.0,"A")
        val expectedSaturdayResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2020-12-26, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2020-12-26, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2021-12-26"),1.0,"A")
        val expectedSundayResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2021-12-26, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2021-12-26, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim when New Years day is on a Saturday or a Sunday is calcualted using bank holiday rate`() {
        val saturdayResult = calcStandbyClaim(ClaimDate("2022-01-01"),1.0,"A")
        val expectedSaturdayResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2022-01-01, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2022-01-01, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedSaturdayResult, saturdayResult)

        val sundayResult = calcStandbyClaim(ClaimDate("2023-01-01"),1.0,"A")
        val expectedSundayResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2023-01-01, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2023-01-01, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedSundayResult, sundayResult)
    }

    @Test
    fun `standby claim for Monday after Christmas when Christmas day is on a Saturday is calcualted using weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2021-12-27"),1.0,"A")
        val expectedResult = Claim(2 * WEEKEND_RATE_A, listOf(
            "2021-12-27, Weekend rate A, £${WEEKEND_RATE_A}",
            "2021-12-27, Weekend rate A, £${WEEKEND_RATE_A}",
        ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for Monday after Christmas when Christmas day is on a Sunday is calcualted using bank holiday rate`() {
        val result = calcStandbyClaim(ClaimDate("2022-12-26"),1.0,"A")
        val expectedResult = Claim(2 * BANK_HOLIDAY_RATE_A, listOf(
            "2022-12-26, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
            "2022-12-26, Bank holiday rate A, £${BANK_HOLIDAY_RATE_A}",
        ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for Tuesday after Christmas when Christmas day is on a Sunday is calcualted using weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2022-12-27"),1.0,"A")
        val expectedResult = Claim(2 * WEEKEND_RATE_A, listOf(
            "2022-12-27, Weekend rate A, £${WEEKEND_RATE_A}",
            "2022-12-27, Weekend rate A, £${WEEKEND_RATE_A}",
        ))
        assertEquals(expectedResult, result)
    }
    @Test
    fun `standby claim for Monday after Christmas when Boxing day is on a Saturday is calcualted using weekend rate`() {
        val result = calcStandbyClaim(ClaimDate("2020-12-28"),1.0,"A")
        val expectedResult = Claim(2 * WEEKEND_RATE_A, listOf(
            "2020-12-28, Weekend rate A, £${WEEKEND_RATE_A}",
            "2020-12-28, Weekend rate A, £${WEEKEND_RATE_A}",
        ))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `standby claim for a complete calendar year is 23366 pounds`() {
        val result = calcStandbyClaim(ClaimDate("2021-01-01"), 365.0, "A")
        assertEquals(23779, result.amount)
    }
    //---- Tests for calculating bank holidays dynamically //
    @Test
    fun `when christmas day is a Wednesday then there is a bank holiday on 25th december and 26th december`() {
        assertTrue(UKHolidayCalculator.isChristmasDayHoliday(ClaimDate("2019-12-25")))
        assertTrue(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2019-12-26")))

    }
    @Test
    fun `when christmas day is a Friday then there is a bank holiday on 25th december and 28th december but not 26th December`() {
        assertTrue(UKHolidayCalculator.isChristmasDayHoliday(ClaimDate("2020-12-25")))
        assertTrue(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2020-12-28")))
        assertFalse(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2020-12-26")))
    }
    @Test
    fun `when christmas day is a Saturday then there is a bank holiday on 27th december and 28th december but not on 25th or 26th December`() {
        assertTrue(UKHolidayCalculator.isChristmasDayHoliday(ClaimDate("2021-12-27")))
        assertTrue(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2021-12-28")))
        assertFalse(UKHolidayCalculator.isChristmasDayHoliday(ClaimDate("2021-12-25")))
        assertFalse(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2021-12-26")))
    }
    @Test
    fun `when christmas day is a Sunday then there is a bank holiday on 26th december and 27th december but not on 25th December`() {
        assertTrue(UKHolidayCalculator.isChristmasDayHoliday(ClaimDate("2022-12-26")))
        assertTrue(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2022-12-27")))
        assertFalse(UKHolidayCalculator.isChristmasDayHoliday(ClaimDate("2022-12-25")))
        assertFalse(UKHolidayCalculator.isBoxingDayHoliday(ClaimDate("2022-12-26")))
    }

    @Test
    fun `when new years day is on a week day then`() {
        assertTrue(UKHolidayCalculator.isNewYearsDayHoliday(ClaimDate("2021-01-01")))
    }
    @Test
    fun `when new years day is on a saturday then 3rd Jan is a holiday but 1st of Jan is not a holiday`() {
        assertTrue(UKHolidayCalculator.isNewYearsDayHoliday(ClaimDate("2022-01-03")))
        assertFalse(UKHolidayCalculator.isNewYearsDayHoliday(ClaimDate("2022-01-01")))
    }
    @Test
    fun `when new years day is on a sunday then 2nd Jan is a holiday but 1st of Jan is not a holiday`() {
        assertTrue(UKHolidayCalculator.isNewYearsDayHoliday(ClaimDate("2023-01-02")))
        assertFalse(UKHolidayCalculator.isNewYearsDayHoliday(ClaimDate("2023-01-01")))
    }
    @Test
    fun `when 1st may is on a Monday then 1st May is a bank holiday`() {
        assertTrue(UKHolidayCalculator.isMayDayHoliday(ClaimDate("2023-05-01")))
    }
    @Test
    fun `when 1st may is on a Tuesday then 7th May is a bank holiday but 1st of may is not a holiday`() {
        assertTrue(UKHolidayCalculator.isMayDayHoliday(ClaimDate("2018-05-07")))
        assertFalse(UKHolidayCalculator.isMayDayHoliday(ClaimDate("2018-05-01")))
    }
    @Test
    fun `when 25th may is on a Monday then 25th May is a bank holiday`() {
        assertTrue(UKHolidayCalculator.isSpringHoliday(ClaimDate("2020-05-25")))
    }
    @Test
    fun `when 31st may is on a Monday then 31st May is a bank holiday`() {
        assertTrue(UKHolidayCalculator.isSpringHoliday(ClaimDate("2021-05-31")))
    }
    @Test
    fun `when 25th august is on a Monday then 25th august is a bank holiday`() {
        assertTrue(UKHolidayCalculator.isSummerHoliday(ClaimDate("2014-08-25")))
    }
    @Test
    fun `when 31st august is on a Monday then 31st august is a bank holiday`() {
        assertTrue(UKHolidayCalculator.isSummerHoliday(ClaimDate("2020-08-31")))
    }
    @Test
    fun `Easter sunday in 2014 is 20 April`() {
        assertEquals(LocalDate(2014,4,20), UKHolidayCalculator.easterSunday(2014) )
    }
    @Test
    fun `Easter sunday in 2021 is 4 April`() {
        assertEquals(LocalDate(2021,4,4), UKHolidayCalculator.easterSunday(2021) )
    }
    @Test
    fun `Easter sunday in 2016 is 27 March`() {
        assertEquals(LocalDate(2016,3,27), UKHolidayCalculator.easterSunday(2016) )
    }

    @Test
    fun `21st April 2014 is an Easter Monday holiday`() {
        assertTrue(UKHolidayCalculator.isEasterMondayHoliday(ClaimDate("2014-04-21")))
    }
    @Test
    fun `28th March 2016 is an Easter Monday holiday`() {
        assertTrue(UKHolidayCalculator.isEasterMondayHoliday(ClaimDate("2016-03-28")))
    }
    @Test
    fun `18th April 2014 is a Good Friday holiday`() {
        assertTrue(UKHolidayCalculator.isGoodFridayHoliday(ClaimDate("2014-04-18")))
    }
    @Test
    fun `25th March 2016 is a Good Friday holiday`() {
        assertTrue(UKHolidayCalculator.isGoodFridayHoliday(ClaimDate("2016-03-25")))
    }

    @Test
    fun `check that all bank holidays in 2021 return as true when checking if they are holidays`() {
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-01-01")))
        assertFalse(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-01-02")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-04-02")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-04-05")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-05-03")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-05-31")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-08-30")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-12-27")))
        assertTrue(UKHolidayCalculator.isBankHoliday(ClaimDate("2021-12-28")))
    }
}