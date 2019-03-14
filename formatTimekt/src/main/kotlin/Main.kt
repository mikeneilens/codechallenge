package mike
fun main(args: Array<String>) {
    println(formatTime(0))
    println(formatTime(1))
    println(formatTime(60))
    println(formatTime(3600))
    println(formatTime(86400))
    println(formatTime(3156000))
}
fun formatTime(timeInSeconds:Int)  = when (timeInSeconds) {
    0 -> "none"
    1,2 -> UnitTime("second", timeInSeconds).toString()
    60,120 -> UnitTime("minute", timeInSeconds/60).toString()
    3600,7200 -> UnitTime("hour", timeInSeconds/3600).toString()
    86400,172800 -> UnitTime("day", timeInSeconds/86400).toString()
    31536000,63072000 -> UnitTime("year",timeInSeconds/31536000).toString()
    else -> "none"
}