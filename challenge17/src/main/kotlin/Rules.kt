class HandResult(val isTheTypeOfHand:Boolean, val value:Int=0)

typealias HandChecker = (Cards) -> HandResult

val isBust:HandChecker = fun (cards:Cards) = HandResult( (cards.totalLessThan22()== 0))

val isPontoon:HandChecker = fun (cards:Cards)=
    HandResult(  (cards.size == 2) && ((cards[0].rank is Rank.Picture && cards[1].rank is Rank.Ace) || (cards[0].rank is Rank.Ace && cards[1].rank is Rank.Picture)))

val isFiveCardTrick:HandChecker = fun (cards:Cards) = HandResult(  ((cards.size == 5) && (!isBust(cards).isTheTypeOfHand) ))

val isNotBust:HandChecker = fun (cards:Cards) = HandResult(!isBust(cards).isTheTypeOfHand, cards.totalLessThan22())

