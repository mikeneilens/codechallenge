class BoardLocation(private val locations: List<Location>) {

    private var locationIndex = 0

    var hasPassedGo = false
        private set

    val currentLocation get() = this.locations[locationIndex]

    constructor (locations:List<Location>, locationIndex:Int):this(locations) {
        this.locationIndex = locationIndex % locations.size
        this.hasPassedGo = (this.locationIndex != locationIndex)
    }

    operator fun plus(other:Dice):BoardLocation {
        val newLocationIndex = (this.locationIndex + other.totalValue)
        return BoardLocation(this.locations, newLocationIndex)
    }
}

