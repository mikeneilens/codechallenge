package example3point2
import model.*

fun example3point2part2(): Configuration {
    item("secure_air_vent")
    item("acid_bath")
    val acidBathAcid = Acid()
    "acid_bath".uses(acidBathAcid)
    acidBathAcid.acidType("hcl")
    acidBathAcid.acidGrade(5)
    "acid_bath".uses(Electricity(12))
    item("camera")
    "camera".uses(Electricity(1))
    item("small_power_plant")
    "small_power_plant".provides(Electricity(power= 11))
    "small_power_plant".depends("secure_air_vent")
    return configuration
}

fun Acid.acidType(type: String) {
    this.type = type
}
fun Acid.acidGrade(grade: Int) {
    this.grade = grade
}
fun Id.uses(item: Item) {
    configuration[this].addUsage(item)
}
fun Id.provides( item:Item) {
    configuration[this].addProvision(item)
}
fun Id.depends(dependency:String) {
    configuration[this].addDependency(configuration[dependency])
}
