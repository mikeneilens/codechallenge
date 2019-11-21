
//This adds a list of Ints to a list of Ints. E.g. [3,4,5] + [1,11] gives [(3+1),(3+11),(4+1),(4+11),(5+1),(5+11)] which is [4,14,5,15,6,16]
fun List<Int>.plusCard(card: Card): List<Int> = card.rank.value().flatMap{  aceValue ->  this.map{aceValue + it}}

fun List<Card>.totalLessThan22():Int {
    return this.fold(listOf(0)){acc, card -> acc.plusCard(card)}
        .sorted()
        .filter{it < 22}
        .lastOrNull() ?: 0
}

val isPontoon = fun (cards:List<Card>):Boolean = (cards.size == 2) && ((cards[0].rank is Rank.Picture && cards[1].rank is Rank.Ace) || (cards[0].rank is Rank.Ace && cards[1].rank is Rank.Picture))

val isBust = fun (cards:List<Card>):Boolean = (cards.totalLessThan22()== 0)

val isFiveCardTrick = fun (cards:List<Card>):Boolean = ((cards.size == 5) && (!isBust(cards)))

val isWinnerRules = listOf(isPontoon,isFiveCardTrick)

fun List<Card>.isWorthMoreThan(other:List<Card>):Boolean {
    isWinnerRules.forEach { isWinner ->
        if (isWinner(other)) return false
        if (isWinner(this)) return true
    }
    return (this.totalLessThan22() > other.totalLessThan22())
}