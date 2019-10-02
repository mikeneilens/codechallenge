import kotlin.math.roundToInt

typealias DistanceInMiles = Double

fun String.toShops():List<Shop> = this.split(",").windowed(4,4).mapNotNull{it.convertToShop()}

fun List<String>.convertToShop():Shop? =
    if ((this.size != 4) ||(this[2].toDoubleOrNull() == null) ||(this[3].toDoubleOrNull() == null) )
        null
    else
        Shop(this[0], this[1], GeoLocation(this[2].toDouble(), this[3].toDouble()))

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

const val secondsBetween8amAnd6pm = 10 * 3600
const val secondsBetween6pmAnd8am = 14 * 3600
const val speedInMPH = 30

fun calculateJourneyTime(shopsData: String, dailyTimeAllowance:Int = secondsBetween8amAnd6pm, nonTravellingTime:Int = secondsBetween6pmAnd8am): Int {
    val unsortedShops = shopsData.toShops()
    if (unsortedShops.size <= 1) return 0
    val sortedShops = unsortedShops.createRoute()

    return sortedShops.calculateJourneyTime(dailyTimeAllowance, nonTravellingTime)
}

fun List<Shop>.calculateJourneyTime(dailyTimeAllowance:Int = secondsBetween8amAnd6pm, nonTravellingTime:Int = secondsBetween6pmAnd8am):Int {
    var timeLeftToday = dailyTimeAllowance
    var travellingTime = 0

    for (nextShop in this.drop(1)) {
        val timeToNextShop = (3600 * nextShop.distanceFromLastShop / speedInMPH).roundToInt()

        if (timeToNextShop > dailyTimeAllowance) {
            return travellingTime
        }

        if (timeToNextShop > timeLeftToday) {
            travellingTime += timeToNextShop + nonTravellingTime
            timeLeftToday = dailyTimeAllowance
        } else {
            travellingTime += timeToNextShop
        }
        timeLeftToday -= timeToNextShop

    }

    return travellingTime
}
