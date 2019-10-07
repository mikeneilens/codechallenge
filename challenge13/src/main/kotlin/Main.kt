
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

    val route = mutableListOf(this.first())
    var closestShop = findClosestShopToEndOfRouteOrNull(this, route)

    while (closestShop != null) {
        route.add(closestShop)
        closestShop = findClosestShopToEndOfRouteOrNull(this, route)
    }

    return route
}

fun findClosestShopToEndOfRouteOrNull(allShops:List<Shop>, route:List<Shop>):Shop? {
    val shopAtEndOfRoute = route.lastOrNull() ?: return null
    val nullShop:Shop? = null

    return allShops.fold(nullShop){ closestShopSoFar:Shop?, candidateShop:Shop ->
        if (route.contains(candidateShop)) closestShopSoFar
        else {
            val distanceToCandidateShop = shopAtEndOfRoute.distanceTo(candidateShop)
            if ((closestShopSoFar == null)||( distanceToCandidateShop < closestShopSoFar.distanceFromLastShop)) {
                candidateShop.withDistance(distanceToCandidateShop)
            } else closestShopSoFar
        }
    }
}

fun List<Shop>.calculateJourneyTime():Seconds {

    val shopsThatCanBeReached = this.takeWhile { shop -> shop.canBeReached() }
    return shopsThatCanBeReached.drop(1).fold(TimeAccumulator()){ timeAccumulator, shop ->
        println("${shop.name} time to here: ${formatTime((timeAccumulator + shop.timeToReachShop()).totalTime.toInt())} distance to previous ${shop.distanceFromLastShop} ")
        timeAccumulator + shop.timeToReachShop()}.totalTime

}

