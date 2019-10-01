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

data class Shop(val name:String, val postcode:String, val geoLocation: GeoLocation, val distanceFromLastShop:DistanceInMiles ) {
    fun distanceTo(otherShop:Shop): DistanceInMiles =  this.geoLocation.distanceTo(otherShop.geoLocation)
    override fun equals(other: Any?): Boolean {
        return other is Shop && this.name == other.name
    }
}

fun orderShops(shops:List<Shop>):List<Shop> {

    if (shops.size <= 1 )
        return shops

    val firstShop = shops[0]
    val secondShop = Shop(shops[1].name, shops[1].postcode, shops[1].geoLocation,shops[1].distanceTo(firstShop))
    if (shops.size == 2) {
        return listOf(firstShop, secondShop)
    }
    val thirdShop = Shop(shops[2].name, shops[2].postcode, shops[2].geoLocation,shops[2].distanceTo(secondShop))
    return listOf(firstShop, secondShop, thirdShop)
}

