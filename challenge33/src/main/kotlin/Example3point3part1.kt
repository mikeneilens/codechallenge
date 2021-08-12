fun example3point3part1() {
    Configuration.item("secure_air_vent")
    Configuration.item("acid_bath")
        .uses(Acid()
            .setType("hcl")
            .setGrade(5))
        .uses(Electricity(power = 12))
    Configuration.item("camera").uses(Electricity(power = 1))
    Configuration.item("small_power_plant")
        .provides(Electricity(power = 11))
        .dependsOn("secure_air_vent")
}

fun Configuration.Companion.item(id:Id):Item {
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
    val item = Configuration[id]
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

