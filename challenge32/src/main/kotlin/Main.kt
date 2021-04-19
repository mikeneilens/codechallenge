import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

data class Claim( val amount:Int, val claimDetails:List<String>)

enum class ShiftType {
    MON_TO_THU,WEEKEND_OR_FRIDAY, BANK_HOLIDAY
}

fun calcStandbyClaim(date: LocalDate, duration: Double, calloutLevel:String): Claim {

    var amount = 0
    val details = mutableListOf<String>()
    var offset = 0.0

    while (offset < duration) {
        val dateOfShift = date.plusDays(offset.toLong())
        amount += rates.amount(dateOfShift.shiftType, calloutLevel)
        details.add(rates.detail(dateOfShift.shiftType, calloutLevel, dateOfShift))
        offset += 1.0 / dateOfShift.noOfShifts
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
    ::isChristmasDayHoliday
    ,::isBoxingDayHoliday
    ,::isNewYearsDayHoliday
    ,::isGoodFridayHoliday
    ,::isEasterMondayHoliday
    ,::isMayDayHoliday
    ,::isSpringHoliday
    ,::isSummerHoliday
)

fun LocalDate.isBankHoliday():Boolean = holidayValidators.map{it(this)}.contains(true)

fun isChristmasDayHoliday(date: LocalDate): Boolean {
    return if (date.month == Month.DECEMBER) {
        if (date.dayOfMonth == 25 && !date.isWeekend()) true
        else if (date.dayOfMonth == 26 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else (date.dayOfMonth == 27 && date.dayOfWeek == DayOfWeek.MONDAY)
    } else false
}
fun isBoxingDayHoliday(date: LocalDate): Boolean {
    return if (date.month == Month.DECEMBER) {
        if (date.dayOfMonth == 28 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else if (date.dayOfMonth == 27 && date.dayOfWeek == DayOfWeek.TUESDAY) true
        else if (date.dayOfMonth == 28 && date.dayOfWeek == DayOfWeek.TUESDAY) true
        else if (date.dayOfMonth == 26 && date.dayOfWeek == DayOfWeek.MONDAY) false
        else (date.dayOfMonth == 26 && !date.isWeekend())
    } else false
}
fun isNewYearsDayHoliday(date: LocalDate): Boolean {
    return if (date.month == Month.JANUARY) {
        if (date.dayOfMonth == 1 && !date.isWeekend()) true
        else if (date.dayOfMonth == 2 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else (date.dayOfMonth == 3 && date.dayOfWeek == DayOfWeek.MONDAY)
    } else false
}

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



