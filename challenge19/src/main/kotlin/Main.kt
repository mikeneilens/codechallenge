import org.hashids.Hashids

data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position) = Position(x + other.x, y + other.y)
}

val moveNorth = Position(0,-1)
val moveSouth = Position(0,1)
val moveWest = Position(-1,0)
val moveEast = Position(1,0)

enum class Command { M,L,R }

enum class Output(val symbol:String) {
    Left("L"),Right("R"),Open("O")
}

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

fun viewAhead(position:Position, orientation:Orientation, mapData:String):List<String>{
    var newPosition = position
    var result = mutableListOf<String>()
    while ( mapData.contentAt( newPosition + orientation.ahead) != Content.Fixture ) {
        newPosition += orientation.ahead
        var stringForPosition = Output.Open.symbol
        if (mapData.contentAt( newPosition + orientation.left) == Content.Open) stringForPosition += Output.Left.symbol
        if (mapData.contentAt( newPosition + orientation.right) == Content.Open) stringForPosition += Output.Right.symbol
        result.add(stringForPosition)
    }
    return result
}

fun moveTrolley(command:Command, referenceId:String, mapData:String): Pair<List<String>, String>{
    val (position, orientation, trollyId) = UserDetails.from(referenceId)

    val (newPosition:Position, newOrientation) = moveOrRotate(command,position,orientation,mapData)

    val viewAhead = viewAhead(newPosition, newOrientation, mapData)

    val newReferenceId = UserDetails(newPosition, newOrientation, trollyId).toReferenceId()
    return Pair(viewAhead, newReferenceId )
}

fun moveOrRotate(command:Command, position:Position, orientation:Orientation, mapData:String) =
    when (command) {
        Command.M ->if (mapData.contentAt(position + orientation.ahead) != Content.Fixture)
                         Pair (position + orientation.ahead, orientation)
                    else Pair(position, orientation)
        Command.L -> Pair(position, orientation.turnLeft())
        Command.R -> Pair(position, orientation.turnRight())
    }

fun moveTrolley(mapData:String):Pair<List<String>,String> {
    val x = 1
    val y = 1
    val orientation = Orientation.East
    val viewAhead = viewAhead(Position(x,y), orientation, mapData)
    val referenceId = UserDetails(Position(x,y),orientation,1234).toReferenceId()
    return Pair(viewAhead, referenceId)
}

data class UserDetails(val position:Position, val orentiation:Orientation, val trollyId:Int) {
    fun toReferenceId(): String =   hashids.encode(position.x.toLong(), position.y.toLong(), orentiation.value.toLong(), trollyId.toLong())
    companion object {
        private val hashids = Hashids("a random string")
        fun from(hash:String):UserDetails {
            val decoded = hashids.decode(hash)
            return UserDetails( Position(decoded[0].toInt(),decoded[1].toInt()),Orientation.from(decoded[2].toInt()),decoded[3].toInt())
        }
    }
}
