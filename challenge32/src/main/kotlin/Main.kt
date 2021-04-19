import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

data class Claim( val amount:Int, val claimDetails:List<String>)

enum class ShiftType {
    MON_TO_THU,WEEKEND_OR_FRIDAY, BANK_HOLIDAY
}

fun calcStandbyClaim(date: LocalDate, duration: Double, callOutLevel:String): Claim {

    var amount = 0
    val details = mutableListOf<String>()
    var noOfDays = 0.0

    while (noOfDays < duration) {
        val dateOfShift = date.plusDays(noOfDays.toLong())
        amount += rates.amount(dateOfShift.shiftType, callOutLevel)
        details.add(rates.detail(dateOfShift.shiftType, callOutLevel, dateOfShift))
        noOfDays += 1.0 / dateOfShift.noOfShifts
    }

    return Claim(amount, details)
}

val LocalDate.shiftType:ShiftType get() {
    if (isBankHoliday()) return ShiftType.BANK_HOLIDAY
    if (isFriday()) return ShiftType.WEEKEND_OR_FRIDAY
    if (isWeekend()) return ShiftType.WEEKEND_OR_FRIDAY
    return ShiftType.MON_TO_THU
}

val LocalDate.noOfShifts:Int get() = if (isWeekend() || isBankHoliday()) 2 else 1

fun LocalDate.isFriday() = dayOfWeek == DayOfWeek.FRIDAY
fun LocalDate.isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY

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

fun LocalDate.isBankHoliday():Boolean = holidayValidators.any{it(this)}

fun isChristmasDayHoliday(date: LocalDate) = (date == getChristmasDayHoliday(date.year))

fun getChristmasDayHoliday(year:Int):LocalDate = weekdayOnOrAfter(LocalDate.parse("$year-12-25"))

fun weekdayOnOrAfter(date:LocalDate) =
    if (date.dayOfWeek == DayOfWeek.SATURDAY) date.plusDays(2)
    else if (date.dayOfWeek == DayOfWeek.SUNDAY) date.plusDays(1)
    else date

fun isBoxingDayHoliday(date: LocalDate) = (date == getBoxingDayHoliday(date.year))

fun getBoxingDayHoliday(year:Int):LocalDate =  weekdayOnOrAfter(getChristmasDayHoliday(year).plusDays(1))

fun isNewYearsDayHoliday(date: LocalDate) = (date == getNewYearsDayHoliday(date.year))

fun getNewYearsDayHoliday(year:Int):LocalDate = weekdayOnOrAfter(LocalDate.parse("$year-01-01"))

fun isMayDayHoliday(date: LocalDate) =
    date.month == Month.MAY && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth < 8

fun isSpringHoliday(date: LocalDate) =
    date.month == Month.MAY && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth > 24

fun isSummerHoliday(date: LocalDate) =
    date.month == Month.AUGUST && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth > 24

fun isEasterMondayHoliday(date: LocalDate) =
    date == getEasterSunday(date.year).plusDays(1)

fun isGoodFridayHoliday(date: LocalDate) =
    date == getEasterSunday(date.year).minusDays(2)

fun getEasterSunday(year:Int):LocalDate {
    val (month, day) = paschalFullMoonDates[year % 19 ]
    val paschalFullMoonDate = LocalDate.parse("$year-$month-$day")
    return when (paschalFullMoonDate.dayOfWeek) {
        DayOfWeek.SUNDAY -> paschalFullMoonDate.plusDays(7)
        DayOfWeek.MONDAY -> paschalFullMoonDate.plusDays(6)
        DayOfWeek.TUESDAY -> paschalFullMoonDate.plusDays(5)
        DayOfWeek.WEDNESDAY -> paschalFullMoonDate.plusDays(4)
        DayOfWeek.THURSDAY -> paschalFullMoonDate.plusDays(3)
        DayOfWeek.FRIDAY -> paschalFullMoonDate.plusDays(2)
        DayOfWeek.SATURDAY -> paschalFullMoonDate.plusDays(1)
        else -> paschalFullMoonDate.plusDays(1)
    }
}



