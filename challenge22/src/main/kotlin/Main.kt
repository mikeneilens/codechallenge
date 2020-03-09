interface Laptop {
    fun description():String
}

class BasicLaptop : Laptop {
    override fun description() = "Basic Laptop"
}

abstract class LaptopDecorator(val decoratedLaptop: Laptop) : Laptop {
    override fun description() = decoratedLaptop.description()
}

class ProcessUpgrade(decoratedLaptop: Laptop) : LaptopDecorator(decoratedLaptop) {
    override fun description() = decoratedLaptop.description() + " + Better processor"
}

class MemoryUpgradeDecorator(decoratedLaptop: Laptop) : LaptopDecorator(decoratedLaptop) {
    override fun description():String = decoratedLaptop.description() + " + Better memory"
}
/*
Hard Drive upgrade

Graphic Card upgrade

Battery upgrade

A shiny case
 */