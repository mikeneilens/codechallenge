import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `createDiscountForAnEAN creates the object correctly from an array of strings`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001","£126.19")

        assertEquals(DiscountForAnEAN("Tomato Soup", "E10001",126.19), createDiscountForEANorNull(dataForOneEAN))
    }
    @Test
    fun `createDiscountForAnEAN creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001")
        assertNull( createDiscountForEANorNull(dataForOneEAN))
    }
    @Test
    fun `createDiscountForAnEAN creates a null if the value for discount is not prefixed with a pound`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001","126.19")
        assertNull( createDiscountForEANorNull(dataForOneEAN))
    }

    @Test
    fun `an empty string creates an empty ListOfDiscountsForAnEAN`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("")
        assertTrue(listOfDiscountForAnEAN.isEmpty())
    }

    @Test
    fun `a string containing CSV data for one DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing one element`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("Tomato Soup,E10001,£126.19")
        assertTrue(listOfDiscountForAnEAN.size == 1)
        assertEquals(listOf(DiscountForAnEAN("Tomato Soup", "E10001",126.19)), listOfDiscountForAnEAN )
    }
    @Test
    fun `a string containing CSV data for several DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing several elements`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("Tomato Soup,E10001,£126.19,Tomato Soup,E10002,£123.80,Chicken Soup,E10004,£127.70")
        val expectedResult = listOf (
            DiscountForAnEAN("Tomato Soup","E10001",126.19),
            DiscountForAnEAN("Tomato Soup","E10002",123.80),
            DiscountForAnEAN("Chicken Soup","E10004",127.70))
        assertEquals(expectedResult, listOfDiscountForAnEAN )
    }
    @Test
    fun `a string containing CSV data for several DiscountForAnEAN including an invalid discount value creates a ListOfDiscountsForAnEAN containing several elements`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("Tomato Soup,E10001,£126.19,Tomato Soup,E10002,123.80,Chicken Soup,E10004,£127.70")
        val expectedResult = listOf (
            DiscountForAnEAN("Tomato Soup","E10001",126.19),
            DiscountForAnEAN("Chicken Soup","E10004",127.70))
        assertEquals(expectedResult, listOfDiscountForAnEAN )
    }
}