import kotlin.math.abs

typealias Time = String
typealias DayOfWeek = String
typealias DaysOfWeek = List<DayOfWeek>

val allDays = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")

class OpeningTime(val daysOfWeek:DaysOfWeek, val opens:Time, val closes:Time)

val Time.hours get() = substringBefore(":").toInt()

val Time.minutes get() = substringAfter(":").toInt()

val Time.minutesText get() = when (true) {
    (minutes == 0) -> ""
    (minutes < 10) -> ":0${minutes}"
    else -> ":${minutes}"
}
val Time.hoursText get() = when(true) {
    (hours == 0) -> "12"
    (hours > 12 ) -> "${hours - 12}"
    else -> "$hours"
}

val Time.meridian get() = if (hours < 12) "am" else "pm"

fun Time.isMidnight() = (this == "00:00")

val Time.compact get() = hoursText + minutesText + meridian

val DayOfWeek.short get() = substring(0..2)

infix fun DayOfWeek.isConsecutiveTo (other:DayOfWeek)= (abs(  allDays.indexOf(this.short) - allDays.indexOf(other.short)) == 1)
