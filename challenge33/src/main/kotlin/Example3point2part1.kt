val config = Configuration()
var currentItem = Item("")
var currentAcid = Acid()

fun example3point2():Configuration {
    item("secure_air_vent")
    item("acid_bath")
    uses(Acid())
    acidType("hcl")
    acidGrade(5)
    uses(Electricity(power= 12))
    item("camera")
    uses(Electricity(power = 1))
    item("small_power_plant")
    provides(Electricity(power = 11))
    dependsOn("secure_air_vent")

    return config
}

fun item(id:Id) {
    currentItem = Item(id)
    config.addItem(currentItem)
}

fun uses(resource:Item) {
    currentItem.addUsage(resource)
}

fun uses(acid:Acid) {
    currentAcid = acid
    currentItem.addUsage(currentAcid)
}

fun provides(resource:Item) {
    currentItem.addProvision(resource)
}

fun dependsOn(resourceId:String) {
    currentItem.addDependency(config[resourceId])
}

fun acidType(type:String) {
    currentAcid.type = type
}

fun acidGrade(grade:Int) {
    currentAcid.grade = grade
}