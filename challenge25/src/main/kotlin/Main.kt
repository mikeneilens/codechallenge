
fun DaysOfWeek.concatenateDays() =
    map{it.short}.concatonateShortDays()

fun DaysOfWeek.concatonateShortDays() =
    drop(1)
    .fold(Pair(first(),first())){(result,prevDayOfWeek), dayOfWeek ->
        val newResult = if (dayOfWeek isConsecutiveTo prevDayOfWeek) {
            result.removeSuffix("-$prevDayOfWeek") + "-$dayOfWeek"
        } else {
            "$result,$dayOfWeek"
        }
        Pair( newResult, dayOfWeek)
}.first

typealias  OpeningTimes = List<OpeningTime>

fun OpeningTimes.concatenate() =
    joinToString(" | ") {
        if (it.opens.isMidnight() && it.closes.isMidnight())
            it.daysOfWeek.concatenateDays() + ": Closed"
        else
            it.daysOfWeek.concatenateDays() + ": " + it.opens.compact + "-" + it.closes.compact
    }