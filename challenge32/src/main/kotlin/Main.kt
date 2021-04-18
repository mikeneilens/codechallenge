import java.time.DayOfWeek
import java.time.LocalDate

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate, duration: Double, calloutLevel:String, offset:Double = 0.0, result:Claim = Claim(0, emptyList()) ): Claim {
    if (offset >= duration) return result

    val dateOfShift = date.plusDays(offset.toLong())

    val newResult = Claim(
        result.amount + rates.claim(dateOfShift.shiftType, calloutLevel),
        result.claimDetails + "$dateOfShift, ${rates.description(dateOfShift.shiftType, calloutLevel)}"
    )

    return if (dateOfShift.isWeekend() || dateOfShift.isBankHoliday())
        calcStandbyClaim(date, duration , calloutLevel, offset + 0.5,  newResult)
    else
        calcStandbyClaim(date, duration , calloutLevel, offset + 1.0, newResult)
}

val LocalDate.shiftType:ShiftType get() {
    if (isBankHoliday()) return ShiftType.BANK_HOLIDAY
    if (isFriday()) return ShiftType.WEEKEND
    if (isWeekend()) return ShiftType.WEEKEND
    return ShiftType.MON_TO_THU
}

fun LocalDate.isFriday() = dayOfWeek == DayOfWeek.FRIDAY
fun LocalDate.isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
fun LocalDate.isBankHoliday() = this == LocalDate.parse("2021-04-02")

