enum class Known (val text:String) {
    Hit("H"), Sunk("S"), DMZ ("."), Water("M");
}
fun String.toKnown():Known = when(this) {
    "S" -> Known.Sunk
    "H" -> Known.Hit
    "M" -> Known.Water
    else -> Known.Water
}
