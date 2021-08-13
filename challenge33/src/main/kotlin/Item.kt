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

    override fun equals(other: Any?) = (other is Item) && (this.id == other.id)
}

data class Acid( override val id:Id = "Acid", var type:String = "", var grade:Int = 0):Item(id)
data class Electricity(override val id:Id = "Electricity", var power:Int):Item(id)

fun List<Item>.toString(id:String, title:String) = if (isNotEmpty()) "\n  $id $title: " + joinToString(",") { "$it" } else ""

