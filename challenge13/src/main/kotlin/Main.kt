import org.gavaghan.geodesy.Ellipsoid
import org.gavaghan.geodesy.GeodeticCalculator
import org.gavaghan.geodesy.GlobalCoordinates

typealias DistanceInMiles = Double


val geoCalc = GeodeticCalculator()
val reference = Ellipsoid.WGS84
val METRES_IN_A_MILE = 1609.34

class GeoLocation(val lat:Double, val lng:Double) {
    fun distanceTo(other:GeoLocation):DistanceInMiles {
        val globalCoordinates1 = GlobalCoordinates(this.lat, this.lng)
        val globalCoordinates2 = GlobalCoordinates(other.lat, other.lng)
        val distanceInMetres = geoCalc.calculateGeodeticCurve(reference, globalCoordinates1, globalCoordinates2).ellipsoidalDistance

        return distanceInMetres/METRES_IN_A_MILE
    }
}

data class Shop(val name:String, val postcode:String, val geoLocation: GeoLocation, val distanceFromLastShop:DistanceInMiles = 0.0 ) {

    fun distanceTo(otherShop:Shop): DistanceInMiles =  this.geoLocation.distanceTo(otherShop.geoLocation)

    fun withDistance(distance:DistanceInMiles):Shop = Shop(this.name, this.postcode, this.geoLocation, distance)

    override fun equals(other: Any?): Boolean {
        return other is Shop && this.name == other.name
    }
}

fun orderShops(shops:List<Shop>):List<Shop> {

    if (shops.size <= 1 )
        return shops

    val firstShop = shops[0]
    if (shops.size == 2) {
        val distanceToFirstShop = shops[1].distanceTo(firstShop)
        val secondShop = shops[1].withDistance(distanceToFirstShop)
        return listOf(firstShop, secondShop)
    }

    fun findClosestShop(shop:Shop, shops:List<Shop>, newListOfShops:List<Shop>):Shop? {
        var shortestDistance = 9999.99
        var closestShop:Shop? = null
        for (potentialShop in shops) {
            if (!newListOfShops.contains(potentialShop)) {
                val distanceToPotentialShop = shop.distanceTo(potentialShop)
                if (distanceToPotentialShop < shortestDistance) {
                    shortestDistance = distanceToPotentialShop
                    closestShop = potentialShop.withDistance(distanceToPotentialShop)
                }
            }
        }
        return closestShop
    }

    val newListOfShops = mutableListOf<Shop>(firstShop)
    val secondShop = findClosestShop(firstShop, shops, newListOfShops)
    secondShop?.let{newListOfShops.add(secondShop)
        val thirdShop = findClosestShop(secondShop, shops, newListOfShops)
        thirdShop?.let{newListOfShops.add(it)}
    }

    return newListOfShops
}

