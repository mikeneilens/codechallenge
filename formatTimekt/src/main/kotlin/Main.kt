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
val quantitiesInEachUnit = listOf(
      UnitTime("year",31536000)
    , UnitTime("day", 86400)
    , UnitTime("hour",3600)
    , UnitTime("minute", 60)
    , UnitTime("second",1)
    )

fun formatTime(timeInSeconds:Int): String {
    if (timeInSeconds == 0) return "none"


    val listOfUnitTimes = createListOfUnitTimes(timeInSeconds)

    val nonZeroUnitTImes = listOfUnitTimes.filter { unitTime:UnitTime -> unitTime.value > 0 }

    return convertListOfUnitTimes(nonZeroUnitTImes)
}

fun createListOfUnitTimes(timeInSeconds: Int):List<UnitTime> {
    var remainingTime = timeInSeconds

    val calcValueForEachUnit = fun  (unitTime:UnitTime):UnitTime {
        val newValue = remainingTime / unitTime.value
        remainingTime = remainingTime % unitTime.value
        return UnitTime(unitTime.unit, newValue)
    }

    return quantitiesInEachUnit.map(calcValueForEachUnit)
}

fun convertListOfUnitTimes(unitTimes:List<UnitTime>):String  = when (unitTimes.size) {
    1 -> "${unitTimes[0]}"
    2 ->  "${unitTimes[0]} and ${unitTimes[1]}"
    else -> {
        val firstValues = unitTimes.dropLast(2).map { "${it}, " }.fold("") { acc, value -> acc + value }
        val lastValue = unitTimes.last()
        val nextToLastValue = unitTimes[unitTimes.size - 2]
        firstValues + "${nextToLastValue} and ${lastValue}"
    }
}