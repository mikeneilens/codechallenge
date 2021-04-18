import java.time.DayOfWeek
import java.time.LocalDate

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate, duration: Int, calloutLevel:String): Claim {
    if (date.isBankHoliday()) return Claim(rates.claim(ShiftType.BANK_HOLIDAY,calloutLevel) * 2,listOf("$date, ${rates.description(ShiftType.BANK_HOLIDAY,calloutLevel)}","$date, ${rates.description(ShiftType.BANK_HOLIDAY,calloutLevel)}"))
    if (date.isFriday()) return Claim(rates.claim(ShiftType.WEEKEND,calloutLevel) ,listOf("$date, ${rates.description(ShiftType.WEEKEND,calloutLevel)}"))
    if (date.isWeekend())  return Claim(rates.claim(ShiftType.WEEKEND,calloutLevel) * 2,listOf("$date, ${rates.description(ShiftType.WEEKEND,calloutLevel)}","$date, ${rates.description(ShiftType.WEEKEND,calloutLevel)}"))
    return Claim(rates.claim(ShiftType.MON_TO_THU,calloutLevel) ,listOf("$date, ${rates.description(ShiftType.MON_TO_THU,calloutLevel)}"))
}

fun LocalDate.isFriday() = dayOfWeek == DayOfWeek.FRIDAY
fun LocalDate.isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
fun LocalDate.isBankHoliday() = this == LocalDate.parse("2021-04-02")
