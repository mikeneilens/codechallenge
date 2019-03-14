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

enum class Unit(val secondsPerUnit: Int) {
    second(1),
    minute(60),
    hour(3600),
    day(86400),
    year(31536000)
}

class UnitTime(private val unit:Unit, val value:Int) {
    override fun toString(): String {
        return  if (value == 1) "$value $unit" else "$value ${unit}s"
    }
}

val units = listOf(Unit.year, Unit.day, Unit.hour, Unit.minute, Unit.second)

fun formatTime(timeInSeconds:Int): String {
    if (timeInSeconds == 0) return "none"

    val listOfUnitTimes = createListOfUnitTimes(timeInSeconds)

    val nonZeroUnitTimes = listOfUnitTimes.filter { unitTime:UnitTime -> unitTime.value > 0 }

    return convertListOfUnitTimesToString(nonZeroUnitTimes)
}

fun createListOfUnitTimes(timeInSeconds: Int):List<UnitTime> {
    var remainingTime = timeInSeconds

    val calcValueForEachUnit = fun  (unit:Unit):UnitTime {
        val newValue = remainingTime / unit.secondsPerUnit
        remainingTime = remainingTime % unit.secondsPerUnit
        return UnitTime(unit, newValue)
    }

    return units.map(calcValueForEachUnit)
}

fun convertListOfUnitTimesToString(unitTimes:List<UnitTime>)  = when (unitTimes.size) {
    1 -> "${unitTimes[0]}"
    2 ->  "${unitTimes[0]} and ${unitTimes[1]}"
    else -> {
        val firstValues = unitTimes.dropLast(2).map {unitTime ->  "$unitTime, " }.fold("") { acc, value -> acc + value }
        val lastValue = unitTimes.last()
        val nextToLastValue = unitTimes[unitTimes.size - 2]
        firstValues + "$nextToLastValue and $lastValue"
    }
}
