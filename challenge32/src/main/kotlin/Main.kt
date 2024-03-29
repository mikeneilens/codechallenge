data class Claim( val amount:Int = 0, val claimDetails:List<String> = emptyList()) {
    operator fun plus(other:Claim) = Claim(this.amount + other.amount, this.claimDetails + other.claimDetails)
}

class TypeOfShift(val noOfShifts:Int, val rates:Map<String, Int>, private val description:String, val isApplicable:(ClaimDate)->Boolean) {
    fun description(dateOfShift:ClaimDate, callOutLevel:String) = "$dateOfShift, $description $callOutLevel, £${rates[callOutLevel] ?: 0}"
}

fun ClaimDate.isBankHolidayShift() =
    isBankHoliday && !isChristmasDayHoliday && !isBoxingDayHoliday && !isNewYearsDayHoliday
            || isChristmasDay
            || isBoxingDay
            || isNewYearsDay
            || (isChristmasDayHoliday && !christmasDayIsAtTheWeekend)
            || (isBoxingDayHoliday && !boxingDayIsAtTheWeekend && !christmasDayIsAtTheWeekend)
            || (isNewYearsDayHoliday && !christmasDayIsAtTheWeekend)

fun ClaimDate.isWeekendShift() =
    (isChristmasDayHoliday && christmasDayIsAtTheWeekend)
            || (isBoxingDayHoliday && (christmasDayIsAtTheWeekend || boxingDayIsAtTheWeekend))
            || (isNewYearsDayHoliday && christmasDayIsAtTheWeekend)
            || isWeekend

val BANK_HOLIDAY = TypeOfShift(2, mapOf("A" to BANK_HOLIDAY_RATE_A, "B" to BANK_HOLIDAY_RATE_B), "Bank holiday rate", ClaimDate::isBankHolidayShift)
val WEEKEND = TypeOfShift(2, mapOf("A" to WEEKEND_RATE_A, "B" to WEEKEND_RATE_B), "Weekend rate", ClaimDate::isWeekendShift)
val MON_TO_FRI = TypeOfShift(1, mapOf("A" to MON_TO_FRI_RATE_A,"B" to MON_TO_FRI_RATE_B), "Week day rate") { true }

fun typeOfShiftForDate(dateOfShift:ClaimDate):TypeOfShift = listOf(BANK_HOLIDAY, WEEKEND, MON_TO_FRI).first {it.isApplicable(dateOfShift)}

fun createClaim(dateOfShift:ClaimDate, typeOfShift:TypeOfShift, callOutLevel:String)
    = Claim(typeOfShift.rates[callOutLevel] ?: 0,listOf(typeOfShift.description(dateOfShift, callOutLevel)))

fun calcStandbyClaim(date:ClaimDate, duration:Double, callOutLevel:String): Claim {
    val calculateClaimForEachShift = {dateOfShift:DateRange ->
        val claimForStart =  createClaim(dateOfShift.start, typeOfShiftForDate(dateOfShift.start), callOutLevel)
        if (dateOfShift.start == dateOfShift.end)
            claimForStart
        else {
            val claimForEnd =  createClaim(dateOfShift.start, typeOfShiftForDate(dateOfShift.end), callOutLevel)
            if (claimForStart.amount > claimForEnd.amount) claimForStart else claimForEnd
        }
    }
    return  datesOfShifts(date, duration)
        .map (calculateClaimForEachShift)
        .sumClaims()
}

fun List<Claim>.sumClaims() = fold(Claim()) { result, claim -> result + claim }

data class DateRange(val start:ClaimDate, val end:ClaimDate)

tailrec fun datesOfShifts(dateRange:ClaimDate, duration:Double, noOfDays:Double = 0.0, result:List<DateRange> = emptyList() ):List<DateRange> =
    if (noOfDays >= duration) result
    else {
        val startDateOfShift = dateRange + noOfDays
        val endDateOfShift = if ( isNormalWeekDayShift(startDateOfShift) || isWeekendOrBankHolidayLateShift(noOfDays)) startDateOfShift + 1 else startDateOfShift
        datesOfShifts(dateRange, duration, noOfDays + 1.0 / typeOfShiftForDate(startDateOfShift).noOfShifts, result + DateRange(startDateOfShift, endDateOfShift))
    }

private fun isNormalWeekDayShift(startDateOfShift: ClaimDate) = typeOfShiftForDate(startDateOfShift).noOfShifts == 1

private fun isWeekendOrBankHolidayLateShift(noOfDays: Double) = ((noOfDays * 2) % 2 == 1.0)





