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
        val timeSpentAtShop = calcTimeSpentAtShop()
        addTimeSpentAtShop(timeSpentAtShop)
    }

    private fun addTravellingTime(timeToNextShop:Double) {
        timeUsedInLatestDay += timeToNextShop
        timeRemainingInLatestDay -= timeToNextShop
    }

    private fun addTimeSpentAtShop(timeSpentAtShop:Double) {
        timeUsedInLatestDay += timeSpentAtShop
        timeRemainingInLatestDay -= timeSpentAtShop
    }

    private fun moveToNextDay() {
        wholeDaysTravelled += 1
        timeUsedInLatestDay = 0.0
        timeRemainingInLatestDay = secondsBetween8amAnd6pm
    }

    private fun calcTimeSpentAtShop() =
        if (timeRemainingInLatestDay > minTimeSpentAtEachShop) minTimeSpentAtEachShop else timeRemainingInLatestDay
}