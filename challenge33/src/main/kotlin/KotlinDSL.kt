
fun kotlinDSL(): Configuration =
    ConfigBuilder.build {

        addItem("secure_air_vent")
        addItem("acid_bath") {
            whichUsesAcid(){
                withType("hcl")
                withGrade(5)
            }
            whichUses(Electricity(power = 12))
        }
        addItem("camera") {
            whichUses(Electricity(power = 1))
        }
        addItem("small_power_plant") {
            whichProvides(Electricity(power = 11))
            whichDependsOn("secure_air_vent")
        }
    }


class ConfigBuilder {
    var config = Configuration()

    fun addItem(id:Id) {
        val item = Item(id)
        config.addItem(item)
    }
    fun addItem(id:Id, init:ItemBuilder2.()->Unit) {
        val item = Item(id)
        config.addItem(item)
        ItemBuilder2(item).apply(init)
    }

    companion object {
        fun build(init:ConfigBuilder.()->Unit): Configuration {
            val configBuilder = ConfigBuilder()
            init(configBuilder)
            return configBuilder.config
        }

    }
}

class ItemBuilder2(private val item:Item) {
    fun whichUses(anItem:Item) {
        this.item.addUsage(anItem)
    }
    fun whichDependsOn(id:Id) {
        val anItem = Item(id)
        this.item.addDependency(anItem)
    }
    fun whichProvides(anItem:Item) {
        this.item.addProvision(anItem)
    }
    fun whichUsesAcid(acidBuilder:AcidBuilder.()->Unit) {
        val acid = Acid()
        this.item.addUsage(acid)
        AcidBuilder(acid).apply(acidBuilder)
    }

}

class AcidBuilder(val acid:Acid) {
    fun withType(type:String) {
        acid.type = type
    }
    fun withGrade(grade:Int) {
        acid.grade = grade
    }
}
