import org.hashids.Hashids
import kotlin.random.Random

data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position) = Position(x + other.x, y + other.y)
}

typealias MapData = String

enum class MapContent (val symbol:Char) {
    Open(' '),
    Fixture('*');
    companion object{fun from(value:Char) = values().first{it.symbol == value} }
}

fun MapData.contentAt(position:Position):MapContent {
    if (position.x < 0 || position.y < 0) return MapContent.Fixture

    val rows = split("\n")
    if (position.y >= rows.size) return MapContent.Fixture

    val rowChars = rows[position.y].toList()
    if (position.x >= rowChars.size) return MapContent.Fixture

    return MapContent.from(rowChars[position.x])
}

enum class Output(val symbol:String) { Left("L"),Right("R"),Open("O") }

fun MapData.viewAheadAt(position:Position, orientation:Orientation):List<String>{
    var newPosition = position
    var result = mutableListOf<String>()
    while ( contentAt( newPosition + orientation.ahead) != MapContent.Fixture ) {
        newPosition  += orientation.ahead
        var stringForPosition = Output.Open.symbol
        if (contentAt( newPosition + orientation.left) == MapContent.Open) stringForPosition += Output.Left.symbol
        if (contentAt( newPosition + orientation.right) == MapContent.Open) stringForPosition += Output.Right.symbol
        result.add(stringForPosition)
    }
    return result
}

fun MapData.startingPosition() = split("\n")
    .mapIndexed{y, row -> row.toList().mapIndexed{x, char -> Pair(Position(x,y),char) } }
    .flatMap{it}
    .first{it.second == MapContent.Open.symbol}.first

enum class Command { M,L,R }

val moveNorth = Position(0,-1)
val moveSouth = Position(0,1)
val moveWest = Position(-1,0)
val moveEast = Position(1,0)

enum class Orientation (val value:Int, val ahead:Position, val left:Position, val right:Position) {
    East(1, moveEast, moveNorth, moveSouth),
    South(2, moveSouth, moveEast, moveWest),
    West(3, moveWest, moveSouth, moveNorth),
    North(4, moveNorth, moveWest, moveEast);

    companion object{fun from(value:Int) = values().first{it.value == value} }

    fun turnLeft():Orientation = when(this) {
        East -> North
        North -> West
        West -> South
        South -> East
    }

    fun turnRight():Orientation = when(this) {
        East -> South
        South -> West
        West -> North
        North -> East
    }
 }

data class Trolley(val position:Position, val orientation: Orientation, val trolleyId:Int) {
    val positionAhead = position + orientation.ahead

    fun move() = Trolley (positionAhead, orientation, trolleyId)
    fun rotateLeft() = Trolley(position, orientation.turnLeft(), trolleyId)
    fun rotateRight() = Trolley(position, orientation.turnRight(), trolleyId)

    fun toReferenceId(): String = hashids.encode(position.x.toLong() + 1, position.y.toLong() + 1, orientation.value.toLong(), trolleyId.toLong())

    fun viewAhead(mapData:MapData) = mapData.viewAheadAt(position, orientation)

    fun moveOrRotate(command:Command, mapData:MapData) =
        when (command) {
            Command.M ->if (mapData.contentAt(positionAhead) != MapContent.Fixture) move() else this
            Command.L -> rotateLeft()
            Command.R -> rotateRight()
        }

    fun viewAheadAndReferenceId(mapData:MapData) = Pair(viewAhead(mapData),toReferenceId())

    companion object {
        private val hashids = Hashids(SALT_FOR_HASH)
        fun from(hash:String):Trolley {
            val decoded = hashids.decode(hash)
            return Trolley(Position(decoded[0].toInt() - 1,decoded[1].toInt() - 1),Orientation.from(decoded[2].toInt()),decoded[3].toInt())
        }
    }
}

fun moveTrolley(_mapData:MapData):Pair<List<String>,String> {
    val mapData = if (_mapData.isEmpty()) defaultMapData else _mapData
    return Trolley(mapData.startingPosition(),Orientation.East, Random.nextInt(1,999999))
            .viewAheadAndReferenceId(mapData)
}

fun moveTrolley(command:Command, referenceId:String, _mapData:MapData): Pair<List<String>, String>{
    val mapData = if (_mapData.isEmpty()) defaultMapData else _mapData
    return Trolley.from(referenceId)
                  .moveOrRotate(command,mapData)
                  .viewAheadAndReferenceId(mapData)
}

val SALT_FOR_HASH  = "a random string"

val defaultMapData =
        "**************************\n" +
        "*                        *\n" +
        "* ********** *********** *\n" +
        "* ********** *********** *\n" +
        "*                        *\n" +
        "* ********** *********** *\n" +
        "* ********** *********** *\n" +
        "* ********** *********** *\n" +
        "*                        *\n" +
        "**************************\n"