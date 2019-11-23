
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

class HandResult(val isTheTypeOfHand:Boolean, val value:Int=0)

val isBust = fun (cards:Cards):Boolean = (cards.totalLessThan22()== 0)

val isPontoon = fun (cards:Cards)=
  HandResult(  (cards.size == 2) && ((cards[0].rank is Rank.Picture && cards[1].rank is Rank.Ace) || (cards[0].rank is Rank.Ace && cards[1].rank is Rank.Picture)))

val isNotBust = fun (cards:Cards) = HandResult(!isBust(cards),  cards.totalLessThan22())

val isFiveCardTrick = fun (cards:Cards) = HandResult(  ((cards.size == 5) && (!isBust(cards)) ))

typealias HandChecker = (Cards) -> HandResult
typealias HandCheckers = List<Pair<HandChecker,String>>

val handCheckers = listOf(Pair(isPontoon,"Pontoon"), Pair(isFiveCardTrick,"Five Card Trick"),Pair(isNotBust,"Total value of"))

fun determineWhichCardsWin(playersCards:Cards, dealersCards:Cards ) =
    if (playerBeatsDealer(playersCards, dealersCards, handCheckers))
        Result(Winner.Player,"Player wins with ${description(playersCards,handCheckers)}")
    else
        Result(Winner.Dealer,  "Dealer wins with ${description(dealersCards,handCheckers)}")


fun playerBeatsDealer(playersCards:Cards, dealersCards:Cards, handCheckers:HandCheckers):Boolean {
    val result = handCheckers.mapNotNull { (check,_) ->
        when (true) {
            (check(playersCards).isTheTypeOfHand && check(dealersCards).isTheTypeOfHand) -> (check(playersCards).value > check(dealersCards).value)
            (check(dealersCards).isTheTypeOfHand) -> false
            (check(playersCards).isTheTypeOfHand) -> true
            else -> null
        }
    }.firstOrNull()
    return result ?: false
}

fun description(cards:Cards, handCheckers:HandCheckers):String {
    val result = handCheckers.mapNotNull{ (check,description) ->
        if (check(cards).isTheTypeOfHand)
            if (check(cards).value > 0) "${description} ${check(cards).value}" else description
        else null
    }.firstOrNull()
    return result ?: "Bust"
}

//This adds a list of Ints to a list of Ints. E.g. [3,4,5] + [1,11] gives [(3+1),(3+11),(4+1),(4+11),(5+1),(5+11)] which is [4,14,5,15,6,16]
operator fun List<Int>.plus(card:Card): List<Int> = card.rank.value().flatMap{  cardValue ->  this.map{listItem -> cardValue + listItem}}

fun Cards.totalLessThan22():Int {
    return this.fold(listOf(0)) { acc, card -> acc + card }
        .sorted().lastOrNull { it < 22 } ?: 0
}
