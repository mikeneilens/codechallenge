import org.junit.jupiter.api.Assertions.assertEquals
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
}