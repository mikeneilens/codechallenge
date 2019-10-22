import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
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
    fun `createDiscountForAnEAN creates a null if the value for discount is not prefixed with a £`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001","126.19")
        assertNull( createDiscountForEANorNull(dataForOneEAN))
    }
}