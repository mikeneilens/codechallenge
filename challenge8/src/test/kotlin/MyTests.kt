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
        assertEquals("Magna Park", warehouse.name)
        assertEquals(GBP(100), warehouse.purchasePrice)
        assertEquals(GBP(20), warehouse.rent)
    }
    @Test
    fun `retail location test`(){
        val shop = Location.RetailSite(Group.Red,"Victoria", GBP(100),
                            undeveloped = DevelopmentType.RentOnly(GBP(10)),
                            miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
                            supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
                            megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
                            )

        assertTrue(shop is Location.RetailSite)
        assertEquals("Victoria", shop.name)
        assertEquals (GBP(10), shop.undeveloped.rent)
        assertEquals (GBP(100),shop.miniStore.buildingCost)
        assertEquals (GBP(10),shop.miniStore.rent)
        assertEquals (GBP(200),shop.supermarket.buildingCost)
        assertEquals (GBP(20),shop.supermarket.rent)
        assertEquals (GBP(300),shop.megastore.buildingCost)
        assertEquals (GBP(30),shop.megastore.rent)
        assertEquals (100, shop.purchasePrice.value)
        assertEquals (Group.Red, shop.group)
    }

    @Test
    fun `currency class should alwatys return positive value` () {
        assertEquals(10, GBP(10).value )
        assertEquals(10, GBP(-10).value)
        assertEquals("£500", "${GBP(500)}")
    }
}
