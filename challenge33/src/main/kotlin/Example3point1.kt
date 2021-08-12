typealias Id = String

open class Item(
    open val id:Id,
    private val uses:MutableList<Item> = mutableListOf(),
    private val provisions:MutableList<Item> = mutableListOf(),
    private val dependencies:MutableList<Item> = mutableListOf()
    ){
    fun addUsage(anItem:Item) = uses.add(anItem)
    fun addProvision(anItem:Item) = provisions.add(anItem)
    fun addDependency(anItem:Item) = dependencies.add(anItem)

    override fun toString(): String =
        "$id ${uses.toString(id, "uses")} ${provisions.toString(id, "provisions")} ${dependencies.toString(id, "dependent on")}"

}

fun List<Item>.toString(id:String, title:String) = if (isNotEmpty()) "\n  $id $title: " + joinToString("\n") { "$it" } else ""

data class Acid( override val id:Id = "Acid", var type:String = "", var grade:Int = 0):Item(id)
data class Electricity(override val id:Id = "Electricity", var power:Int):Item(id)

data class Configuration(val items:MutableMap<Id,Item> = mutableMapOf()) {
    fun addItem(item:Item) {
        items[item.id] = item
    }
    operator fun get(id:Id) = items.getValue(id)

    override fun toString(): String {
       return items.values.joinToString("\n") { it.toString() }
    }
}

fun example3point1():Configuration {
    val config = Configuration()
    config.addItem(Item("secure_air_vent"))
    config.addItem(Item("acid_bath"))
    config["acid_bath"].addUsage(Electricity(power=12))
    val acid = Acid()
    config["acid_bath"].addUsage(acid)
    acid.type = "hcl"
    acid.grade = 5
    config.addItem(Item("camera"))
    config["camera"].addUsage(Electricity(power = 1))
    config.addItem(Item("small_power_plant"))
    config["small_power_plant"].addProvision(Electricity(power=11))
    config["small_power_plant"].addDependency(config["secure_air_vent"])
    return config
}

