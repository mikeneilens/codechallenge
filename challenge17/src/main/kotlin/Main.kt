
typealias Cards = List<Card>

sealed class Winner{
    object Player:Winner()
    object Dealer:Winner()
}
data class Result(val winner:Winner, val description:String)

fun determineWhoHasWon(_playersCards:List<String>, _dealersCards:List<String>):Result {
    val playersCards = _playersCards.map(::Card)
    val dealersCards = _dealersCards.map(::Card)

    return if (playerBeatsDealer(playersCards, dealersCards, handCheckers))
        Result(Winner.Player,"Player wins with ${description(playersCards,handCheckers)}")
    else
        Result(Winner.Dealer,  "Dealer wins with ${description(dealersCards,handCheckers)}")
}

typealias HandCheckers = Sequence<Pair<HandChecker,String>>

val handCheckers = sequenceOf(Pair(isPontoon,"Pontoon"), Pair(isFiveCardTrick,"Five Card Trick"),Pair(isNotBust,"Total value of"),Pair(isBust,"Bust but still wins"))

fun playerBeatsDealer(playersCards:Cards, dealersCards:Cards, handCheckers:HandCheckers):Boolean {
    val result = handCheckers.mapNotNull { (check,description) ->
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
            if (check(cards).value > 0) "$description ${check(cards).value}" else description
        else null
    }.firstOrNull()
    return result ?: "Bust"
}

//This adds a list of Ints to a list of Ints. E.g. [3,4,5] + [1,11] gives [(3+1),(3+11),(4+1),(4+11),(5+1),(5+11)] which is [4,14,5,15,6,16]
operator fun List<Int>.plus(card:Card): List<Int> = card.rank.value().flatMap{  cardValue ->  this.map{listItem -> cardValue + listItem}}

//Adds together all the cards. Because the ace has more than one value, there can be more than one total, so picked the biggest total below 22.
fun Cards.totalLessThan22() = this.fold(listOf(0)) { acc, card -> acc + card }.sorted().lastOrNull { it < 22 } ?: 0


