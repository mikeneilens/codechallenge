import java.time.DayOfWeek
import java.time.LocalDate

data class Claim( val amount:Int, val claimDetails:List<String>)

enum class ShiftType {
    MON_TO_THU,WEEKEND_OR_FRIDAY, BANK_HOLIDAY
}

fun calcStandbyClaim(date: LocalDate, duration: Double, calloutLevel:String, offset:Double = 0.0, totalClaim:Claim = Claim(0, emptyList()) ): Claim {
    if (offset >= duration) return totalClaim

    val dateOfShift = date.plusDays(offset.toLong())

    val newTotalClaim = Claim(
        totalClaim.amount + rates.claim(dateOfShift.shiftType, calloutLevel),
        totalClaim.claimDetails + "$dateOfShift, ${rates.description(dateOfShift.shiftType, calloutLevel)}"
    )

    return calcStandbyClaim(date, duration , calloutLevel, offset + 1.0 / dateOfShift.noOfShifts,  newTotalClaim)
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
    LocalDate.parse("2021-04-02"),
    LocalDate.parse("2021-04-05"),
).contains(this)

