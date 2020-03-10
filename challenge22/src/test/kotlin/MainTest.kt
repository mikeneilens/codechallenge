import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

class MainTest {
    @Test
    fun `adding a processor upgrade to a basic laptop`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val laptopWithProcessorUpgrade: Laptop = ProcessorUpgrade(basicLaptop, ProcessorOption.quadCore)

        assertEquals(500, laptopWithProcessorUpgrade.price)
        assertEquals("Basic Laptop (£400) + Quad Core, 2.3 Ghz Processor (£100)", "$laptopWithProcessorUpgrade" )

        if (laptopWithProcessorUpgrade is ProcessorUpgrade) {
            assertTrue(laptopWithProcessorUpgrade.decoratedLaptop == basicLaptop)
        }
    }

    @Test
    fun `adding a memory upgrade to a basic laptop`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val laptopWithMemoryUpgrade: Laptop = MemoryUpgrade(basicLaptop, MemoryOption._32Gb)

        assertEquals(490, laptopWithMemoryUpgrade.price)
        assertEquals("Basic Laptop (£400) + 32Gb Memory (£90)", "$laptopWithMemoryUpgrade" )

        if (laptopWithMemoryUpgrade is ProcessorUpgrade) {
            assertTrue(laptopWithMemoryUpgrade.decoratedLaptop == basicLaptop)
        }
    }

    @Test
    fun `adding a drive upgrade to a basic laptop`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val laptopWithDriveUpgrade: Laptop = DriveUpgrade(basicLaptop, DriveOption._512SSD)

        assertEquals(550, laptopWithDriveUpgrade.price)
        assertEquals("Basic Laptop (£400) + 512 Gbyte SSD (£150)", "$laptopWithDriveUpgrade" )

        if (laptopWithDriveUpgrade is ProcessorUpgrade) {
            assertTrue(laptopWithDriveUpgrade.decoratedLaptop == basicLaptop)
        }
    }

    @Test
    fun `adding a graphics upgrade to a basic laptop`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val laptopWithGraphicsUpgrade: Laptop = GraphicsUpgrade(basicLaptop, GraphicsOption.option1)

        assertEquals(600, laptopWithGraphicsUpgrade.price)
        assertEquals("Basic Laptop (£400) + NVIDIA® GeForce® RTX™ 2070 Graphics Card (£200)", "$laptopWithGraphicsUpgrade" )

        if (laptopWithGraphicsUpgrade is ProcessorUpgrade) {
            assertTrue(laptopWithGraphicsUpgrade.decoratedLaptop == basicLaptop)
        }
    }

    @Test
    fun `adding a shiny case upgrade to a basic laptop`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val laptopWithCaseUpgrade: Laptop = ShinyCaseUpgrade(basicLaptop, 100)

        assertEquals(500, laptopWithCaseUpgrade.price)
        assertEquals("Basic Laptop (£400) +  Shiny case (£100)", "$laptopWithCaseUpgrade" )

        if (laptopWithCaseUpgrade is ShinyCaseUpgrade) {
            assertTrue(laptopWithCaseUpgrade.decoratedLaptop == basicLaptop)
        }
    }

    @Test
    fun `adding more than one upgrade to a basic laptop`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val finalLaptop = ProcessorUpgrade(ShinyCaseUpgrade(MemoryUpgrade(basicLaptop,MemoryOption._16Gb),100),ProcessorOption.sixCoreFast)

        assertEquals(850, finalLaptop.price)
        assertEquals("Basic Laptop (£400) + 16Gb Memory (£50) +  Shiny case (£100) + Six Core, 2.8 Ghz Processor (£300)", "$finalLaptop" )

    }

    @Test
    fun `adding the same option twice to a basic laptop throws an error`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val firstProcessUpgrade = ProcessorUpgrade(basicLaptop, ProcessorOption.quadCore)
        assertThrows<Exception> { ProcessorUpgrade(firstProcessUpgrade, ProcessorOption.sixCoreFast)}
    }
}