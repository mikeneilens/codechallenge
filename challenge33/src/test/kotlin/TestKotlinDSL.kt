import koltinDSL.kotlinDSL
import model.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestKotlinDSL {
    @Test
    fun `creating a config using configBuilder DSL`() {
        val configuration = kotlinDSL()

        val secure_air_vent =  configuration["secure_air_vent"]
        assertEquals(0, secure_air_vent.uses.size)
        assertEquals(0, secure_air_vent.provisions.size)
        assertEquals(0, secure_air_vent.dependencies.size)

        val acid_bath =  configuration["acid_bath"]
        assertEquals(2, acid_bath.uses.size)

        assertEquals(true, acid_bath.uses[0] is Acid)
        val acid = acid_bath.uses[0] as Acid
        assertEquals("hcl", acid.type)
        assertEquals(5, acid.grade)

        assertEquals(true, acid_bath.uses[1] is Electricity)
        val acidBathElectricity = acid_bath.uses[1] as Electricity
        assertEquals(12, acidBathElectricity.power)
    }

}