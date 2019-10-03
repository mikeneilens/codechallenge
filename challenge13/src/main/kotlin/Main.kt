
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

fun calculateJourneyTime(shopsData: String): Int {
    val unsortedShops = shopsData.toShops()
    if (unsortedShops.size <= 1) return 0
    val sortedShops = unsortedShops.createRoute()
    return sortedShops.calculateJourneyTime().toInt()
}

fun List<Shop>.calculateJourneyTime():Double {

    val timeAccumulator = TimeAccumulator()

    for (shop in this.drop(1)) {
        if (shop.canNeverBeReached()) return timeAccumulator.totalTime
        else timeAccumulator.updateUsing(shop)

        println("$shop time so far: ${timeAccumulator.totalTime}")
    }

    return timeAccumulator.totalTime

}

