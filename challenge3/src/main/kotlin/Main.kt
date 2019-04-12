fun main(args: Array<String>) {
    println("Hello world")
}

enum class MapTile(val text:String) {
    Person("p"),
    Wall("#"),
    Block("b"),
    Storage("*"),
    PersonOnStorage("P"),
    BlockOnStorage("B"),
    Empty(" ");

    companion object {
        fun fromString(string:String):MapTile {
            values().firstOrNull { it.text == string }?.let{return it}
            return Empty
        }
        fun fromChar(char:Char):MapTile {
            return this.fromString(char.toString())
        }
    }
}

data class Position(val row:Int, val column:Int) {
    override fun hashCode(): Int = column * 10000 + row
    override fun equals(other: Any?): Boolean {
        return other is Position && this.column == other.column && this.row == other.row
    }
    infix operator fun plus(other:Position):Position {
        return Position(this.row + other.row, this.column + other.column)
    }
}

fun List<String>.toMap():MutableMap<Position,MapTile> {
    val mutableMap= mutableMapOf<Position,MapTile>()
    this.forEachIndexed{ row, string ->
        mutableMap.fromStringForRow(row, string)
    }
    return mutableMap
}

fun MutableMap<Position, MapTile>.fromStringForRow(row:Int, string:String) {
    string.forEachIndexed{ column, character ->
        this[Position(row, column)] = MapTile.fromChar(character)
    }
}

fun Map<Position, MapTile>.toListOfString():List<String> {
    return this.keys.map{it.row }.distinct().sorted().map{row -> this.toStringForRow(row)}
}

fun Map<Position, MapTile>.toStringForRow(row:Int):String {
    val listOfPositionMapTile = this.filter { mapTile -> mapTile.key.row == row }.toSortedMap(compareBy { position ->  position.column }).toList()
    return listOfPositionMapTile.fold("") { acc, pair -> acc + pair.second.text}
}

enum class Direction(val move:Position) {
    L (Position(0,-1)),
    R (Position(0,1)),
    U (Position(-1,0)),
    D (Position(1,0))
}

fun processSokobanMove(listOfString:List<String>, direction:String):List<String> {
    val gameMap = listOfString.toMap()
    val directionToMove = Direction.valueOf(direction)
    gameMap.movePerson(directionToMove)
    return gameMap.toListOfString()
}

fun MutableMap<Position, MapTile>.movePerson(direction: Direction) {
    val positionOfPerson = this.positionOfPerson()
    val positionToMoveTo = positionOfPerson + direction.move

    when {
        this.canMoveOnto(positionToMoveTo) -> movePerson(positionToMoveTo, positionOfPerson)
        this.containsBlock(positionToMoveTo) -> tryAndMoveBlock(positionToMoveTo, direction, positionOfPerson)
    }
}

fun Map<Position, MapTile>.positionOfPerson():Position {
    return this.filterValues{it == MapTile.Person || it == MapTile.PersonOnStorage  }.keys.first()
}

fun Map<Position, MapTile>.canMoveOnto(position:Position):Boolean {
    return this[position] == MapTile.Empty || this[position] == MapTile.Storage
}

fun Map<Position, MapTile>.containsBlock(position:Position):Boolean {
    return this[position] == MapTile.Block || this[position] == MapTile.BlockOnStorage
}

private fun MutableMap<Position, MapTile>.tryAndMoveBlock(positionOfBlock:Position, direction: Direction, positionOfPerson: Position) {
    val positionAdjacentToBlock = positionOfBlock + direction.move
    if (this.canMoveOnto(positionAdjacentToBlock)) {
        moveBlock(positionAdjacentToBlock, positionOfBlock)
        movePerson(positionOfBlock, positionOfPerson)
    }
}

fun MutableMap<Position, MapTile>.movePerson(positionToMoveTo: Position, positionToMoveFrom: Position) {
    val mapTileForOldPosition = if (this[positionToMoveFrom] == MapTile.Person) MapTile.Empty else MapTile.Storage
    val mapTileForNewPosition = if (this[positionToMoveTo] == MapTile.Empty) MapTile.Person else MapTile.PersonOnStorage
    this[positionToMoveFrom] = mapTileForOldPosition
    this[positionToMoveTo] = mapTileForNewPosition
}

fun MutableMap<Position, MapTile>.moveBlock(positionToMoveTo: Position, positionToMoveFrom: Position) {
    val mapTileForOldPosition = if (this[positionToMoveFrom] == MapTile.Block) MapTile.Empty else MapTile.Storage
    val mapTileForNewPosition = if (this[positionToMoveTo] == MapTile.Empty) MapTile.Block else MapTile.BlockOnStorage
    this[positionToMoveFrom] = mapTileForOldPosition
    this[positionToMoveTo] = mapTileForNewPosition
}

