interface Laptop {
    override fun toString():String
    val price get():Int = 0
}

class BasicLaptop(override val price:Int) : Laptop {
    override fun toString() = "Basic Laptop (£$price)"
}

abstract class LaptopDecorator  (val decoratedLaptop: Laptop,  val optionPrice:Int, val name:String, val description:String): Laptop {
    override fun toString(): String  = "$decoratedLaptop + $name $description (£$optionPrice)"
    override val price = decoratedLaptop.price + optionPrice

    init {
        if (upgradeAlreadyAdded(decoratedLaptop)) throw Exception("Trying to add $description to $decoratedLaptop.  The laptop already has a $description upgrade")
    }
}

fun LaptopDecorator.upgradeAlreadyAdded(laptopDecorator: Laptop):Boolean {
    return when (laptopDecorator) {
        is LaptopDecorator -> if (this.description == laptopDecorator.description) true else upgradeAlreadyAdded(laptopDecorator.decoratedLaptop)
        is Laptop -> false
        else -> false
    }
}

class ProcessorUpgrade(decoratedLaptop: Laptop, processorOption: ProcessorOption)
    : LaptopDecorator(decoratedLaptop, processorOption.price, processorOption.description, "Processor")

class MemoryUpgrade(decoratedLaptop: Laptop, memoryOption: MemoryOption )
    : LaptopDecorator(decoratedLaptop, memoryOption.price, memoryOption.description, "Memory")

class DriveUpgrade(decoratedLaptop: Laptop, driveOption: DriveOption )
    : LaptopDecorator(decoratedLaptop, driveOption.price, driveOption.description, "SSD")

class GraphicsUpgrade(decoratedLaptop: Laptop, graphicsOption: GraphicsOption )
    : LaptopDecorator(decoratedLaptop, graphicsOption.price, graphicsOption.description, "Graphics Card")

class BatteryUpgrade(decoratedLaptop: Laptop, optionPrice:Int )
    : LaptopDecorator(decoratedLaptop, optionPrice, "", "Large Battery")

class ShinyCaseUpgrade(decoratedLaptop: Laptop, optionPrice:Int )
    : LaptopDecorator(decoratedLaptop, optionPrice, "", "Shiny case")

enum class MemoryOption (val description:String, val price:Int) {
    _16Gb ("16Gb",50),
    _32Gb ("32Gb", 90)
}

enum class ProcessorOption (val description:String, val price:Int) {
    quadCore ("Quad Core, 2.3 Ghz",100),
    quadCoreFast ("Quad Core, 2.8 Ghz",200),
    sixCoreFast ("Six Core, 2.8 Ghz",300),
}

enum class DriveOption (val description:String, val price:Int) {
    _256SSD ("256 Gbyte",100),
    _512SSD ("512 Gbyte",150),
    _1TBSSD ("1Tbyte",200)
}

enum class GraphicsOption (val description:String, val price:Int) {
    option1 ("NVIDIA® GeForce® RTX™ 2070",200),
    option2 ("NVIDIA® GeForce® MX250",400)
}