data class Claim( val amount:Int = 0, val claimDetails:List<String> = emptyList()) {
    operator fun plus(other:Claim) = Claim(this.amount + other.amount, this.claimDetails + other.claimDetails)
}

class TypeOfShift(val noOfShifts:Int, val rates:Map<String, Int>, private val description:String, val isApplicable:(ClaimDate)->Boolean) {
    fun description(dateOfShift:ClaimDate, callOutLevel:String) = "$dateOfShift, $description $callOutLevel, £${rates[callOutLevel] ?: 0}"
}

val BANK_HOLIDAY = TypeOfShift(2, mapOf("A" to BANK_HOLIDAY_RATE_A, "B" to BANK_HOLIDAY_RATE_B), "Bank holiday rate", ClaimDate::isBankHoliday)
val FRIDAY = TypeOfShift (1, mapOf("A" to WEEKEND_RATE_A, "B" to WEEKEND_RATE_B), "Weekend rate", ClaimDate::isFriday )
val WEEKEND = TypeOfShift(2, mapOf("A" to WEEKEND_RATE_A, "B" to WEEKEND_RATE_B), "Weekend rate", ClaimDate::isWeekend)
val MON_TO_THU = TypeOfShift(1, mapOf("A" to MON_TO_THU_RATE_A,"B" to MON_TO_THU_RATE_B), "Week day rate") { true }

fun typeOfShiftForDate(dateOfShift:ClaimDate):TypeOfShift = listOf(BANK_HOLIDAY, FRIDAY, WEEKEND, MON_TO_THU).first {it.isApplicable(dateOfShift)}

fun createClaim(dateOfShift:ClaimDate, typeOfShift:TypeOfShift, callOutLevel:String)
    = Claim(typeOfShift.rates[callOutLevel] ?: 0,listOf(typeOfShift.description(dateOfShift, callOutLevel)))

fun calcStandbyClaim(date:ClaimDate, duration:Double, callOutLevel:String): Claim {
    val calculateClaimForEachShift = {dateOfShift:ClaimDate -> createClaim(dateOfShift, typeOfShiftForDate(dateOfShift), callOutLevel)}
    return  datesOfShifts(date, duration)
        .map (calculateClaimForEachShift)
        .sumClaims()
}

fun List<Claim>.sumClaims() = fold(Claim()) { result, claim -> result + claim }

tailrec fun datesOfShifts(date:ClaimDate, duration:Double, noOfDays:Double = 0.0, result:List<ClaimDate> = emptyList() ):List<ClaimDate> =
    if (noOfDays >= duration) result
    else {
        val dateOfShift = date + noOfDays
        datesOfShifts(date, duration, noOfDays + 1.0 / typeOfShiftForDate(dateOfShift).noOfShifts, result + dateOfShift )
    }





