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

    if (shops.size <= 1 ) return shops

    val newListOfShops = mutableListOf(shops.first())
    var closestShop = findClosestShop(shops, newListOfShops)

    while (closestShop != null) {
        newListOfShops.add(closestShop)
        closestShop = findClosestShop(shops, newListOfShops)
    }

    return newListOfShops
}

fun findClosestShop(allShops:List<Shop>, newListOfShops:List<Shop>):Shop? {
    val shop = newListOfShops.lastOrNull()
    if (shop == null) return null
    val nullShop:Shop? = null

    val closestShop:Shop? = allShops.fold(nullShop){closestShop:Shop?, nextShop:Shop ->
        if (newListOfShops.contains(nextShop)) closestShop
        else {
            if ((closestShop == null)||( shop.distanceTo(nextShop) < closestShop.distanceFromLastShop)) {
                nextShop.withDistance(shop.distanceTo(nextShop))
            } else closestShop
        }
    }

    return closestShop
}
