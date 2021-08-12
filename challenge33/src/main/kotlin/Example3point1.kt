typealias Id = String

open class Item(
    open val id:Id,
    val uses:MutableList<Item> = mutableListOf(),
    val provisions:MutableList<Item> = mutableListOf(),
    val dependencies:MutableList<Item> = mutableListOf()
    ){
    fun addUsage(anItem:Item) = uses.add(anItem)
    fun addProvision(anItem:Item) = provisions.add(anItem)
    fun addDependency(anItem:Item) = dependencies.add(anItem)

    override fun toString(): String =
        "$id ${uses.toString(id, "uses")} ${provisions.toString(id, "provisions")} ${dependencies.toString(id, "dependent on")}"
}

fun List<Item>.toString(id:String, title:String) = if (isNotEmpty()) "\n  $id $title: " + joinToString(",") { "$it" } else ""

data class Acid( override val id:Id = "Acid", var type:String = "", var grade:Int = 0):Item(id)
data class Electricity(override val id:Id = "Electricity", var power:Int):Item(id)

class Configuration {

    companion object {
        private val items:MutableMap<Id,Item> = mutableMapOf()
        fun addItem(item:Item) {
            items[item.id] = item
        }
        operator fun get(id:Id) = items.getValue(id)
        override fun toString() = items.values.joinToString("\n") { it.toString() }
    }
}

fun example3point1() {
    Configuration.addItem(Item("secure_air_vent"))
    Configuration.addItem(Item("acid_bath"))
    Configuration["acid_bath"].addUsage(Electricity(power = 12))
    val acid = Acid()
    Configuration["acid_bath"].addUsage(acid)
    acid.type = "hcl"
    acid.grade = 5
    Configuration.addItem(Item("camera"))
    Configuration["camera"].addUsage(Electricity(power = 1))
    Configuration.addItem(Item("small_power_plant"))
    Configuration["small_power_plant"].addProvision(Electricity(power=11))
    Configuration["small_power_plant"].addDependency(Configuration["secure_air_vent"])
}

