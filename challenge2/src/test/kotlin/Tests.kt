import junit.framework.TestCase.assertEquals
import org.junit.Test

class MyTests {
    @Test
    fun `Single valid price on input`() {
        assertEquals("Now £100",fixPriceLabel("Now £100"))
     }
    fun `Two valid prices on input`() {
        assertEquals("Was £200, now £100",fixPriceLabel("Now £100"))
    }
    fun `Three valid prices on input`() {
        assertEquals("Was £200, then £150, now £100",fixPriceLabel("Now £100"))
    }
    fun `Four valid prices on input`() {
        assertEquals("Was £200, then £150, now £100",fixPriceLabel("Now £100"))
    }
    fun `Three valid prices with some decimal`() {
        assertEquals("Was £200, then £150.00, now £100.50",fixPriceLabel("Now £100"))
    }
 }


