import kotlin.math.min

class TimeAccumulator {
    private var wholeDaysTravelled = 0
    private var timeRemainingInLatestDay = secondsBetween8amAnd6pm
    private var timeUsedInLatestDay = 0.0

    val totalTime get() = wholeDaysTravelled * oneDayInSeconds + timeUsedInLatestDay

    fun updateUsing(shop:Shop) {

        if (shop.timeToReachShop() > timeRemainingInLatestDay) { //need to wait until tomorrow before travelling
            moveToNextDay()
        }

        addTravellingTime(shop.timeToReachShop())
        addTimeSpentAtShop()
    }

    private fun addTravellingTime(timeToNextShop:Double) {
        timeUsedInLatestDay += timeToNextShop
        timeRemainingInLatestDay -= timeToNextShop
    }

    private fun addTimeSpentAtShop() {
        timeUsedInLatestDay += minTimeSpentAtEachShop
        timeRemainingInLatestDay -= minTimeSpentAtEachShop
    }

    private fun moveToNextDay() {
        wholeDaysTravelled += 1
        timeUsedInLatestDay = 0.0
        timeRemainingInLatestDay = secondsBetween8amAnd6pm
    }

}