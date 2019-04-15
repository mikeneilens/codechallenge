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

    val onStorage:MapTile by lazy {
        when (this) {
            Person -> PersonOnStorage
            Block -> BlockOnStorage
            else -> this
        }
    }
    val notOnStorage:MapTile by lazy {
        when (this) {
            PersonOnStorage -> Person
            BlockOnStorage -> Block
            else -> this
        }
    }

    companion object {
        fun fromString(string:String):MapTile {
            values().firstOrNull { it.text == string }?.let{return it}
            return Empty
        }

        fun fromChar(char:Char):MapTile = MapTile.fromString(char.toString())
    }
}

data class Position(val row:Int, val column:Int) {
    override fun hashCode(): Int = column * 10000 + row

    override fun equals(other: Any?): Boolean =  other is Position && this.column == other.column && this.row == other.row

    infix operator fun plus(other:Position):Position = Position(this.row + other.row, this.column + other.column)

}

typealias GameMap = MutableMap<Position, MapTile>
fun newGameMap():GameMap = mutableMapOf()

fun List<String>.toGameMap():GameMap {
    val gameMap= newGameMap()
    this.forEachIndexed{ row, string ->
        gameMap.fromStringForRow(row, string)
    }
    return gameMap
}

fun GameMap.fromStringForRow(row:Int, string:String) {
    string.forEachIndexed{ column, character ->
        this[Position(row, column)] = MapTile.fromChar(character)
    }
}

fun GameMap.toListOfString():List<String> {
    return this.keys
            .map(Position::row)
            .distinct().sorted()
            .map{row -> this.toStringForRow(row)}
}

fun GameMap.toStringForRow(row:Int):String {
    val listOfTextForEachPosition = this.filter { mapTile -> mapTile.key.row == row }
        .toSortedMap(compareBy { position ->  position.column })
        .toList()
        .map{it.second.text}
    return listOfTextForEachPosition.reduce { acc, text -> acc + text}
}

enum class Direction(val move:Position) {
    L (Position(0,-1)),
    R (Position(0,1)),
    U (Position(-1,0)),
    D (Position(1,0))
}

fun processSokobanMove(listOfString:List<String>, direction:String):List<String> {
    val gameMap = listOfString.toGameMap()
    val directionToMove = Direction.valueOf(direction)
    gameMap.moveMapTile(directionToMove)
    return gameMap.toListOfString()
}

fun GameMap.moveMapTile(direction: Direction) {
    val positionOfPerson = this.positionOfPerson()
    val positionToMoveTo = positionOfPerson + direction.move

    when {
        this.canMoveOnto(positionToMoveTo) -> moveMapTile(positionOfPerson, positionToMoveTo)
        this.containsBlock(positionToMoveTo) -> tryAndMoveBlock(positionToMoveTo, direction, positionOfPerson)
    }
}

fun GameMap.positionOfPerson():Position {
    return this.filterValues{it == MapTile.Person || it == MapTile.PersonOnStorage  }.keys.first()
}

fun GameMap.canMoveOnto(position:Position):Boolean = this[position] == MapTile.Empty || this[position] == MapTile.Storage

fun GameMap.containsBlock(position:Position):Boolean = this[position] == MapTile.Block || this[position] == MapTile.BlockOnStorage

fun GameMap.tryAndMoveBlock(positionOfBlock:Position, direction: Direction, positionOfPerson: Position) {
    val positionAdjacentToBlock = positionOfBlock + direction.move
    if (this.canMoveOnto(positionAdjacentToBlock)) {
        moveMapTile(positionOfBlock, positionAdjacentToBlock)
        moveMapTile(positionOfPerson, positionOfBlock)
    }
}

fun GameMap.moveMapTile(positionToMoveFrom: Position, positionToMoveTo: Position) {
    this[positionToMoveFrom]?.let {mapTile ->
        val mapTileForOldPosition = if (this[positionToMoveFrom] == mapTile.notOnStorage) MapTile.Empty else MapTile.Storage
        val mapTileForNewPosition = if (this[positionToMoveTo] == MapTile.Empty) mapTile.notOnStorage else mapTile.onStorage
        this[positionToMoveFrom] = mapTileForOldPosition
        this[positionToMoveTo] = mapTileForNewPosition
    }
}

