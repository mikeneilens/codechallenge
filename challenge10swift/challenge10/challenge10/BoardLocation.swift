
struct BoardLocation {
    
    private let locationIndex:Int
    private let locations:Array<Location>
    
    let hasPassedGo:Bool
    var currentLocation:Location { return locations[locationIndex] }
    
    init(locations _locations:Array<Location>) {
        self.locations = _locations
        self.locationIndex = 0
        self.hasPassedGo = false
    }
    
    init(locations _locations:Array<Location>, locationIndex _locationIndex:Int) {
        self.locations = _locations
        if _locationIndex > (locations.endIndex - 1 ) {
            self.locationIndex = _locationIndex - locations.endIndex
            self.hasPassedGo = true
        } else {
            self.locationIndex = _locationIndex
            self.hasPassedGo = false
        }
    }
    
    func move(using dice:Dice) -> BoardLocation {
        let newLocationIndex = (locationIndex + dice.total)
        return BoardLocation(locations:locations, locationIndex:newLocationIndex)
    }
}
