enum class Upgrade(val performUpgrade:(AnyLaptop)->AnyLaptop) {
    Memory(memory), Case(case), Processor(processor), Disk(disk), Graphics(graphics), Battery(battery)
}

open class  AnyLaptop(val description: String, val cost: Double, val upgrades:List<Upgrade> = listOf() ) {
    init {
        if (upgrades.distinct().size < upgrades.size ) throw Exception("Laptop already upgraded with ${upgrades.last()}")
    }
}

operator fun AnyLaptop.plus(other:Upgrade):AnyLaptop = other.performUpgrade(this)


val memory = fun (laptopBeingUpgraded: AnyLaptop) = AnyLaptop(
    laptopBeingUpgraded.description + ", 16GB Memory",
    laptopBeingUpgraded.cost + 49.99,
    laptopBeingUpgraded.upgrades + Upgrade.Memory )


val case = fun (laptopBeingUpgraded: AnyLaptop) = AnyLaptop(
    laptopBeingUpgraded .description + ", Shiny Case",
    laptopBeingUpgraded.cost + 10.99,
    laptopBeingUpgraded.upgrades + Upgrade.Case
)

val processor = fun (laptopBeingUpgraded: AnyLaptop) = AnyLaptop(
    laptopBeingUpgraded.description + ", Processor Upgrade",
    laptopBeingUpgraded.cost + 54.99,
    laptopBeingUpgraded.upgrades + Upgrade.Processor
)

val disk = fun (laptopBeingUpgraded: AnyLaptop) = AnyLaptop(
    laptopBeingUpgraded.description + ", 1TB Hard Disk",
    laptopBeingUpgraded.cost + 44.99,
    laptopBeingUpgraded.upgrades + Upgrade.Disk
)

val graphics = fun (laptopBeingUpgraded: AnyLaptop) = AnyLaptop(
    laptopBeingUpgraded.description + ", Gamer Graphics Card",
    laptopBeingUpgraded.cost + 67.99,
    laptopBeingUpgraded.upgrades + Upgrade.Graphics
)

val battery = fun (laptopBeingUpgraded: AnyLaptop) = AnyLaptop(
    laptopBeingUpgraded.description + ", Long Life Battery",
    laptopBeingUpgraded.cost + 67.99,
    laptopBeingUpgraded.upgrades + Upgrade.Battery
)
