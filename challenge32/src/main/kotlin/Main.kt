data class Claim( val amount:Int = 0, val claimDetails:List<String> = emptyList()) {

    constructor(dateOfShift:ClaimDate, shiftType:ShiftType, callOutLevel:String )
            :this(rates.amount(shiftType, callOutLevel),listOf(rates.detail(shiftType, callOutLevel, dateOfShift)))

    operator fun plus(other:Claim) = Claim(this.amount + other.amount, this.claimDetails + other.claimDetails)
}

enum class ShiftType {
    MON_TO_THU,WEEKEND_OR_FRIDAY,BANK_HOLIDAY
}

fun calcStandbyClaim(date: ClaimDate, duration: Double, callOutLevel:String): Claim {

    var noOfDays = 0.0
    var claim = Claim()

    while (noOfDays < duration) {
        val dateOfShift = date + noOfDays
        claim += Claim(dateOfShift, shiftType(dateOfShift), callOutLevel)
        noOfDays += 1.0 / noOfShifts(dateOfShift)
    }

    return claim
}

fun shiftType(dateOfShift: ClaimDate):ShiftType  {
    if (dateOfShift.isBankHoliday()) return ShiftType.BANK_HOLIDAY
    if (dateOfShift.isFriday()) return ShiftType.WEEKEND_OR_FRIDAY
    if (dateOfShift.isWeekend()) return ShiftType.WEEKEND_OR_FRIDAY
    return ShiftType.MON_TO_THU
}

fun noOfShifts(dateOfShift:ClaimDate) = if (dateOfShift.isWeekend() || dateOfShift.isBankHoliday()) 2 else 1




