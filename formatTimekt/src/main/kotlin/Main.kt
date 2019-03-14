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
    1 -> "1 second"
    60 -> "1 minute"
    3600 -> "1 hour"
    86400 -> "1 day"
    31536000 -> "1 year"
    else -> "none"
}