import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestExample3point2part1 {
    @Test
    fun `test initial version`() {
        Configuration.reset()
        example3point2()
        println(Configuration)

        val secure_air_vent =  Configuration["secure_air_vent"]
        assertEquals(0, secure_air_vent.uses.size)
        assertEquals(0, secure_air_vent.provisions.size)
        assertEquals(0, secure_air_vent.dependencies.size)

        val acid_bath =  Configuration["acid_bath"]
        assertEquals(2, acid_bath.uses.size)
        assertEquals(true, acid_bath.uses[0] is Acid)

        val acid = acid_bath.uses[0] as Acid
        assertEquals("hcl", acid.type)
        assertEquals(5, acid.grade)

        assertEquals(true, acid_bath.uses[1] is Electricity)
        val acidBathElectricity = acid_bath.uses[1] as Electricity
        assertEquals(12, acidBathElectricity.power)

        val camera =  Configuration["camera"]
        assertEquals(1, camera.uses.size)
        assertEquals(true, camera.uses[0] is Electricity)
        val cameraElectricity = camera.uses[0] as Electricity
        assertEquals(1, cameraElectricity.power)

        val small_power_plant = Configuration["small_power_plant"]
        assertEquals(0, small_power_plant.uses.size)
        assertEquals(1, small_power_plant.provisions.size)
        assertEquals(true, small_power_plant.provisions[0] is Electricity)
        val smallPowerPlantElectricity = small_power_plant.provisions[0] as Electricity
        assertEquals(11, smallPowerPlantElectricity.power )
        assertEquals(1, small_power_plant.dependencies.size)
        assertEquals(secure_air_vent, small_power_plant.dependencies[0])
    }
}