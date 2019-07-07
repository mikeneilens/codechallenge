import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MyTests {
    @Test
    fun `free parking test`() {
        val freeParking = Location.FreeParking
        assertTrue(freeParking is Location.FreeParking)
    }
    @Test
    fun `go test`(){
        val go = Location.Go()
        assertTrue(go is Location.Go)
        assertEquals(GBP(100), go.fee)
    }

    @Test
    fun `warehouse test`(){
        val warehouse = Location.FactoryOrWarehouse("Magna Park")
        assertTrue(warehouse is Location.FactoryOrWarehouse)
        assertEquals(GBP(100), warehouse.purchasePrice)
    }
    @Test
    fun `retail location test`(){
        val shop = Location.RetailSite("Victoria",
                            GBP(100),
                            Development.Undeveloped(GBP(10)),
                            Development.MiniStore(GBP(100),GBP(10)),
                            Development.Supermarket(GBP(200),GBP(20)),
                            Development.Megastore(GBP(300),GBP(30)),
                            Group.Red)

        assertTrue(shop is Location.RetailSite)
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
