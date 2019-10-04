data class Shop(val name:String, val postcode:String, val geoLocation: GeoLocation, val distanceFromLastShop:DistanceInMiles = 0.0 ) {

    fun distanceTo(otherShop:Shop): DistanceInMiles =  this.geoLocation.distanceTo(otherShop.geoLocation)

    fun withDistance(distance:DistanceInMiles):Shop = Shop(this.name, this.postcode, this.geoLocation, distance)

    fun canBeReached():Boolean  = if (timeToReachShop()  <= secondsBetween8amAnd6pm) true else false

    fun timeToReachShop():Seconds = oneHourInSeconds * distanceFromLastShop / speedInMPH

    override fun equals(other: Any?): Boolean = other is Shop && this.name == other.name

}
