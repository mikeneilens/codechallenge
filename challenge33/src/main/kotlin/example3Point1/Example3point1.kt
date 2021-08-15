package example3Point1
import model.Acid
import model.Configuration
import model.Electricity
import model.Item

fun example3point1(): Configuration {
    val configuration = Configuration()
    configuration.addItem(Item("secure_air_vent"))
    configuration.addItem(Item("acid_bath"))
    configuration["acid_bath"].addUsage(Electricity(12))
    val acid = Acid()
    configuration["acid_bath"].addUsage(acid)
    acid.type = "hcl"
    acid.grade = 5
    configuration.addItem(Item("camera"))
    configuration["camera"].addUsage(Electricity(1))
    configuration.addItem(Item("small_power_plant"))
    configuration["small_power_plant"].addProvision(Electricity(power=11))
    configuration["small_power_plant"].addDependency(configuration["secure_air_vent"])
    return configuration
}

