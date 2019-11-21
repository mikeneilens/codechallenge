

//This adds a list of Ints to a list of Ints. E.g. [3,4,5] + [1,11] gives [(3+1),(3+11),(4+1),(4+11),(5+1),(5+11)] which is [4,14,5,15,6,16]
fun List<Int>.plusCard(card: Card): List<Int> = card.rank.value().flatMap{  aceValue ->  this.map{aceValue + it}}

fun List<Card>.totalLessThan22():Int {
    return this.fold(listOf(0)){acc, card -> acc.plusCard(card)}
        .sorted()
        .filter{it < 22}
        .lastOrNull() ?: 0
}

fun List<Card>.isPontoon():Boolean = (this.size == 2) && ((this[0].rank is Rank.Picture && this[1].rank is Rank.Ace) || (this[0].rank is Rank.Ace && this[1].rank is Rank.Picture))

fun List<Card>.isBust():Boolean = (this.totalLessThan22()== 0)

fun List<Card>.isFiveCardTrick():Boolean = ((this.size == 5) && (!this.isBust()))

fun List<Card>.isWorthMoreThan(other:List<Card>):Boolean {
    return when (true) {
        other.isPontoon() -> false
        this.isPontoon() -> true
        other.isFiveCardTrick() -> false
        this.isFiveCardTrick() -> true
        else -> (this.totalLessThan22() > other.totalLessThan22())
    }
}