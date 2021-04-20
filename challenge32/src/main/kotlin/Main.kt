data class Claim( val amount:Int = 0, val claimDetails:List<String> = emptyList()) {

    constructor(dateOfShift:ClaimDate, typeOfShift:TypeOfShift, callOutLevel:String )
            :this(rates.amount(typeOfShift, callOutLevel),listOf(rates.detail(typeOfShift, callOutLevel, dateOfShift)))

    operator fun plus(other:Claim) = Claim(this.amount + other.amount, this.claimDetails + other.claimDetails)
}

enum class TypeOfShift {
    MON_TO_THU,WEEKEND_OR_FRIDAY,BANK_HOLIDAY
}

fun calcStandbyClaim(date:ClaimDate, duration:Double, callOutLevel:String): Claim {

    var noOfDays = 0.0
    var claim = Claim()

    while (noOfDays < duration) {
        val dateOfShift = date + noOfDays
        claim += Claim(dateOfShift, typeOfShift(dateOfShift), callOutLevel)
        noOfDays += 1.0 / noOfShifts(dateOfShift)
    }

    return claim
}

fun typeOfShift(dateOfShift:ClaimDate):TypeOfShift  {
    if (dateOfShift.isBankHoliday()) return TypeOfShift.BANK_HOLIDAY
    if (dateOfShift.isFriday()) return TypeOfShift.WEEKEND_OR_FRIDAY
    if (dateOfShift.isWeekend()) return TypeOfShift.WEEKEND_OR_FRIDAY
    return TypeOfShift.MON_TO_THU
}

fun noOfShifts(dateOfShift:ClaimDate) = if (dateOfShift.isWeekend() || dateOfShift.isBankHoliday()) 2 else 1




