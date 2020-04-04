
class TimeAccumulator(private var wholeDaysTravelled:Int = 0, private var timeUsedInLatestDay:Seconds = 0.0) {

    val totalTime:Seconds get() = wholeDaysTravelled * oneDayInSeconds + timeUsedInLatestDay

    operator fun plus(travellingTime: Double):TimeAccumulator {
        return if (travellingTime > (secondsBetween8amAnd6pm - timeUsedInLatestDay)) { //need to wait until tomorrow before travelling
            TimeAccumulator(wholeDaysTravelled + 1, travellingTime + minTimeSpentAtEachShop)
        } else {
            TimeAccumulator(wholeDaysTravelled, timeUsedInLatestDay + travellingTime + minTimeSpentAtEachShop)
        }
    }
}