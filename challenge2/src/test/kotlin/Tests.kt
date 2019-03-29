import junit.framework.TestCase.assertEquals
import org.junit.Test

class MyTests {
    @Test
    fun `Single valid price on input`() {
        assertEquals("now £100",fixPriceLabel("now £100"))
     }
    @Test
    fun `Two valid prices on input`() {
        assertEquals("Was £200, now £100",fixPriceLabel("Was £200, now £100"))
    }
    @Test
    fun `Three valid prices on input`() {
        assertEquals("Was £200, then £150, now £100",fixPriceLabel("Was £200, then £150, now £100"))
    }
    @Test
    fun `Four valid prices on input`() {
        assertEquals("Was £200, then £150, now £100",fixPriceLabel("Was £200, then £150, now £100"))
    }
    @Test
    fun `Three valid prices with some decimal`() {
        assertEquals("Was £200, then £150.00, now £100.50",fixPriceLabel("Was £200, then £150.00, now £100.50"))
    }
    @Test
    fun `Now price is higher than the Was price`() {
        assertEquals("Was £200, now £250",fixPriceLabel("now £2500"))
    }
 }


