package mike
fun main(args: Array<String>) {
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

    val trailingValue = nonZeroUnitTImes.last()
    val leadingValue = nonZeroUnitTImes.dropLast(1).lastOrNull()

    return if (leadingValue != null) "${leadingValue} and ${trailingValue}" else "${trailingValue}"
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