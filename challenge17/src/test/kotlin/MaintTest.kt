import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class MainTest {
    //Test card suits are initialised correctly
    @Test
    fun `string AC creates a card with ace of clubs`() {
        val card = Card("AC")
        assertEquals(Rank.Ace, card.rank)
        assertEquals(Suit.Clubs, card.suit)
    }
    @Test
    fun `string AD creates a card with ace of diamonds`() {
        val card = Card("AD")
        assertEquals(Rank.Ace, card.rank)
        assertEquals(Suit.Diamonds, card.suit)
    }
    @Test
    fun `string AH creates a card with ace of hearts`() {
        val card = Card("AH")
        assertEquals(Rank.Ace, card.rank)
        assertEquals(Suit.Hearts, card.suit)
    }
    @Test
    fun `string AS creates a card with ace of spades`() {
        val card = Card("AS")
        assertEquals(Rank.Ace, card.rank)
        assertEquals(Suit.Spades, card.suit)
    }
    //Test card rank is initialised correctly
    @Test
    fun `string 2S creates a card with two of spades`() {
        val card = Card("2S")
        assertEquals(Rank.Number(2), card.rank)
    }
    @Test
    fun `string TS creates a card with ten of spades`() {
        val card = Card("TS")
        assertEquals(Rank.Number(10), card.rank)
    }
    @Test
    fun `string JS creates a card with jack of spades`() {
        val card = Card("JS")
        assertEquals(Rank.Picture.Jack, card.rank)
    }
    @Test
    fun `string QS creates a card with queen of spades`() {
        val card = Card("QS")
        assertEquals(Rank.Picture.Queen, card.rank)
    }
    @Test
    fun `string KS creates a card with king of spades`() {
        val card = Card("KS")
        assertEquals(Rank.Picture.King, card.rank)
    }
    @Test
    fun `adding a card of rank 2 to a list of 0 gives a list of 2  `() {
        val twoOfClubs = Card("2C")
        assertEquals(listOf(2), listOf(0) + twoOfClubs )
    }
    @Test
    fun `adding a card of rank 2 to a list of 3 gives a list of 5  `() {
        val twoOfClubs = Card("2C")
        assertEquals(listOf(5), listOf(3) + twoOfClubs )
    }
    @Test
    fun `adding a card of rank Ace to a list of 3 gives a list of 4,14 `() {
        val aceOfClubs = Card("AC")
        assertEquals(listOf(4,14), listOf(3) + aceOfClubs )
    }
    @Test
    fun `adding a card of rank Ace and another card of rank Ace to a list of 3 gives a list of 5,15,15,25 `() {
        val aceOfClubs = Card("AC")
        val aceOfDiamonds = Card("AD")
        assertEquals(listOf(5,15,15,25), listOf(3) + aceOfClubs + aceOfDiamonds)
    }
    @Test
    fun `The total less than 22 of a list containing 2 of clubs is 2`(){
        val twoOfClubs = Card("2C")
        assertEquals(2, listOf(twoOfClubs).totalLessThan22())
    }
    @Test
    fun `The total less than 22 of a list containing 2 of clubs and queen of diamonds is 12`(){
        val twoOfClubs = Card("2C")
        val queenOfDiamonds = Card("QD")
        assertEquals(12, listOf(twoOfClubs, queenOfDiamonds).totalLessThan22())
    }
    @Test
    fun `The total less than 22 of a list containing 2 of clubs and queen of diamonds and nine of spades is 21`(){
        val twoOfClubs = Card("2C")
        val queenOfDiamonds = Card("QD")
        val nineOfSpades = Card("9S")
        assertEquals(21, listOf(twoOfClubs, queenOfDiamonds, nineOfSpades).totalLessThan22())
    }
    @Test
    fun `The total less than 22 of a list containing 2 of clubs and queen of diamonds and ten of spades is zero`(){
        val twoOfClubs = Card("2C")
        val queenOfDiamonds = Card("QD")
        val tenOfSpades = Card("TS")
        assertEquals(0, listOf(twoOfClubs, queenOfDiamonds, tenOfSpades).totalLessThan22())
    }
    @Test
    fun `The total less than 22 of a list containing 2 of clubs and queen of diamonds and ace of spades is 13`(){
        val twoOfClubs = Card("2C")
        val queenOfDiamonds = Card("QD")
        val aceOfSpades = Card("AS")
        assertEquals(13, listOf(twoOfClubs, queenOfDiamonds, aceOfSpades).totalLessThan22())
    }
    @Test
    fun `The total less than 22 of a list containing 2 of clubs and ace of diamonds and ace of spades is 14`(){
        val twoOfClubs = Card("2C")
        val aceOfDiamonds = Card("AD")
        val aceOfSpades = Card("AS")
        assertEquals(14, listOf(twoOfClubs, aceOfDiamonds, aceOfSpades).totalLessThan22())
    }
    @Test
    fun `The total less than 22 of a list containing ace of clubs and ace of diamonds and ace of spades is 13`(){
        val aceOfClubs = Card("AC")
        val aceOfDiamonds = Card("AD")
        val aceOfSpades = Card("AS")
        assertEquals(13, listOf(aceOfClubs, aceOfDiamonds, aceOfSpades).totalLessThan22())
    }

    @Test
    fun `A Jack of spades and an Ace of diamonds is Pontoon`() {
        val jackOfSpades = Card("JS")
        val aceOfDiamonds = Card("AD")
        val cards = listOf(jackOfSpades,aceOfDiamonds)
        assertTrue(isPontoon(cards).isTheTypeOfHand)
    }
    @Test
    fun `An Ace of Diamonds and a  Jack of spades is Pontoon`() {
        val jackOfSpades = Card("JS")
        val aceOfDiamonds = Card("AD")
        val cards = listOf(aceOfDiamonds,jackOfSpades)
        assertTrue(isPontoon(cards).isTheTypeOfHand)
    }
    @Test
    fun `An Ace of Diamonds and a  ten of spades is not Pontoon`() {
        val tenOfSpades = Card("TS")
        val aceOfDiamonds = Card("AD")
        val cards = listOf(aceOfDiamonds,tenOfSpades)
        assertFalse(isPontoon(cards).isTheTypeOfHand)
    }
    @Test
    fun `An Ace of Diamonds and a  Jack of spades and two of clubs is not Pontoon`() {
        val jackOfSpades = Card("JS")
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val cards = listOf(aceOfDiamonds,jackOfSpades,twoOfClubs)
        assertFalse(isPontoon(cards).isTheTypeOfHand)
    }
    @Test
    fun `five cards that add up to less than 22 is five card trick`() {
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val threeOfDiamonds = Card("3D")
        val twoOfSpades = Card("2S")
        val fiveOfHearts = Card("5H")
        val cards = listOf(fiveOfHearts,twoOfSpades,twoOfClubs,threeOfDiamonds,aceOfDiamonds)
        assertTrue(isFiveCardTrick(cards).isTheTypeOfHand)
    }
    @Test
    fun `four cards that add up to less than 22 is not five card trick`() {
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val threeOfDiamonds = Card("3D")
        val fiveOfHearst = Card("5H")
        val cards = listOf(fiveOfHearst,twoOfClubs,threeOfDiamonds,aceOfDiamonds)
        assertFalse(isFiveCardTrick(cards).isTheTypeOfHand)
    }
    @Test
    fun `six cards that add up to less than 22 is not five card trick`() {
        val aceOfDiamonds = Card("AD")
        val aceOfSpades = Card("AS")
        val twoOfClubs = Card("2C")
        val threeOfDiamonds = Card("3D")
        val twoOfSpades = Card("2S")
        val fiveOfHearts = Card("5H")
        val cards = listOf(aceOfSpades,fiveOfHearts,twoOfSpades,twoOfClubs,threeOfDiamonds,aceOfDiamonds)
        assertFalse(isFiveCardTrick(cards).isTheTypeOfHand)
    }
    @Test
    fun `five cards that add up to more than 21 is not five card trick`() {
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val sevenOfDiamonds = Card("7D")
        val tenOfSpades = Card("Ts")
        val fiveOfHearts = Card("5H")
        val cards = listOf(fiveOfHearts,tenOfSpades,twoOfClubs,sevenOfDiamonds,aceOfDiamonds)
        assertFalse(isFiveCardTrick(cards).isTheTypeOfHand)
    }
    @Test
    fun `jack spades plus 7 of diamonds plus 6 of hearts is bust`() {
        val jackOfSpades = Card("JS")
        val sevenOfDiamonds = Card("7D")
        val sixOfHearts = Card("6H")
        val cards = listOf(jackOfSpades,sevenOfDiamonds,sixOfHearts)
        assertTrue(isBust(cards).isTheTypeOfHand)
    }
    @Test
    fun `jack spades plus 7 of diamonds plus 4 of hearts is not bust`() {
        val jackOfSpades = Card("JS")
        val sevenOfDiamonds = Card("7D")
        val fourOfHearts = Card("4H")
        val cards = listOf(jackOfSpades,sevenOfDiamonds,fourOfHearts)
        assertFalse(isBust(cards).isTheTypeOfHand)
    }
    @Test
    fun `jack spades plus 7 of diamonds plus 6 of hearts is a hand which is not notBust`() {
        val jackOfSpades = Card("JS")
        val sevenOfDiamonds = Card("7D")
        val sixOfHearts = Card("6H")
        val cards = listOf(jackOfSpades,sevenOfDiamonds,sixOfHearts)
        assertFalse(isNotBust(cards).isTheTypeOfHand)
    }
    @Test
    fun `jack spades plus 7 of diamonds plus 4 of hearts is a hand which is notBust`() {
        val jackOfSpades = Card("JS")
        val sevenOfDiamonds = Card("7D")
        val fourOfHearts = Card("4H")
        val cards = listOf(jackOfSpades,sevenOfDiamonds,fourOfHearts)
        assertTrue(isNotBust(cards).isTheTypeOfHand)
    }
    @Test
    fun `Pontoon does not beat another Pontoon`() {
        val jackOfSpades = Card("JS")
        val aceOfDiamonds = Card("AD")
        val kingOfSpades = Card("KS")
        val aceOfHearts = Card("AH")

        val playersCards = listOf(jackOfSpades, aceOfDiamonds)
        val dealersCards = listOf(kingOfSpades, aceOfHearts)
        assertFalse(playerBeatsDealer(playersCards,dealersCards, handCheckers))
    }
    @Test
    fun `Pontoon beats a five card trick`() {
        val jackOfSpades = Card("JS")
        val aceOfHearts = Card("AD")
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val threeOfDiamonds = Card("3D")
        val twoOfSpades = Card("2S")
        val fiveOfHearts = Card("5H")

        val playersCards = listOf(jackOfSpades, aceOfHearts)
        val dealersCards = listOf(fiveOfHearts,twoOfSpades,twoOfClubs,threeOfDiamonds,aceOfDiamonds)
        assertTrue(playerBeatsDealer(playersCards ,dealersCards,handCheckers))
    }
    @Test
    fun `Five card trick does not beat a five card trick`() {
        val aceOfHearts = Card("AD")
        val twoOfHearts = Card("2H")
        val threeOfHearts = Card("3H")
        val twoOfDiamonds = Card("2D")
        val fiveOfClubs = Card("5C")
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val threeOfDiamonds = Card("3D")
        val twoOfSpades = Card("2S")
        val fiveOfHearts = Card("5H")

        val playersCards = listOf(aceOfHearts,twoOfHearts,threeOfHearts,twoOfDiamonds,fiveOfClubs)
        val dealersCards = listOf(fiveOfHearts,twoOfSpades,twoOfClubs,threeOfDiamonds,aceOfDiamonds)
        assertFalse(playerBeatsDealer(playersCards, dealersCards,handCheckers))
    }
    @Test
    fun `Five card trick beats a hand which is not bust but is not a Pontoon or five card trick`() {
        val aceOfHearts = Card("AH")
        val twoOfHearts = Card("2H")
        val threeOfHearts = Card("3H")
        val twoOfDiamonds = Card("2D")
        val fiveOfClubs = Card("5C")
        val aceOfDiamonds = Card("AD")
        val threeOfDiamonds = Card("3D")

        val playersCards = listOf(aceOfHearts,twoOfHearts,threeOfHearts,twoOfDiamonds,fiveOfClubs)
        val dealersCards = listOf(threeOfDiamonds,aceOfDiamonds)
        assertTrue(playerBeatsDealer(playersCards, dealersCards,handCheckers))
    }
    @Test
    fun `hand which is not bust does not beat a hand of the same value`() {
        val aceOfHearts = Card("AH")
        val twoOfHearts = Card("2H")
        val threeOfHearts = Card("3H")
        val kingOfDiamonds = Card("KD")
        val sixOfClubs = Card("6C")

        val playersCards = listOf(aceOfHearts,twoOfHearts,threeOfHearts)
        val dealersCards = listOf(kingOfDiamonds,sixOfClubs)
        assertFalse(playerBeatsDealer(playersCards, dealersCards,handCheckers))
    }
    @Test
    fun `hand which is not bust does beat a hand of a lower value`() {
        val aceOfHearts = Card("AH")
        val twoOfHearts = Card("2H")
        val threeOfHearts = Card("3H")
        val kingOfDiamonds = Card("KD")
        val fiveOfClubs = Card("5C")

        val playersCards = listOf(aceOfHearts,twoOfHearts,threeOfHearts)
        val dealersCards = listOf(kingOfDiamonds,fiveOfClubs)
        assertTrue(playerBeatsDealer(playersCards,dealersCards,handCheckers))
    }
    @Test
    fun `hand which is not bust does beat a hand which is bust`() {
        val aceOfHearts = Card("AH")
        val twoOfHearts = Card("2H")
        val threeOfHearts = Card("3H")
        val kingOfDiamonds = Card("KD")
        val fiveOfClubs = Card("5C")
        val sevenOfClubs = Card("7C")

        val playersCards = listOf(aceOfHearts,twoOfHearts,threeOfHearts)
        val dealersCards = listOf(kingOfDiamonds,fiveOfClubs, sevenOfClubs)
        assertTrue(playerBeatsDealer(playersCards, dealersCards ,handCheckers))
    }
    @Test
    fun `hand which is bust does not beat a hand which is bust`() {
        val kingOfHearts = Card("KH")
        val nineOfHearts = Card("9H")
        val threeOfHearts = Card("3H")
        val kingOfDiamonds = Card("KD")
        val fiveOfClubs = Card("5C")
        val sevenOfClubs = Card("7C")

        val playersCards = listOf(kingOfHearts,nineOfHearts,threeOfHearts)
        val dealersCards =  listOf(kingOfDiamonds,fiveOfClubs, sevenOfClubs)
        assertFalse(playerBeatsDealer(playersCards, dealersCards,handCheckers))
    }
    @Test
    fun `description of a Pontoon is Pontoon`() {
        val jackOfSpades = Card("JS")
        val aceOfDiamonds = Card("AD")
        val cards = listOf(jackOfSpades,aceOfDiamonds)
        assertEquals("Pontoon", description(cards, handCheckers))
    }
    @Test
    fun `description of a five card trick is Five Card Trick`() {
        val aceOfDiamonds = Card("AD")
        val twoOfClubs = Card("2C")
        val threeOfDiamonds = Card("3D")
        val twoOfSpades = Card("2S")
        val fiveOfHearts = Card("5H")
        val cards = listOf(fiveOfHearts,twoOfSpades,twoOfClubs,threeOfDiamonds,aceOfDiamonds)
        assertEquals("Five Card Trick", description(cards, handCheckers))
    }
    @Test
    fun `description of a bust hand is Bust`() {
        val jackOfSpades = Card("JS")
        val sevenOfDiamonds = Card("7D")
        val sixOfHearts = Card("6H")
        val cards = listOf(jackOfSpades,sevenOfDiamonds,sixOfHearts)
        assertEquals("Bust but still wins", description(cards, handCheckers))
    }
    @Test
    fun `description of two cards worth a total of 17 is  Total value of 17`() {
        val jackOfSpades = Card("JS")
        val sevenOfDiamonds = Card("7D")
        val cards = listOf(jackOfSpades,sevenOfDiamonds)
        assertEquals("Total value of 17", description(cards, handCheckers))
    }

    @Test
    fun `when player has JS and AD and the dealer has JH and 7D the result is player winning`() {
        val jackOfSpades = "JS"
        val aceOfDiamonds = "AD"
        val playersCards = listOf(jackOfSpades,aceOfDiamonds)

        val jackOfHearts = "JH"
        val sevenOfDiamonds = "7D"
        val dealersCards = listOf(jackOfHearts,sevenOfDiamonds)

        val result = determineWhoHasWon(playersCards,dealersCards)
        assertEquals(Winner.Player, result.winner)
        assertEquals("Player wins with Pontoon", result.description)
    }
    @Test
    fun `when player has JS and 7H and the dealer has JH and 7D the result is dealer winning`() {
        val jackOfSpades = "JS"
        val sevenOfHearts = "7H"
        val playersCards = listOf(jackOfSpades,sevenOfHearts)

        val jackOfHearts = "JH"
        val sevenOfDiamonds = "7D"
        val dealersCards = listOf(jackOfHearts,sevenOfDiamonds)

        val result = determineWhoHasWon(playersCards,dealersCards)
        assertEquals(Winner.Dealer, result.winner)
        assertEquals("Dealer wins with Total value of 17", result.description)
    }
    @Test
    fun `when player has bust and the dealer has bust the result is dealer winning`() {
        val jackOfSpades = "JS"
        val jackOfDiaonds = "JD"
        val sevenOfHearts = "7H"
        val playersCards = listOf(jackOfSpades,sevenOfHearts,jackOfDiaonds)

        val jackOfHearts = "JH"
        val kingOfHearts = "JH"
        val sevenOfDiamonds = "7D"
        val dealersCards = listOf(jackOfHearts,sevenOfDiamonds,kingOfHearts)

        val result = determineWhoHasWon(playersCards,dealersCards)
        assertEquals(Winner.Dealer, result.winner)
        assertEquals("Dealer wins with Bust but still wins", result.description)
    }
}
