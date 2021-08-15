package example3point2
import model.*

val configuration = Configuration()
var currentItem = Item("")
var currentAcid = Acid()

fun example3point2(): Configuration {
    item("secure_air_vent")
    item("acid_bath")
    uses(Acid())
    acidType("hcl")
    acidGrade(5)
    uses(Electricity(power= 12))
    item("camera")
    uses(Electricity(1))
    item("small_power_plant")
    provides(Electricity(11))
    dependsOn("secure_air_vent")
    return configuration
}

fun item(id:Id) {
    currentItem = Item(id)
    configuration.addItem(currentItem)
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
    currentItem.addDependency(configuration[resourceId])
}

fun acidType(type:String) {
    currentAcid.type = type
}

fun acidGrade(grade:Int) {
    currentAcid.grade = grade
}