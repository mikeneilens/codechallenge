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

enum class Unit(val secondsPerUnit: Int, val maxSize:Int) {
    year(31536000, 100),
    day(86400, 365),
    hour(3600, 24),
    minute(60, 60),
    second(1, 60);

    fun toUnitTime(timeInSeconds: Int):UnitTime {
        val value = timeInSeconds / this.secondsPerUnit % this.maxSize
        return UnitTime(this, value)
    }
}

class UnitTime(private val unit:Unit, val value:Int) {
    override fun toString(): String = if (value == 1) "$value $unit" else "$value ${unit}s"
}

fun formatTime(timeInSeconds:Int): String {
    if (timeInSeconds == 0) return "none"

    val listOfUnitTimes = createListOfUnitTimes(timeInSeconds)
    val nonZeroUnitTimes = listOfUnitTimes.filter { unitTime:UnitTime -> unitTime.value > 0 }

    return convertListOfUnitTimesToString(nonZeroUnitTimes)
}


fun createListOfUnitTimes(timeInSeconds: Int):List<UnitTime> {
    return Unit.values().map{unit -> unit.toUnitTime(timeInSeconds)}
}

fun convertListOfUnitTimesToString(unitTimes:List<UnitTime>)  = when (unitTimes.size) {
    1 -> "${unitTimes[0]}"
    2 ->  "${unitTimes[0]} and ${unitTimes[1]}"
    else -> {
        val firstValues = unitTimes.dropLast(2).map {unitTime ->  "$unitTime, " }.fold("") { acc, value -> acc + value }
        val lastValue = unitTimes.last()
        val nextToLastValue = unitTimes[unitTimes.size - 2]
        "$firstValues$nextToLastValue and $lastValue"
    }
}
