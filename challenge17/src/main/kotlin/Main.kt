
typealias Cards = List<Card>

sealed class Winner{
    object Player:Winner()
    object Dealer:Winner()
}
data class Result(val winner:Winner, val description:String)

fun determineWhoHasWon(_playersCards:List<String>, _dealersCards:List<String>):Result {
    val playersCards = _playersCards.map(::Card)
    val dealersCards = _dealersCards.map(::Card)
    return determineWhichCardsWin(playersCards,dealersCards)
}

fun determineWhichCardsWin(playersCards:Cards, dealersCards:Cards):Result {
    if (playersCards.isWorthMoreThan(dealersCards)) return Result(Winner.Player,"Player wins with ${playersCards.description()}")
    else return Result(Winner.Dealer,  "Dealer wins with ${dealersCards.description()}")
}

val isPontoon = fun (cards:Cards):Boolean = (cards.size == 2) && ((cards[0].rank is Rank.Picture && cards[1].rank is Rank.Ace) || (cards[0].rank is Rank.Ace && cards[1].rank is Rank.Picture))
val isBust = fun (cards:Cards):Boolean = (cards.totalLessThan22()== 0)
val isFiveCardTrick = fun (cards:Cards):Boolean = ((cards.size == 5) && (!isBust(cards)))

val isWinnerRules = mapOf(isPontoon to "Pontoon", isFiveCardTrick to "Five Card Trick")

fun Cards.isWorthMoreThan(otherCards:Cards):Boolean {
    isWinnerRules.forEach { isWinner ->
        if (isWinner.key(otherCards)) return false
        if (isWinner.key(this)) return true
    }
    return (this.totalLessThan22() > otherCards.totalLessThan22())
}

fun Cards.description():String {
    isWinnerRules.forEach { isWinner ->
        if (isWinner.key(this)) return isWinner.value
    }
    return when (true) {
        isBust(this) -> "Bust"
        else -> "Total value of ${this.totalLessThan22()}"
    }
}

//This adds a list of Ints to a list of Ints. E.g. [3,4,5] + [1,11] gives [(3+1),(3+11),(4+1),(4+11),(5+1),(5+11)] which is [4,14,5,15,6,16]
fun List<Int>.plusCard(card:Card): List<Int> = card.rank.value().flatMap{  cardValue ->  this.map{listItem -> cardValue + listItem}}

fun Cards.totalLessThan22():Int {
    return this.fold(listOf(0)){acc, card -> acc.plusCard(card)}
        .sorted()
        .filter{it < 22}
        .lastOrNull() ?: 0
}

