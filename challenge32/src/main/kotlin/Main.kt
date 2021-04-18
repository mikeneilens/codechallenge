import java.time.DayOfWeek
import java.time.LocalDate

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate, duration: Int, calloutLevel:String): Claim {
    if (date.isBankHoliday()) return Claim(rates.claim(ShiftType.BANK_HOLIDAY,"A") * 2,listOf("2021-04-02, ${rates.description(ShiftType.BANK_HOLIDAY,"A")}","2021-04-02, ${rates.description(ShiftType.BANK_HOLIDAY,"A")}"))
    if (date.dayOfWeek == DayOfWeek.FRIDAY) return Claim(rates.claim(ShiftType.WEEKEND,"A") ,listOf("2021-04-16, ${rates.description(ShiftType.WEEKEND,"A")}"))
    if (date.dayOfWeek == DayOfWeek.SATURDAY) return Claim(rates.claim(ShiftType.WEEKEND,"A") * 2,listOf("2021-04-17, ${rates.description(ShiftType.WEEKEND,"A")}","2021-04-17, ${rates.description(ShiftType.WEEKEND,"A")}"))
    if (date.dayOfWeek == DayOfWeek.SUNDAY) return Claim(rates.claim(ShiftType.WEEKEND,"A") * 2,listOf("2021-04-18, ${rates.description(ShiftType.WEEKEND,"A")}","2021-04-18, ${rates.description(ShiftType.WEEKEND,"A")}"))
    return Claim(rates.claim(ShiftType.MON_TO_THU,"A") ,listOf("2021-04-19, ${rates.description(ShiftType.MON_TO_THU,"A")}"))
}

fun LocalDate.isBankHoliday() = this == LocalDate.parse("2021-04-02")
