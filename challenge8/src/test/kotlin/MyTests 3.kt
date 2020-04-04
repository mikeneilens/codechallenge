import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MyTests {
    @Test
    fun `free parking test`() {
        val freeParking = FreeParking
        assertTrue(freeParking is FreeParking)
    }
    @Test
    fun `go test`(){
        val go = Go
        assertTrue(go is Go)
        assertEquals(GBP(100), go.fee)
    }

    @Test
    fun `warehouse test`(){
        val warehouse = FactoryOrWarehouse("Magna Park")
        assertTrue(warehouse is FactoryOrWarehouse)
        assertTrue(warehouse is Location)
        assertTrue(warehouse is Purchaseable)
        assertEquals("Magna Park", warehouse.name)
        assertEquals(GBP(100), warehouse.purchasePrice)
        assertEquals(GBP(20), warehouse.rent)
    }
    @Test
    fun `retail location test`(){
        val shop =          RetailSite(Group.Red,"Victoria", GBP(100),
                            undeveloped = DevelopmentType.RentOnly(GBP(10)),
                            miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
                            supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
                            megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
                            )

        assertTrue(shop is RetailSite)
        assertTrue(shop is Location)
        assertTrue(shop is Purchaseable)
        assertTrue(shop is Buildable)

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

    @Test
    fun `can make a list of locations of different types`() {
        //This is just to show usage

        val warehouse = FactoryOrWarehouse("Magna Park")
        val shop =          RetailSite(Group.Red,"Victoria", GBP(100),
            undeveloped = DevelopmentType.RentOnly(GBP(10)),
            miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
            supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
            megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
        )

        val listOfLocations = listOf<Location>(Go, FreeParking, warehouse,shop)
        assertTrue(listOfLocations is List<Location>)
        assertTrue(listOfLocations[0] is Go)
        assertTrue(listOfLocations[1] is FreeParking )
        assertTrue(listOfLocations[2] is Purchaseable )
        assertTrue(listOfLocations[3] is Buildable)

    }
}
