class BoardLocation(locations:List<Location>) {
    val locations:List<Location>
    private var locationIndex = 0
    var hasPassedGo = false

    fun currentLocation():Location = this.locations[locationIndex]

    init {
        this.locations = locations
    }

    constructor (locations:List<Location>, locationIndex:Int):this(locations) {
        this.locationIndex = locationIndex % locations.size
        this.hasPassedGo = (this.locationIndex != locationIndex)
    }

    operator fun plus(other:Dice):BoardLocation {
        val newLocationIndex = (this.locationIndex + other.totalValue)
        return BoardLocation(this.locations, newLocationIndex)
    }
}

