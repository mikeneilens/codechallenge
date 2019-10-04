
typealias DistanceInMiles = Double

fun calculateJourneyTime(shopsData: String): Int {
    val unsortedShops = shopsData.toShops()
    val route = unsortedShops.createRoute()
    return route.calculateJourneyTime().toInt()
}

fun String.toShops():List<Shop> = this.split(",").windowed(4,4).mapNotNull{it.convertToShopOrNull()}

fun List<String>.convertToShopOrNull() =
    if ((this.size != 4) ||(this[2].toDoubleOrNull() == null) ||(this[3].toDoubleOrNull() == null) )
        null
    else
        Shop(this[0], this[1], GeoLocation(this[3].toDouble(), this[2].toDouble()))

fun List<Shop>.createRoute():List<Shop> {

    if (this.size <= 1 ) return this

    val newListOfShops = mutableListOf(this.first())
    var closestShop = findClosestShopOrNull(this, newListOfShops)

    while (closestShop != null) {
        newListOfShops.add(closestShop)
        closestShop = findClosestShopOrNull(this, newListOfShops)
    }

    return newListOfShops
}

fun findClosestShopOrNull(allShops:List<Shop>, newListOfShops:List<Shop>):Shop? {
    val shop = newListOfShops.lastOrNull() ?: return null
    val closestShop:Shop? = null

    return allShops.fold(closestShop){ closestShop:Shop?, nextShop:Shop ->
        if (newListOfShops.contains(nextShop)) closestShop
        else {
            if ((closestShop == null)||( shop.distanceTo(nextShop) < closestShop.distanceFromLastShop)) {
                nextShop.withDistance(shop.distanceTo(nextShop))
            } else closestShop
        }
    }
}

fun List<Shop>.calculateJourneyTime():Seconds {

    val shopsThatCanBeReached = this.takeWhile { shop -> shop.canBeReached() }
    return shopsThatCanBeReached.drop(1).fold(TimeAccumulator()){timeAccumulator, shop -> timeAccumulator + shop.timeToReachShop()}.totalTime

}

