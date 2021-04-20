import java.time.DayOfWeek
import java.time.Month

val holidayValidators = listOf(
    ::isNewYearsDayHoliday,
    ::isGoodFridayHoliday,
    ::isEasterMondayHoliday,
    ::isMayDayHoliday,
    ::isSpringHoliday,
    ::isSummerHoliday,
    ::isChristmasDayHoliday,
    ::isBoxingDayHoliday,
)

fun ClaimDate.isBankHoliday():Boolean = holidayValidators.any{it(this)}

fun isChristmasDayHoliday(date: ClaimDate) = (date == christmasDayHoliday(date.year))

fun christmasDayHoliday(year:Int):ClaimDate = weekdayOnOrAfter(ClaimDate("$year-12-25"))

fun weekdayOnOrAfter(date:ClaimDate) = when (date.dayOfWeek) {
    DayOfWeek.SATURDAY -> date + 2
    DayOfWeek.SUNDAY -> date + 1
    else -> date
}

fun isBoxingDayHoliday(date: ClaimDate) = (date == boxingDayHoliday(date.year))

fun boxingDayHoliday(year:Int):ClaimDate =  weekdayOnOrAfter(christmasDayHoliday(year) + 1)

fun isNewYearsDayHoliday(date: ClaimDate) = (date == newYearsDayHoliday(date.year))

fun newYearsDayHoliday(year:Int):ClaimDate = weekdayOnOrAfter(ClaimDate("$year-01-01"))

fun isMayDayHoliday(date: ClaimDate) =
    date.month == Month.MAY && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth < 8

fun isSpringHoliday(date: ClaimDate) =
    date.month == Month.MAY && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth > 24

fun isSummerHoliday(date: ClaimDate) =
    date.month == Month.AUGUST && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth > 24

fun isEasterMondayHoliday(date: ClaimDate) =
    date == (easterSunday(date.year) + 1)

fun isGoodFridayHoliday(date: ClaimDate) =
    date == (easterSunday(date.year) - 2)

fun easterSunday(year:Int):ClaimDate {
    val (month, day) = paschalFullMoonDates[year % 19 ]
    val paschalFullMoonDate = ClaimDate("$year-$month-$day")
    return when (paschalFullMoonDate.dayOfWeek) {
        DayOfWeek.SUNDAY -> paschalFullMoonDate + 7
        DayOfWeek.MONDAY -> paschalFullMoonDate + 6
        DayOfWeek.TUESDAY -> paschalFullMoonDate + 5
        DayOfWeek.WEDNESDAY -> paschalFullMoonDate + 4
        DayOfWeek.THURSDAY -> paschalFullMoonDate + 3
        DayOfWeek.FRIDAY -> paschalFullMoonDate + 2
        DayOfWeek.SATURDAY -> paschalFullMoonDate + 1
        else -> paschalFullMoonDate + 1 //java enums can be null!
    }
}