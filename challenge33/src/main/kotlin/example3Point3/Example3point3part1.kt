package example3Point3
import model.*

val configuration = Configuration()

fun example3point3part1(): Configuration {
    configuration.item("secure_air_vent")
    configuration.item("acid_bath")
        .uses(
            Acid()
            .setType("hcl")
            .setGrade(5))
        .uses(Electricity(12))
    configuration.item("camera").uses(Electricity(1))
    configuration.item("small_power_plant")
        .provides(Electricity(11))
        .dependsOn("secure_air_vent")
    return configuration
}

fun Configuration.item(id:Id):Item {
    val item = Item(id)
    addItem(item)
    return item
}
fun Item.uses(item:Item):Item {
    addUsage(item)
    return this
}
fun Item.provides(item:Item):Item {
    addProvision(item)
    return this
}
fun Item.dependsOn(id:Id):Item {
    val item = configuration[id]
    addDependency(item)
    return this
}
fun Acid.setType(type:String):Acid {
    this.type = type
    return this
}
fun Acid.setGrade(grade:Int):Acid {
    this.grade = grade
    return this
}

