import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

interface BankHolidayCalculator {
    fun isBankHoliday(claimDate:ClaimDate):Boolean
    fun isChristmasDayHoliday(claimDate:ClaimDate):Boolean
    fun isBoxingDayHoliday(claimDate: ClaimDate):Boolean
    fun isNewYearsDayHoliday(claimDate: ClaimDate):Boolean
}

object UKHolidayCalculator:BankHolidayCalculator {
    private val holidayValidators by lazy { listOf(
        ::isNewYearsDayHoliday,
        ::isGoodFridayHoliday,
        ::isEasterMondayHoliday,
        ::isMayDayHoliday,
        ::isSpringHoliday,
        ::isSummerHoliday,
        ::isChristmasDayHoliday,
        ::isBoxingDayHoliday,
    )}

    override fun isBankHoliday(claimDate:ClaimDate) = holidayValidators.any{it(claimDate)}

    override fun isChristmasDayHoliday(claimDate: ClaimDate) = (claimDate == christmasDayHoliday(claimDate.year))

    fun christmasDayHoliday(year:Int) = weekdayOnOrAfter(ClaimDate("$year-12-25"))

    fun weekdayOnOrAfter(date:ClaimDate) = when (date.dayOfWeek) {
        DayOfWeek.SATURDAY -> date + 2
        DayOfWeek.SUNDAY -> date + 1
        else -> date
    }

    override fun isBoxingDayHoliday(claimDate: ClaimDate) = (claimDate == boxingDayHoliday(claimDate.year))

    fun boxingDayHoliday(year:Int):ClaimDate =  weekdayOnOrAfter(christmasDayHoliday(year) + 1)

    override fun isNewYearsDayHoliday(claimDate: ClaimDate) = (claimDate == newYearsDayHoliday(claimDate.year))

    fun newYearsDayHoliday(year:Int) = weekdayOnOrAfter(ClaimDate("$year-01-01"))

    fun isMayDayHoliday(claimDate: ClaimDate) =
        claimDate.month == Month.MAY && claimDate.dayOfWeek == DayOfWeek.MONDAY && claimDate.dayOfMonth < 8

    fun isSpringHoliday(claimDate: ClaimDate) =
        claimDate.month == Month.MAY && claimDate.dayOfWeek == DayOfWeek.MONDAY && claimDate.dayOfMonth > 24

    fun isSummerHoliday(claimDate: ClaimDate) =
        claimDate.month == Month.AUGUST && claimDate.dayOfWeek == DayOfWeek.MONDAY && claimDate.dayOfMonth > 24

    fun isEasterMondayHoliday(claimDate: ClaimDate) = claimDate == (easterSunday(claimDate.year) + 1)

    fun isGoodFridayHoliday(claimDate: ClaimDate) = claimDate == (easterSunday(claimDate.year) - 2)

    fun easterSunday(year:Int):ClaimDate = sundayAfter(paschalFullMoonDates.dateWith(year))

    fun sundayAfter(claimDate:ClaimDate) = claimDate - (claimDate.dayOfWeek.value % 7) + 7
}
