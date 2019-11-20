import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
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
        assertEquals(listOf(2), listOf(0).plusCard(twoOfClubs) )
    }
    @Test
    fun `adding a card of rank 2 to a list of 3 gives a list of 5  `() {
        val twoOfClubs = Card("2C")
        assertEquals(listOf(5), listOf(3).plusCard(twoOfClubs) )
    }
    @Test
    fun `adding a card of rank Ace to a list of 3 gives a list of 4,14 `() {
        val aceOfClubs = Card("AC")
        assertEquals(listOf(4,14), listOf(3).plusCard(aceOfClubs) )
    }
    @Test
    fun `adding a card of rank Ace and another card of rank Ace to a list of 3 gives a list of 5,15,15,25 `() {
        val aceOfClubs = Card("AC")
        val aceOfDiamonds = Card("AD")
        assertEquals(listOf(5,15,15,25), listOf(3).plusCard(aceOfClubs).plusCard(aceOfDiamonds))
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
    fun `The total less than 22 of a list containing 2 of clubs and queen of diamonds and ten of spades is null`(){
        val twoOfClubs = Card("2C")
        val queenOfDiamonds = Card("QD")
        val tenOfSpades = Card("TS")
        assertNull(listOf(twoOfClubs, queenOfDiamonds, tenOfSpades).totalLessThan22())
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
}
