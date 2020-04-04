package mike

fun main(args: Array<String>) {
    println(formatTime(3662))
    println(formatTime(0))
    println(formatTime(1))
    println(formatTime(60))
    println(formatTime(3600))
    println(formatTime(86400))
    println(formatTime(31536000))
}

enum class UnitOfTime(val secondsPerUnit: Int, val maxSize:Int) {
    year(31536000, 100),
    day(86400, 365),
    hour(3600, 24),
    minute(60, 60),
    second(1, 60);

    fun toUnitAndQuantity(timeInSeconds: Int):UnitAndQuantity {
        val value = timeInSeconds / this.secondsPerUnit % this.maxSize
        return UnitAndQuantity(this, value)
    }
}

class UnitAndQuantity(private val unit:UnitOfTime, val value:Int) {
    override fun toString(): String = if (value == 1) "$value $unit" else "$value ${unit}s"
}

fun formatTime(timeInSeconds:Int): String {
    if (timeInSeconds == 0) return "none"

    val unitAndQuantities = createUnitAndQuantities(timeInSeconds)
    val nonZeroUnitAndQuantities = unitAndQuantities.filter { unitAndQuantity:UnitAndQuantity -> unitAndQuantity.value > 0 }

    return convertUnitAndQuantitiesToString(nonZeroUnitAndQuantities)
}


fun createUnitAndQuantities(timeInSeconds: Int):List<UnitAndQuantity> {
    return UnitOfTime.values().map{ unit -> unit.toUnitAndQuantity(timeInSeconds)}
}

fun convertUnitAndQuantitiesToString(unitAndQuantities:List<UnitAndQuantity>)  = when (unitAndQuantities.size) {
    1 -> "${unitAndQuantities[0]}"
    2 ->  "${unitAndQuantities[0]} and ${unitAndQuantities[1]}"
    else -> {
        val firstValues = unitAndQuantities.dropLast(2).map { unitAndQuantity ->  "$unitAndQuantity, " }.fold("") { acc, value -> acc + value }
        val lastValue = unitAndQuantities.last()
        val nextToLastValue = unitAndQuantities[unitAndQuantities.size - 2]
        "$firstValues$nextToLastValue and $lastValue"
    }
}
