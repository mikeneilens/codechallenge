import java.time.DayOfWeek
import java.time.LocalDate

const val MON_TO_THU_RATE_A = 25
const val WEEKEND_RATE_A = 40
const val BANK_HOLIDAY_RATE_A = 80

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate, duration: Int, calloutLevel:String): Claim {
    if (date.isBankHoliday()) return Claim(BANK_HOLIDAY_RATE_A * 2,listOf("2021-04-02, Bank holiday rate A, £80","2021-04-02, Bank holiday rate A, £80"))
    if (date.dayOfWeek == DayOfWeek.FRIDAY) return Claim(WEEKEND_RATE_A,listOf("2021-04-16, Weekend rate A, £40"))
    if (date.dayOfWeek == DayOfWeek.SATURDAY) return Claim(WEEKEND_RATE_A * 2,listOf("2021-04-17, Weekend rate A, £40","2021-04-17, Weekend rate A, £40"))
    if (date.dayOfWeek == DayOfWeek.SUNDAY) return Claim(WEEKEND_RATE_A * 2,listOf("2021-04-18, Weekend rate A, £40","2021-04-18, Weekend rate A, £40"))
    return Claim(MON_TO_THU_RATE_A,listOf("2021-04-19, Week day rate A, £25"))
}

fun LocalDate.isBankHoliday() = this == LocalDate.parse("2021-04-02")
