
sealed class Suit {
    object Clubs:Suit()
    object Diamonds:Suit()
    object Hearts:Suit()
    object Spades:Suit()

    companion object {
        val suitMap = mapOf("C" to Clubs, "D" to Diamonds, "H" to Hearts, "S" to Spades)
        fun fromString(string:String):Suit = suitMap[string] ?: Clubs
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
        private val rankMap = mapOf("T" to Number(10),"J" to Picture.Jack, "Q" to Picture.Queen, "K" to Picture.King,"A" to Ace)
        fun fromString(string:String):Rank {
            val number = string.toIntOrNull()
            return if (number != null) Number(number) else rankMap[string] ?: Ace
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

fun List<Int>.plusCard(card: Card): List<Int> = card.rank.value().flatMap{  aceValue ->  this.map{aceValue + it}}

fun List<Card>.totalLessThan22():Int? {
    return this.fold(listOf(0)){acc, card -> acc.plusCard(card)}.filter{it < 22}.lastOrNull()
}

fun List<Card>.isPontoon():Boolean = (this.size == 2) && ((this[0].rank is Rank.Picture && this[1].rank is Rank.Ace) || (this[0].rank is Rank.Ace && this[1].rank is Rank.Picture))

fun List<Card>.isFiveCardTrick():Boolean = ((this.size == 5) && (this.totalLessThan22() != null))