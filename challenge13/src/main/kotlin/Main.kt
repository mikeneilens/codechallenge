import org.gavaghan.geodesy.Ellipsoid
import org.gavaghan.geodesy.GeodeticCalculator
import org.gavaghan.geodesy.GlobalCoordinates

class Main {
}

class GeoLocation(val lat:Double, val lng:Double)

class shop(val name:String, val postcode:String, geoLocation: GeoLocation )

val geoCalc = GeodeticCalculator()
val reference = Ellipsoid.WGS84
val METRES_IN_A_MILE = 1609.34

fun GeoLocation.distanceTo(other:GeoLocation):Double {
    val globalCoordinates1 = GlobalCoordinates(this.lat, this.lng)
    val globalCoordinates2 = GlobalCoordinates(other.lat, other.lng)
    val distanceInMetres = geoCalc.calculateGeodeticCurve(reference, globalCoordinates1, globalCoordinates2).ellipsoidalDistance

    return distanceInMetres/METRES_IN_A_MILE
}