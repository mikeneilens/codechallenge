import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

val paschalFullMoonDates = listOf(
    Pair(4,14),
    Pair(4,3),
    Pair(3,23),
    Pair(4,11),
    Pair(3,31),
    Pair(4,18),
    Pair(4,8),
    Pair(3,28),
    Pair(4,16),
    Pair(4,5),
    Pair(3,25),
    Pair(4,13),
    Pair(4,2),
    Pair(3,22),
    Pair(4,10),
    Pair(3,30),
    Pair(4,17),
    Pair(4,7),
    Pair(3,27)
)

fun List<Pair<Int,Int>>.dateWith(year:Int) = LocalDate(year = year, monthNumber = get(year % 19).first, dayOfMonth =  get(year % 19).second)