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
}

fun MutableMap<Position, MapTile>.fromString(row:Int, string:String) {
    string.forEachIndexed{ column, character ->
        this[Position(row, column)] = MapTile.fromChar(character)
    }
}
fun MutableMap<Position, MapTile>.toString(row:Int):String {
    val listOfPositionMapTile = this.filter { mapTile -> mapTile.key.row == row }.toSortedMap(compareBy { position ->  position.column }).toList()
    return listOfPositionMapTile.fold("") { acc, pair -> acc + pair.second.text}
}

fun List<String>.toMap():MutableMap<Position,MapTile> {
    val map= mutableMapOf<Position,MapTile>()
    this.forEachIndexed{ row, string ->
        map.fromString(row, string)
    }
    return map
}
fun MutableMap<Position, MapTile>.toListOfString():List<String> {
    return this.keys.toList().map{it.row }.distinct().sorted().map{row -> this.toString(row)}
}