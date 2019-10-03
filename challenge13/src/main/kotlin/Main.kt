import kotlin.math.roundToInt

typealias DistanceInMiles = Double

fun String.toShops():List<Shop> = this.split(",").windowed(4,4).mapNotNull{it.convertToShop()}

fun List<String>.convertToShop():Shop? =
    if ((this.size != 4) ||(this[2].toDoubleOrNull() == null) ||(this[3].toDoubleOrNull() == null) )
        null
    else
        Shop(this[0], this[1], GeoLocation(this[3].toDouble(), this[2].toDouble()))

fun List<Shop>.createRoute():List<Shop> {

    if (this.size <= 1 ) return this

    val newListOfShops = mutableListOf(this.first())
    var closestShop = findClosestShop(this, newListOfShops)

    while (closestShop != null) {
        newListOfShops.add(closestShop)
        closestShop = findClosestShop(this, newListOfShops)
    }

    return newListOfShops
}

fun findClosestShop(allShops:List<Shop>, newListOfShops:List<Shop>):Shop? {
    val shop = newListOfShops.lastOrNull() ?: return null
    val nullShop:Shop? = null

    return allShops.fold(nullShop){ closestShop:Shop?, nextShop:Shop ->
        if (newListOfShops.contains(nextShop)) closestShop
        else {
            if ((closestShop == null)||( shop.distanceTo(nextShop) < closestShop.distanceFromLastShop)) {
                nextShop.withDistance(shop.distanceTo(nextShop))
            } else closestShop
        }
    }
}

const val secondsBetween8amAnd6pm = 10.0 * 3600.0
const val minTimeSpentAtEachShop = 20.0 * 60.0
const val speedInMPH = 30.0

fun calculateJourneyTime(shopsData: String): Int {
    val unsortedShops = shopsData.toShops()
    if (unsortedShops.size <= 1) return 0
    val sortedShops = unsortedShops.createRoute()

    return sortedShops.calculateJourneyTime().toInt()
}

class TimeAccumulator {
    var wholeDaysTravelled = 0
    var timeRemainingInLatestDay = secondsBetween8amAnd6pm
    var timeUsedInLatestDay = 0.0

    val totalTime get() = wholeDaysTravelled * 3600.0 * 24.0 + timeUsedInLatestDay

    fun updateUsing(nextShop:Shop) {

        if (nextShop.timeToReachShop() > timeRemainingInLatestDay) { //need to wait until tomorrow before travelling
            moveToNextDay()
        }

        addTravellingTime(nextShop.timeToReachShop())
        val timeSpentAtShop = calcTimeSpentAtShop()
        addTimeSpenAtShop(timeSpentAtShop)
    }

    private fun addTravellingTime(timeToNextShop:Double) {
        timeUsedInLatestDay += timeToNextShop
        timeRemainingInLatestDay -= timeToNextShop
    }

    private fun addTimeSpenAtShop(timeSpentAtShop:Double) {
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

fun List<Shop>.calculateJourneyTime():Double {

    val timeAccumulator = TimeAccumulator()

    for (nextShop in this.drop(1)) {
        if (nextShop.canNeverBeReached()) return timeAccumulator.totalTime
        else timeAccumulator.updateUsing(nextShop)
    }

    return timeAccumulator.totalTime

}

