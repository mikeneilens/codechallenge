class Player(val name:String, private var boardLocation:BoardLocation = BoardLocation(locations)) {

    val currentLocation get() = boardLocation.currentLocation
    val hasPassedGo get() = boardLocation.hasPassedGo

    fun move(dice: Dice) {
        boardLocation += dice
    }

}
