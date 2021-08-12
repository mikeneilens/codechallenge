
fun example3point2part2():Configuration {
    item("secure_air_vent")
    item("acid_bath")
    val acid_bath_acid = Acid()
    uses("acid_bath", acid_bath_acid)
    acid_type(acid_bath_acid, "hcl")
    acid_grade(acid_bath_acid, 5)
    uses("acid_bath", Electricity(power = 12))
    item("camera")
    uses("camera", Electricity(power = 1))
    item("small_power_plant")
    provides("small_power_plant", Electricity(power= 11))
    depends("small_power_plant", "secure_air_vent")
    return config
}

fun acid_type(acid: Acid, type: String) {
    acid.type = type
}
fun acid_grade(acid: Acid, grade: Int) {
    acid.grade = grade
}
fun uses(id:Id, item:Item) {
    config[id].addUsage(item)
}
fun provides(id:Id, item:Item) {
    config[id].addProvision(item)
}
fun depends(id:Id, dependency:String) {
    config[id].addDependency(config[dependency])
}
