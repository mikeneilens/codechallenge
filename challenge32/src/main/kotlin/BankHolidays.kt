import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

val holidayValidators = listOf(
    ClaimDate::isNewYearsDayHoliday,
    ClaimDate::isGoodFridayHoliday,
    ClaimDate::isEasterMondayHoliday,
    ClaimDate::isMayDayHoliday,
    ClaimDate::isSpringHoliday,
    ClaimDate::isSummerHoliday,
    ClaimDate::isChristmasDayHoliday,
    ClaimDate::isBoxingDayHoliday,
)

fun ClaimDate.isBankHoliday() = holidayValidators.any{it(this)}

fun ClaimDate.isChristmasDayHoliday() = (this == christmasDayHoliday(this.year))

fun christmasDayHoliday(year:Int) = weekdayOnOrAfter(ClaimDate("$year-12-25"))

fun weekdayOnOrAfter(date:ClaimDate) = when (date.dayOfWeek) {
    DayOfWeek.SATURDAY -> date + 2
    DayOfWeek.SUNDAY -> date + 1
    else -> date
}

fun ClaimDate.isBoxingDayHoliday() = (this == boxingDayHoliday(this.year))

fun boxingDayHoliday(year:Int):ClaimDate =  weekdayOnOrAfter(christmasDayHoliday(year) + 1)

fun ClaimDate.isNewYearsDayHoliday() = (this == newYearsDayHoliday(this.year))

fun newYearsDayHoliday(year:Int) = weekdayOnOrAfter(ClaimDate("$year-01-01"))

fun ClaimDate.isMayDayHoliday() =
    month == Month.MAY && dayOfWeek == DayOfWeek.MONDAY && dayOfMonth < 8

fun ClaimDate.isSpringHoliday() =
    month == Month.MAY && dayOfWeek == DayOfWeek.MONDAY && dayOfMonth > 24

fun ClaimDate.isSummerHoliday() =
    month == Month.AUGUST && dayOfWeek == DayOfWeek.MONDAY && dayOfMonth > 24

fun ClaimDate.isEasterMondayHoliday() = this == (easterSunday(this.year) + 1)

fun ClaimDate.isGoodFridayHoliday() = this == (easterSunday(this.year) - 2)

fun easterSunday(year:Int):ClaimDate = sundayAfter(paschalFullMoonDates.dateWith(year))

fun sundayAfter(claimDate:ClaimDate) = claimDate - (claimDate.dayOfWeek.value % 7) + 7
