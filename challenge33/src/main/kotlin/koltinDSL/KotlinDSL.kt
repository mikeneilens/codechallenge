package koltinDSL
import model.*
import model.Configuration

fun kotlinDSL(): Configuration =
    ConfigBuilder.build {

        addItem("secure_air_vent")
        addItem("acid_bath") {
            usesAcid(){
                withType("hcl")
                withGrade(5)
            }
            uses(Electricity(12))
        }
        addItem("camera") {
            uses(Electricity(1))
        }
        addItem("small_power_plant") {
            provides(Electricity(11))
            dependsOn("secure_air_vent")
        }
    }


class ConfigBuilder {
    var config = Configuration()

    fun addItem(id:Id) {
        val item = Item(id)
        config.addItem(item)
    }
    fun addItem(id:Id, init:ItemBuilder.()->Unit) {
        val item = Item(id)
        config.addItem(item)
        ItemBuilder(item).apply(init)
    }

    companion object {
        fun build(init:ConfigBuilder.()->Unit): Configuration {
            val configBuilder = ConfigBuilder()
            init(configBuilder)
            return configBuilder.config
        }

    }
}

class ItemBuilder(private val item:Item) {
    fun uses(anItem:Item) {
        this.item.addUsage(anItem)
    }
    fun dependsOn(id:Id) {
        val anItem = Item(id)
        this.item.addDependency(anItem)
    }
    fun provides(anItem:Item) {
        this.item.addProvision(anItem)
    }
    fun usesAcid(acidBuilder:AcidBuilder.()->Unit) {
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
