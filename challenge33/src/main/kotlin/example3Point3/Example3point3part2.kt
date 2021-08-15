package example3Point3
import model.*

fun example3point3part2(): Configuration {
    val configurationBuilder = ConfigurationBuilder()

    configurationBuilder.item("secure_air_vent")
    configurationBuilder.item("acid_bath")
        .uses(Acid()
            .setType("hcl")
            .setGrade(5))
        .uses(Electricity(12))
    configurationBuilder.item("camera").uses(Electricity(1))
    configurationBuilder.item("small_power_plant")
        .provides(Electricity(11))
        .dependsOn("secure_air_vent")

    return configurationBuilder.configuration
}

class ConfigurationBuilder{
    val configuration = Configuration()

    fun item(id:Id):ItemBuilder {
        val itemBuilder = ItemBuilder(id)
        configuration.addItem(itemBuilder.item)
        return itemBuilder
    }
}

class ItemBuilder(id:Id){
    val item = Item(id)
    fun uses(item:Item):ItemBuilder {
        this.item.addUsage(item)
        return this
    }
    fun provides(item:Item):ItemBuilder {
        this.item.addProvision(item)
        return this
    }
    fun dependsOn(id:String):ItemBuilder {
        this.item.addDependency(Item(id))
        return this
    }
}