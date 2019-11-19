
sealed class Suit {
    object Clubs:Suit()
    object Diamonds:Suit()
    object Hearts:Suit()
    object Spades:Suit()

    companion object {
        val suitMap = mapOf("C" to Suit.Clubs, "D" to Suit.Diamonds, "H" to Suit.Hearts, "S" to Suit.Spades)
        fun fromString(string:String):Suit = suitMap[string] ?: Suit.Clubs
    }
}

sealed class Rank {
    object Ace:Rank()
    class Number(val value:Int): Rank()
    sealed class Picture:Rank() {
        object Jack:Picture()
        object Queen:Picture()
        object King:Picture()
    }

    fun value():List<Int> {
        return when(this) {
            is Ace -> listOf(1,11)
            is Picture -> listOf(10)
            is Number -> listOf(this.value)
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is Rank) &&
                   ((this is Number) && (other is Number) && (this.value == other.value)
                || ((this is Ace) && (other is Ace))
                || ((this is Picture.Jack) && (other is Picture.Jack))
                || ((this is Picture.Queen) && (other is Picture.Queen))
                || ((this is Picture.King) && (other is Picture.King)))
    }

    companion object {
        val rankMap = mapOf("T" to Rank.Number(10),"J" to Rank.Picture.Jack, "Q" to Rank.Picture.Queen, "K" to Rank.Picture.King,"A" to Rank.Ace)
        fun fromString(string:String):Rank {
            val number = string.toIntOrNull()
            return if (number != null) Rank.Number(number) else rankMap[string] ?: Rank.Ace
        }
    }
}

class Card(string:String) {
    val rank:Rank
    val suit:Suit
    init {
        this.rank = Rank.fromString(string.first().toString())
        this.suit = Suit.fromString(string.last().toString())
    }
}
