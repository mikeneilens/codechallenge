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
    var offset = 0.0

    while (offset < duration) {
        val dateOfShift = date.plusDays(offset.toLong())
        amount += rates.amount(dateOfShift.shiftType, callOutLevel)
        details.add(rates.detail(dateOfShift.shiftType, callOutLevel, dateOfShift))
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

fun isChristmasDayHoliday(date: LocalDate) = (date == getChristmasDay(date.year))

fun getChristmasDay(year:Int):LocalDate {
    val december25 = LocalDate.parse("$year-12-25")
    if (december25.dayOfWeek == DayOfWeek.SATURDAY) return december25.plusDays(2)
    if (december25.dayOfWeek == DayOfWeek.SUNDAY) return december25.plusDays(1)
    return december25
}

fun isBoxingDayHoliday(date: LocalDate) = (date == getBoxingDay(date.year))

fun getBoxingDay(year:Int):LocalDate {
    val december26 = LocalDate.parse("$year-12-26")
    if (december26.dayOfWeek == DayOfWeek.SATURDAY) return december26.plusDays(2)
    if (december26.dayOfWeek == DayOfWeek.SUNDAY) return december26.plusDays(2)
    if (december26.dayOfWeek == DayOfWeek.MONDAY) return december26.plusDays(1)
    return december26
}

fun isNewYearsDayHoliday(date: LocalDate) = (date == getNewYearsDay(date.year))

fun getNewYearsDay(year:Int):LocalDate {
    val january01 = LocalDate.parse("$year-01-01")
    if (january01.dayOfWeek == DayOfWeek.SATURDAY) return january01.plusDays(2)
    if (january01.dayOfWeek == DayOfWeek.SUNDAY) return january01.plusDays(1)
    return january01
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



