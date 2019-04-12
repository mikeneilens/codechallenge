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
    }
}
