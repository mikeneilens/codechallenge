import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

class TestUsingFunctions {
    @Test
    fun `Adding a battery to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithBatteryUpgrade = basicLaptop + Upgrade.Battery

        assertEquals(467.99, laptopWithBatteryUpgrade.cost)
    }
    @Test
    fun `Adding a memory to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithMemoryUpgrade = basicLaptop + Upgrade.Memory

        assertEquals(449.99, laptopWithMemoryUpgrade.cost)
    }
    @Test
    fun `Adding a case to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithCaseUpgrade = basicLaptop + Upgrade.Case

        assertEquals(410.99, laptopWithCaseUpgrade.cost)
    }
    @Test
    fun `Adding a processor to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithProcessorUpgrade = basicLaptop + Upgrade.Processor

        assertEquals(454.99, laptopWithProcessorUpgrade.cost)
    }
    @Test
    fun `Adding a disk to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithDiskUpgrade = basicLaptop + Upgrade.Disk

        assertEquals(444.99, laptopWithDiskUpgrade.cost)
    }
    @Test
    fun `Adding a graphics to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithGraphicskUpgrade = basicLaptop + Upgrade.Graphics

        assertEquals(467.99, laptopWithGraphicskUpgrade.cost)
    }
@Test
    fun `Adding a graphics and a battery to a laptop`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        val laptopWithGraphicsAndBatteryUpgrade = basicLaptop + Upgrade.Graphics + Upgrade.Battery

        assertEquals(535.98, laptopWithGraphicsAndBatteryUpgrade.cost)
    }

    @Test
    fun `Adding more than one battery to a laptop throws an exception`() {
        val basicLaptop = AnyLaptop("Basic laptop", 400.0)

        assertThrows<Exception> { basicLaptop + Upgrade.Battery + Upgrade.Graphics + Upgrade.Battery}
    }
}