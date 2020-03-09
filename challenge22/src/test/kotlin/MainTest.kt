import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `try out decorator`() {
        val basicLaptop: Laptop = BasicLaptop(400)
        val laptopWithProcessorUpgrade: Laptop = ProcessorUpgrade(basicLaptop, ProcessorOption.quadCore)
        val laptopWithProcessorAndMemoryUpgrade = MemoryUpgrade(laptopWithProcessorUpgrade, MemoryOption._16Gb)
        println(laptopWithProcessorAndMemoryUpgrade)
        println(laptopWithProcessorAndMemoryUpgrade.price)

        val lapTopWithSSDUpgrade = DriveUpgrade(basicLaptop, DriveOption._512SSD)
        println(lapTopWithSSDUpgrade)

        val laptopWithGraphicsUpgrade = GraphicsUpgrade(basicLaptop, GraphicsOption.option2)
        println(laptopWithGraphicsUpgrade)

        val laptopWithShinyCaseUpgrade = ShinyCaseUpgrade(basicLaptop, 200)
        println(laptopWithShinyCaseUpgrade)

        //This will throw an error
        val laptopWithTwoProcessUpgrades = ProcessorUpgrade(laptopWithProcessorUpgrade, ProcessorOption.sixCoreFast)
        println(laptopWithTwoProcessUpgrades) // never reached
    }
}