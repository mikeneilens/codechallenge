import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `DiscountForAnEAN is created correctly from an array of strings`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001","£126.19")

        assertEquals(DiscountForAnEAN("Tomato Soup", "E10001",126.19), createDiscountForEANorNull(dataForOneEAN))
    }

}