
const val MON_TO_THU_RATE_A = 25
const val WEEKEND_RATE_A = 40
const val BANK_HOLIDAY_RATE_A = 80
const val MON_TO_THU_RATE_B = 15
const val WEEKEND_RATE_B = 25
const val BANK_HOLIDAY_RATE_B = 50

data class RotaItem(val typeOfShift:TypeOfShift, val callOutLevel:String) {
    fun description() = when (typeOfShift) {
        TypeOfShift.MON_TO_THU -> "Week day rate $callOutLevel"
        TypeOfShift.WEEKEND_OR_FRIDAY -> "Weekend rate $callOutLevel"
        TypeOfShift.BANK_HOLIDAY -> "Bank holiday rate $callOutLevel"
    }
}

val rates = mapOf(
    RotaItem(TypeOfShift.MON_TO_THU,"A") to MON_TO_THU_RATE_A,
    RotaItem(TypeOfShift.MON_TO_THU,"B") to MON_TO_THU_RATE_B,
    RotaItem(TypeOfShift.WEEKEND_OR_FRIDAY,"A") to WEEKEND_RATE_A,
    RotaItem(TypeOfShift.WEEKEND_OR_FRIDAY,"B") to WEEKEND_RATE_B,
    RotaItem(TypeOfShift.BANK_HOLIDAY,"A") to BANK_HOLIDAY_RATE_A,
    RotaItem(TypeOfShift.BANK_HOLIDAY,"B") to BANK_HOLIDAY_RATE_B,
)

fun Map<RotaItem, Int>.amount(typeOfShift:TypeOfShift, callOutLevel: String):Int {
    val rotaItem =  RotaItem(typeOfShift,callOutLevel)
    return get(rotaItem) ?: 0
}

fun Map<RotaItem, Int>.detail(typeOfShift:TypeOfShift, callOutLevel: String, dateOfShift:ClaimDate):String {
    val description =  RotaItem(typeOfShift,callOutLevel).description()
    val value = amount(typeOfShift,callOutLevel)
    return "$dateOfShift, $description, £$value"
}