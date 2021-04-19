import java.time.DayOfWeek
import java.time.LocalDate

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
        amount += rates.claim(dateOfShift.shiftType, calloutLevel)
        details.add("$dateOfShift, ${rates.description(dateOfShift.shiftType, calloutLevel)}")
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
    LocalDate.parse("2021-08-30"),
    LocalDate.parse("2021-12-27"),
    LocalDate.parse("2021-12-28"),
).contains(this)

