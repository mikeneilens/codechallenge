enum class Upgrade {
    none, memory, case, processor, disk, graphics, battery
}

open class  AnyLaptop(val description: String, val cost: Double, val upgrade:Upgrade = Upgrade.none )

class UpgradedLaptop(description:String, cost:Double, upgrade:Upgrade, val laptopBeingUpgraded:AnyLaptop): AnyLaptop(description, cost, upgrade) {
    init {
        if (laptopBeingUpgraded.upgradeAlreadyAdded(upgrade)) throw Exception("Laptop already upgraded with $upgrade")
    }
}

fun AnyLaptop.upgradeAlreadyAdded(upgrade: Upgrade):Boolean =
    when (this) {
        is UpgradedLaptop -> if (this.upgrade == upgrade) true else laptopBeingUpgraded.upgradeAlreadyAdded(upgrade)
        else -> false
    }

fun memory(laptopBeingUpgraded: AnyLaptop) = UpgradedLaptop(
    laptopBeingUpgraded.description + ", 16GB Memory",
    laptopBeingUpgraded.cost + 49.99,
    Upgrade.memory,
    laptopBeingUpgraded)


fun case(laptopBeingUpgraded: AnyLaptop) = UpgradedLaptop(
    laptopBeingUpgraded .description + ", Shiny Case",
    laptopBeingUpgraded.cost + 10.99,
    Upgrade.case,
    laptopBeingUpgraded
)

fun processor(laptopBeingUpgraded: AnyLaptop) = UpgradedLaptop(
    laptopBeingUpgraded.description + ", Processor Upgrade",
    laptopBeingUpgraded.cost + 54.99,
    Upgrade.processor,
    laptopBeingUpgraded
)

fun disk(laptopBeingUpgraded: AnyLaptop) = UpgradedLaptop(
    laptopBeingUpgraded.description + ", 1TB Hard Disk",
    laptopBeingUpgraded.cost + 44.99,
    Upgrade.disk,
    laptopBeingUpgraded
)

fun graphics(laptopBeingUpgraded: AnyLaptop) = UpgradedLaptop(
    laptopBeingUpgraded.description + ", Gamer Graphics Card",
    laptopBeingUpgraded.cost + 67.99,
    Upgrade.graphics,
    laptopBeingUpgraded
)

fun battery(laptopBeingUpgraded: AnyLaptop) = UpgradedLaptop(
    laptopBeingUpgraded.description + ", Long Life Battery",
    laptopBeingUpgraded.cost + 67.99,
    Upgrade.battery,
    laptopBeingUpgraded
)
