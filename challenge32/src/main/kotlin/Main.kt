data class Claim( val amount:Int = 0, val claimDetails:List<String> = emptyList()) {

    operator fun plus(other:Claim) = Claim(this.amount + other.amount, this.claimDetails + other.claimDetails)

}

sealed class TypeOfShift(val noOfShifts:Int, val rates:Map<String, Int>, val description:String) {
    class MON_TO_THU(noOfShifts: Int):TypeOfShift(noOfShifts, mapOf("A" to MON_TO_THU_RATE_A,"B" to MON_TO_THU_RATE_B), "Week day rate")
    class WEEKEND_OR_FRIDAY(noOfShifts: Int):TypeOfShift(noOfShifts, mapOf("A" to WEEKEND_RATE_A, "B" to WEEKEND_RATE_B), "Weekend rate")
    class BANK_HOLIDAY(noOfShifts: Int):TypeOfShift(noOfShifts, mapOf("A" to BANK_HOLIDAY_RATE_A, "B" to BANK_HOLIDAY_RATE_B), "Bank holiday rate")

    fun description(dateOfShift:ClaimDate, callOutLevel:String) = "$dateOfShift, $description $callOutLevel, £${rates[callOutLevel] ?: 0}"
}

fun createClaim(dateOfShift:ClaimDate, typeOfShift:TypeOfShift, callOutLevel:String)
    = Claim(typeOfShift.rates[callOutLevel] ?: 0,listOf(typeOfShift.description(dateOfShift, callOutLevel)))

fun calcStandbyClaim(date:ClaimDate, duration:Double, callOutLevel:String): Claim {
    var noOfDays = 0.0
    var claim = Claim()

    while (noOfDays < duration) {
        val dateOfShift = date + noOfDays
        claim += createClaim(dateOfShift, typeOfShift(dateOfShift), callOutLevel)
        noOfDays += 1.0 / typeOfShift(dateOfShift).noOfShifts
    }

    return claim
}

fun typeOfShift(dateOfShift:ClaimDate):TypeOfShift  {
    if (dateOfShift.isBankHoliday()) return TypeOfShift.BANK_HOLIDAY(2)
    if (dateOfShift.isFriday()) return TypeOfShift.WEEKEND_OR_FRIDAY(1)
    if (dateOfShift.isWeekend()) return TypeOfShift.WEEKEND_OR_FRIDAY(2)
    return TypeOfShift.MON_TO_THU(1)
}



