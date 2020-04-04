import com.sun.org.apache.xalan.internal.lib.ExsltMath.power
import org.gavaghan.geodesy.Ellipsoid
import org.gavaghan.geodesy.GeodeticCalculator
import org.gavaghan.geodesy.GlobalCoordinates

val geoCalc = GeodeticCalculator()
val reference: Ellipsoid = Ellipsoid.WGS84
const val METRES_IN_A_MILE = 1609.34

data class GeoLocation(private val lat:Double, private val lng:Double) {
    fun distanceTo(other:GeoLocation):DistanceInMiles {
        val globalCoordinates1 = GlobalCoordinates(this.lat, this.lng)
        val globalCoordinates2 = GlobalCoordinates(other.lat, other.lng)
        val distanceInMetres = geoCalc.calculateGeodeticCurve(reference, globalCoordinates1, globalCoordinates2).ellipsoidalDistance

        return distanceInMetres/METRES_IN_A_MILE
    }
    fun distanceTo2(other:GeoLocation):DistanceInMiles {
        return power(power((this.lat - other.lat),2.0) + power((this.lng - other.lng),2.0),0.5) * 70

    }
}
