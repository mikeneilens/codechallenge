import junit.framework.TestCase.assertEquals
import org.junit.Test

class MyTests {
    @Test
    fun `initial test`() {
        val freeParking = Location.FreeParking
        assertEquals(true , freeParking is Location.FreeParking  )

        val go = Location.Go()
        assertEquals(GBP(100), go.fee)

        val warehouse = Location.FactoryOrWarehouse("Magna Park")
        assertEquals(GBP(100), warehouse.purchasePrice)

        val shop = Location.RetailSite("Victoria",
                            GBP(100),
                            Development.Undeveloped(GBP(10)),
                            Development.MiniStore(GBP(100),GBP(10)),
                            Development.Supermarket(GBP(200),GBP(20)),
                            Development.Megastore(GBP(300),GBP(30)),
                            Group.Red)

        assertEquals (GBP(10), shop.undeveloped.rent)
        assertEquals (GBP(200),shop.supermarket.buildingCost)
        assertEquals (100, shop.purchasePrice.value)
        assertEquals (Group.Red, shop.group)
    }

    @Test
    fun `currency class should alwatys return positive value` () {
        assertEquals(10, GBP(10).value )
        assertEquals(10, GBP(-10).value)
    }
}
