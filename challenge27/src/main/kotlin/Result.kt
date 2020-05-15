enum class Result {
    Hit, Sunk, DMZ, Water;

    override fun toString():String = when(this) {
        Hit -> "H"
        Sunk -> "S"
        DMZ -> "."
        Water -> "M"
    }
}
fun String.toResult():Result = when(this) {
    "S" -> Result.Sunk
    "H" -> Result.Hit
    "M" -> Result.Water
    else -> Result.Water
}
