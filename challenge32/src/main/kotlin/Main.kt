import java.time.DayOfWeek
import java.time.LocalDate

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate, duration: Double, calloutLevel:String, result:Claim = Claim(0, emptyList()) ): Claim {
    if (duration <= 0.0) return result

    val newResult = Claim(
        result.amount + rates.claim(date.shiftType, calloutLevel),
        result.claimDetails + "$date, ${rates.description(date.shiftType, calloutLevel)}"
    )

    return if (date.isWeekend() || date.isBankHoliday())
        calcStandbyClaim(date.calcDate(duration), duration - 0.5, calloutLevel, newResult)
    else
        calcStandbyClaim(date.plusDays(1), duration - 1.0, calloutLevel, newResult)
}

fun LocalDate.calcDate(duration:Double) = if (duration.isInt()) this else this.plusDays(1)
fun Double.isInt() = ((this * 2).toInt() % 2) == 0

val LocalDate.shiftType:ShiftType get() {
    if (isBankHoliday()) return ShiftType.BANK_HOLIDAY
    if (isFriday()) return ShiftType.WEEKEND
    if (isWeekend()) return ShiftType.WEEKEND
    return ShiftType.MON_TO_THU
}

fun LocalDate.isFriday() = dayOfWeek == DayOfWeek.FRIDAY
fun LocalDate.isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
fun LocalDate.isBankHoliday() = this == LocalDate.parse("2021-04-02")

