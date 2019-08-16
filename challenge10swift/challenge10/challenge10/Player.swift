
struct Player {
    let name:String
    private var boardLocation:BoardLocation = BoardLocation(locations:locations)
    
    var currentLocation:Location {return boardLocation.currentLocation}
    var hasPassedGo:Bool {return boardLocation.hasPassedGo}
    
    init(_ _name:String) {
        name = _name
    }
    
    mutating func move(using dice: Dice) {
        boardLocation = boardLocation.move(using: dice)
    }
    
}
