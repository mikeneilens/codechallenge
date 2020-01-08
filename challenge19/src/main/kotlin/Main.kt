import org.hashids.Hashids

data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position) = Position(x + other.x, y + other.y)
}

enum class Command { M,L,R }

enum class Output(val symbol:String) { Left("L"),Right("R"),Open("O") }

val moveNorth = Position(0,-1)
val moveSouth = Position(0,1)
val moveWest = Position(-1,0)
val moveEast = Position(1,0)

enum class Orientation (val value:Int, val ahead:Position, val left:Position, val right:Position) {
    East(1, moveEast, moveNorth, moveSouth),
    South(2, moveSouth, moveEast, moveWest),
    West(3, moveWest, moveSouth, moveNorth),
    North(4, moveNorth, moveWest, moveEast);

    val moveNorth = Position(0,-1)
    val moveSouth = Position(0,1)
    val moveWest = Position(-1,0)
    val moveEast = Position(1,0)

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
    val positionToLeft = position + orientation.left
    val positionToRight = position + orientation.right

    fun move() = Trolley (positionAhead, orientation, trolleyId)
    fun rotateLeft() = Trolley(position, orientation.turnLeft(), trolleyId)
    fun rotateRight() = Trolley(position, orientation.turnRight(), trolleyId)

    fun toReferenceId(): String =   hashids.encode(position.x.toLong() + 1, position.y.toLong() + 1, orientation.value.toLong(), trolleyId.toLong())

    fun viewAhead(mapData:String):List<String>{
        var movedTrolley = this
        var result = mutableListOf<String>()
        while ( mapData.contentAt( movedTrolley.positionAhead) != Content.Fixture ) {
            movedTrolley  = movedTrolley.move()
            var stringForPosition = Output.Open.symbol
            if (mapData.contentAt( movedTrolley.positionToLeft) == Content.Open) stringForPosition += Output.Left.symbol
            if (mapData.contentAt( movedTrolley.positionToRight) == Content.Open) stringForPosition += Output.Right.symbol
            result.add(stringForPosition)
        }
        return result
    }

    fun moveOrRotate(command:Command, mapData:String) =
        when (command) {
            Command.M ->if (mapData.contentAt(positionAhead) != Content.Fixture) move() else this
            Command.L -> rotateLeft()
            Command.R -> rotateRight()
        }

    companion object {
        private val hashids = Hashids(SALT_FOR_HASH)
        fun from(hash:String):Trolley {
            val decoded = hashids.decode(hash)
            return Trolley(Position(decoded[0].toInt() - 1,decoded[1].toInt() - 1),Orientation.from(decoded[2].toInt()),decoded[3].toInt())
        }
    }
}

enum class Content (val symbol:Char) {
    Open(' '),
    Fixture('*');
    companion object{fun from(value:Char) = values().first{it.symbol == value} }
}

fun String.contentAt(position:Position):Content {
    if (position.x < 0 || position.y < 0) return Content.Fixture

    val rows = split("\n")
    if (position.y >= rows.size) return Content.Fixture

    val rowChars = rows[position.y].toList()
    if (position.x >= rowChars.size) return Content.Fixture

    return Content.from(rowChars[position.x])
}

fun moveTrolley(command:Command, referenceId:String, _mapData:String): Pair<List<String>, String>{
    val mapData = if (_mapData.isEmpty()) defaultMapData else _mapData

    val trolley = Trolley.from(referenceId)
    val newTrolley = trolley.moveOrRotate(command,mapData)
    val viewAhead = newTrolley.viewAhead(mapData)
    val newReferenceId = newTrolley.toReferenceId()
    return Pair(viewAhead, newReferenceId )
}


fun moveTrolley(_mapData:String):Pair<List<String>,String> {
    val mapData = if (_mapData.isEmpty()) defaultMapData else _mapData
    val startingPosition = mapData.startingPosition()
    val orientation = Orientation.East
    val trolleyId = 1234
    val trolley = Trolley(startingPosition,orientation, trolleyId)
    val viewAhead = trolley.viewAhead(mapData)
    val referenceId = trolley.toReferenceId()
    return Pair(viewAhead, referenceId)
}

fun String.startingPosition() = split("\n")
                                        .mapIndexed{y, row -> row.toList().mapIndexed{x, char -> Pair(Position(x,y),char) } }
                                        .flatMap{it}
                                        .first{it.second == Content.Open.symbol}.first


val SALT_FOR_HASH  = "a random string"

val defaultMapData =
        "\"**************************\\n\" +\n" +
        "\"*                        *\\n\" +\n" +
        "\"* ********** *********** *\\n\" +\n" +
        "\"* ********** *********** *\\n\" +\n" +
        "\"*                        *\\n\" +\n" +
        "\"* ********** *********** *\\n\" +\n" +
        "\"* ********** *********** *\\n\" +\n" +
        "\"* ********** *********** *\\n\" +\n" +
        "\"*                        *\\n\" +\n" +
        "\"**************************\\n\" +\n"