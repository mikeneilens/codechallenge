import java.time.DayOfWeek
import java.time.LocalDate

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate, duration: Int, calloutLevel:String): Claim {
    if (date.isBankHoliday()) return Claim(rates.claim(ShiftType.BANK_HOLIDAY,"A") * 2,listOf("$date, ${rates.description(ShiftType.BANK_HOLIDAY,"A")}","$date, ${rates.description(ShiftType.BANK_HOLIDAY,"A")}"))
    if (date.isFriday()) return Claim(rates.claim(ShiftType.WEEKEND,"A") ,listOf("$date, ${rates.description(ShiftType.WEEKEND,"A")}"))
    if (date.isWeekend())  return Claim(rates.claim(ShiftType.WEEKEND,"A") * 2,listOf("$date, ${rates.description(ShiftType.WEEKEND,"A")}","$date, ${rates.description(ShiftType.WEEKEND,"A")}"))
    return Claim(rates.claim(ShiftType.MON_TO_THU,"A") ,listOf("$date, ${rates.description(ShiftType.MON_TO_THU,"A")}"))
}

fun LocalDate.isFriday() = dayOfWeek == DayOfWeek.FRIDAY
fun LocalDate.isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
fun LocalDate.isBankHoliday() = this == LocalDate.parse("2021-04-02")
