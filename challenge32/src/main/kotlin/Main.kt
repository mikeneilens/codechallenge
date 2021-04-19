import java.time.DayOfWeek
import java.time.Month

data class Claim( val amount:Int, val claimDetails:List<String>)

enum class ShiftType {
    MON_TO_THU,WEEKEND_OR_FRIDAY,BANK_HOLIDAY
}

fun calcStandbyClaim(date: ClaimDate, duration: Double, callOutLevel:String): Claim {

    var amount = 0
    val details = mutableListOf<String>()
    var noOfDays = 0.0

    while (noOfDays < duration) {
        val dateOfShift = date + noOfDays
        amount += rates.amount(shiftType(dateOfShift), callOutLevel)
        details.add(rates.detail(shiftType(dateOfShift), callOutLevel, dateOfShift))
        noOfDays += 1.0 / noOfShifts(dateOfShift)
    }

    return Claim(amount, details)
}

fun shiftType(dateOfShift: ClaimDate):ShiftType  {
    if (dateOfShift.isBankHoliday()) return ShiftType.BANK_HOLIDAY
    if (dateOfShift.isFriday()) return ShiftType.WEEKEND_OR_FRIDAY
    if (dateOfShift.isWeekend()) return ShiftType.WEEKEND_OR_FRIDAY
    return ShiftType.MON_TO_THU
}

fun noOfShifts(dateOfShift:ClaimDate) = if (dateOfShift.isWeekend() || dateOfShift.isBankHoliday()) 2 else 1




