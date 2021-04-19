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
fun LocalDate.isBankHoliday() = listOf(
    LocalDate.parse("2021-01-01"),
    LocalDate.parse("2021-04-02"),
    LocalDate.parse("2021-04-05"),
    LocalDate.parse("2021-05-03"),
    LocalDate.parse("2021-05-31"),
    LocalDate.parse("2021-08-30"),
    LocalDate.parse("2021-12-27"),
    LocalDate.parse("2021-12-28"),
).contains(this)

fun isChristmasDayHoliday(date: LocalDate): Boolean {
    return if (date.month == Month.DECEMBER) {
        if (date.dayOfMonth == 25 && !date.isWeekend()) true
        else if (date.dayOfMonth == 26 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else if (date.dayOfMonth == 27 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else false
    } else false
}
fun isBoxingDayHoliday(date: LocalDate): Boolean {
    return if (date.month == Month.DECEMBER) {
        if (date.dayOfMonth == 28 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else if (date.dayOfMonth == 27 && date.dayOfWeek == DayOfWeek.TUESDAY) true
        else if (date.dayOfMonth == 26 && date.dayOfWeek == DayOfWeek.MONDAY) false
        else if (date.dayOfMonth == 26 && !date.isWeekend()) true
        else false
    } else false
}
fun isNewYearsDayHoliday(date: LocalDate): Boolean {
    return if (date.month == Month.JANUARY) {
        if (date.dayOfMonth == 1 && !date.isWeekend()) true
        else if (date.dayOfMonth == 2 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else if (date.dayOfMonth == 3 && date.dayOfWeek == DayOfWeek.MONDAY) true
        else false
    } else false
}



