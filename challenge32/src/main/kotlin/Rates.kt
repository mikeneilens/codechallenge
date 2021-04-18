enum class ShiftType {
    MON_TO_THU,WEEKEND, BANK_HOLIDAY
}

const val MON_TO_THU_RATE_A = 25
const val WEEKEND_RATE_A = 40
const val BANK_HOLIDAY_RATE_A = 80
const val MON_TO_THU_RATE_B = 15
const val WEEKEND_RATE_B = 25
const val BANK_HOLIDAY_RATE_B = 50

data class RotaItem(val shiftType:ShiftType, val calloutLevel:String) {
    fun description() = when (shiftType) {
        ShiftType.MON_TO_THU -> "Week day rate $calloutLevel"
        ShiftType.WEEKEND -> "Weekend rate $calloutLevel"
        ShiftType.BANK_HOLIDAY -> "Bank holiday rate $calloutLevel"
    }
}

val rates = mapOf<RotaItem,Int>(
    RotaItem(ShiftType.MON_TO_THU,"A") to MON_TO_THU_RATE_A,
    RotaItem(ShiftType.MON_TO_THU,"B") to MON_TO_THU_RATE_B,
    RotaItem(ShiftType.WEEKEND,"A") to WEEKEND_RATE_A,
    RotaItem(ShiftType.WEEKEND,"B") to WEEKEND_RATE_B,
    RotaItem(ShiftType.BANK_HOLIDAY,"A") to BANK_HOLIDAY_RATE_A,
    RotaItem(ShiftType.BANK_HOLIDAY,"B") to BANK_HOLIDAY_RATE_B,
)

fun Map<RotaItem, Int>.claim(shiftType:ShiftType, calloutLevel: String):Int {
    val rotaItem =  RotaItem(shiftType,calloutLevel)
    return getValue(rotaItem)
}

fun Map<RotaItem, Int>.description(shiftType:ShiftType, calloutLevel: String):String {
    val rotaItem =  RotaItem(shiftType,calloutLevel)
    val value = getValue(rotaItem)
    return "${rotaItem.description()}, £$value"
}