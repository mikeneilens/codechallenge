
fun example3point2part2():Configuration{
    item("secure_air_vent")
    item("acid_bath")
    val acidBathAcid = Acid()
    uses("acid_bath", acidBathAcid)
    acidType(acidBathAcid, "hcl")
    acidGrade(acidBathAcid, 5)
    uses("acid_bath", Electricity(power = 12))
    item("camera")
    uses("camera", Electricity(power = 1))
    item("small_power_plant")
    provides("small_power_plant", Electricity(power= 11))
    depends("small_power_plant", "secure_air_vent")
    return configuration
}

fun acidType(acid: Acid, type: String) {
    acid.type = type
}
fun acidGrade(acid: Acid, grade: Int) {
    acid.grade = grade
}
fun uses(id:Id, item:Item) {
    configuration[id].addUsage(item)
}
fun provides(id:Id, item:Item) {
    configuration[id].addProvision(item)
}
fun depends(id:Id, dependency:String) {
    configuration[id].addDependency(configuration[dependency])
}
