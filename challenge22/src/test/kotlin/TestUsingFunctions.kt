import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

class TestUsingFunctions {
    @Test
    fun `Adding a battery to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithBatteryUpgrade = battery(basicLaptop)

        assertEquals(467.99, laptopWithBatteryUpgrade.cost)
    }

    @Test
    fun `Adding more than one battery to a laptop throws an exception`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithBatteryUpgrade = battery(basicLaptop)

        assertEquals(467.99, laptopWithBatteryUpgrade.cost)
        assertThrows<Exception> { battery(laptopWithBatteryUpgrade)}
    }
}