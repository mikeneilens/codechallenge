
class TimeAccumulator {
    private var wholeDaysTravelled = 0
    private var timeRemainingInLatestDay:Seconds = secondsBetween8amAnd6pm
    private var timeUsedInLatestDay:Seconds = 0.0

    val totalTime:Seconds get() = wholeDaysTravelled * oneDayInSeconds + timeUsedInLatestDay

    fun add(travellingTime:Double) {

        if (travellingTime > timeRemainingInLatestDay) { //need to wait until tomorrow before travelling
            moveToNextDay()
        }

        addTime(travellingTime)
        addTime(minTimeSpentAtEachShop)
    }

    private fun addTime(time:Double) {
        timeUsedInLatestDay += time
        timeRemainingInLatestDay -= time
    }

    private fun moveToNextDay() {
        wholeDaysTravelled += 1
        timeUsedInLatestDay = 0.0
        timeRemainingInLatestDay = secondsBetween8amAnd6pm
    }

}