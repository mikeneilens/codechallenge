
fun example3point1() {
    Configuration.addItem(Item("secure_air_vent"))
    Configuration.addItem(Item("acid_bath"))
    Configuration["acid_bath"].addUsage(Electricity(power = 12))
    val acid = Acid()
    Configuration["acid_bath"].addUsage(acid)
    acid.type = "hcl"
    acid.grade = 5
    Configuration.addItem(Item("camera"))
    Configuration["camera"].addUsage(Electricity(power = 1))
    Configuration.addItem(Item("small_power_plant"))
    Configuration["small_power_plant"].addProvision(Electricity(power=11))
    Configuration["small_power_plant"].addDependency(Configuration["secure_air_vent"])
}

