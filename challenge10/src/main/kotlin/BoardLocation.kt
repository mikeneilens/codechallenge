class BoardLocation(private val locations: List<Location>) {

    private var locationIndex = 0

    var hasPassedGo = false
        private set

    val currentLocation get() = locations[locationIndex]

    constructor (_locations:List<Location>, _locationIndex:Int):this(_locations) {

        if (_locationIndex > locations.lastIndex) {
            this.locationIndex = _locationIndex % locations.size
            this.hasPassedGo = true
        } else {
            this.locationIndex = _locationIndex
        }

    }

    operator fun plus(dice:Dice):BoardLocation {
        val newLocationIndex = (this.locationIndex + dice.totalValue)
        return BoardLocation(this.locations, newLocationIndex)
    }
}
