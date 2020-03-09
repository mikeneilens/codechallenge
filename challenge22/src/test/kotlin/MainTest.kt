import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `try out decorator`() {
        val basicLaptop: Laptop = BasicLaptop()
        val laptopWithProcessorUpgrade: Laptop = ProcessUpgrade(basicLaptop)
        val laptopWithProcessorAndMemoryUpgrade = MemoryUpgradeDecorator(laptopWithProcessorUpgrade)
        println(laptopWithProcessorAndMemoryUpgrade.description())
    }
}