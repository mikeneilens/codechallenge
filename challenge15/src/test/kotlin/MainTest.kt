import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt

class MainTest {

    @Test
    fun `createDiscountForAnEAN creates the object correctly from an array of strings`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001", "£126.19")

        assertEquals(DiscountsForAnEAN("Tomato Soup", "E10001", 126.19), createDiscountForEANorNull(dataForOneEAN))
    }

    @Test
    fun `createDiscountForAnEAN creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001")
        assertNull(createDiscountForEANorNull(dataForOneEAN))
    }

    @Test
    fun `createDiscountForAnEAN creates a null if the value for discount is not prefixed with a pound`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001", "126.19")
        assertNull(createDiscountForEANorNull(dataForOneEAN))
    }

    @Test
    fun `createDiscountForAnEAN creates a null if the value for discount invalid`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001", "£XX26.19")
        assertNull(createDiscountForEANorNull(dataForOneEAN))
    }

    @Test
    fun `an empty string creates an empty ListOfDiscountsForAnEAN`() {
        val csvData = ""
        val listOfDiscountForAnEAN = csvData.toListOfObjects(3, createDiscountForEANorNull)
        assertTrue(listOfDiscountForAnEAN.isEmpty())
    }

    @Test
    fun `a string containing CSV data for one DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing one element`() {
        val csvData = "Tomato Soup,E10001,£126.19"
        val listOfDiscountForAnEAN = csvData.toListOfObjects(3, createDiscountForEANorNull)
        assertTrue(listOfDiscountForAnEAN.size == 1)
        assertEquals(listOf(DiscountsForAnEAN("Tomato Soup", "E10001", 126.19)), listOfDiscountForAnEAN)
    }

    @Test
    fun `a string containing CSV data for several DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing several elements`() {
        val csvData = "Tomato Soup,E10001,£126.19,Tomato Soup,E10002,£123.80,Chicken Soup,E10004,£127.70"
        val listOfDiscountForAnEAN = csvData.toListOfObjects(3, createDiscountForEANorNull)
        val expectedResult = listOf(
            DiscountsForAnEAN("Tomato Soup", "E10001", 126.19),
            DiscountsForAnEAN("Tomato Soup", "E10002", 123.80),
            DiscountsForAnEAN("Chicken Soup", "E10004", 127.70)
        )
        assertEquals(expectedResult, listOfDiscountForAnEAN)
    }

    @Test
    fun `a string containing CSV data for several DiscountForAnEAN including an invalid discount value creates a ListOfDiscountsForAnEAN containing several elements`() {
        val csvData = "Tomato Soup,E10001,£126.19,Tomato Soup,E10002,123.80,Chicken Soup,E10004,£127.70"
        val listOfDiscountForAnEAN = csvData.toListOfObjects(3, createDiscountForEANorNull)
        val expectedResult = listOf(
            DiscountsForAnEAN("Tomato Soup", "E10001", 126.19),
            DiscountsForAnEAN("Chicken Soup", "E10004", 127.70)
        )
        assertEquals(expectedResult, listOfDiscountForAnEAN)
    }

    @Test
    fun `createDeliveryToAShop creates the object correctly when given a valid array of strings`() {
        val dataForOneEAN = listOf("Carrot Soup", "E110011", "I118", "72", "Depot-A", "7")

        assertEquals(
            DeliveryToAShop("Carrot Soup", "E110011", "I118", 72, "Depot-A", 7),
            createDeliveryToAShopOrNull(dataForOneEAN)
        )
    }

    @Test
    fun `createDeliveryToAShop creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001")
        assertNull(createDeliveryToAShopOrNull(dataForOneEAN))
    }

    @Test
    fun `createDeliveryToAShop creates a null if either qty is invalid`() {
        val dataWithInvalidCaseSize = listOf("Carrot Soup", "E110011", "I118", "x72", "Depot-A", "7")
        assertNull(createDeliveryToAShopOrNull(dataWithInvalidCaseSize))

        val dataWithInvalidQtyDelivered = listOf("Carrot Soup", "E110011", "I118", "72", "Depot-A", "xx7")
        assertNull(createDeliveryToAShopOrNull(dataWithInvalidQtyDelivered))
    }

    @Test
    fun `createDeliveryToADepot creates the object correctly when given a valid array of strings`() {
        val dataForOneItem = listOf("Carrot Soup","I118","72","Depot-C","Dodgy Food Inc","766")

        assertEquals(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-C","Dodgy Food Inc",766),
            createDeliveryToADepotOrNull(dataForOneItem)
        )
    }
    @Test
    fun `createDeliveryToADepot creates a null if the data contains wrong number of elements`() {
        val dataForOneItem = listOf("Carrot Soup","I118","72","Depot-C")
        assertNull(createDeliveryToADepotOrNull(dataForOneItem))
    }
    @Test
    fun `createDeliveryToADepot creates a null if either qty is invalid`() {
        val dataWithInvalidCaseSize = listOf("Carrot Soup","I118","xx72","Depot-C","Dodgy Food Inc","766")
        assertNull(createDeliveryToADepotOrNull(dataWithInvalidCaseSize))

        val dataWithInvalidQtyDelivered = listOf("Carrot Soup","I118","72","Depot-C","Dodgy Food Inc","xx766")
        assertNull(createDeliveryToADepotOrNull(dataWithInvalidQtyDelivered))
    }

    @Test
    fun `A single EAN discounted, a single delviery to the shop for the same EAN and a single delivery to depot for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val deliveriesToAShop = listOf(DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-A", 12))
        val deliveriesToDepots = listOf(DeliveryToADepot("Carrot Soup","I119",72,"Depot-A","Dodgy Food Inc",766))

        val result = EANRebateCalculator("E10001",deliveriesToAShop,deliveriesToDepots)
        val expectedResult = listOf(SupplierRebatePercent("Dodgy Food Inc",1.0))
        assertEquals(expectedResult, result.suppliers)

    }
    @Test
    fun `A single EAN discounted, two delviery to the shop for the same EAN and a single delivery to depot for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val deliveriesToAShop = listOf(
                                                                DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-A", 12),  //50%
                                                                DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-A", 12))  //50%

        val deliveriesToDepots = listOf(DeliveryToADepot("Carrot Soup","I119",72,"Depot-A","Dodgy Food Inc",766))

        val result = EANRebateCalculator("E10001",deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(SupplierRebatePercent("Dodgy Food Inc",0.5),
                                                            SupplierRebatePercent("Dodgy Food Inc",0.5))
        assertEquals(expectedResult, result.suppliers)
    }

    @Test
    fun `A single EAN discounted, one delviery to the shop for the same EAN and two deliveries to the depot for the same DepotItem that delivered to the shop gives 50% to each supplier`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-A", 12))

        val deliveriesToDepots = listOf(
            DeliveryToADepot("Carrot Soup","I119",72,"Depot-A","Dodgy Food Inc",766), //50%
            DeliveryToADepot("Carrot Soup","I119",72,"Depot-A","Mary's Farm",766)) //50%

        val result = EANRebateCalculator("E10001",deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(SupplierRebatePercent("Dodgy Food Inc",0.5),
                                                            SupplierRebatePercent("Mary's Farm",0.5))
        assertEquals(expectedResult, result.suppliers)

    }

    @Test
    fun `A single EAN discounted, two delvieries to the shop from different depots for the same EAN and one delivery to each depot from one supplier for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-A", 12), //50%
            DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-B", 12)) //50%

        val deliveriesToDepots = listOf(
            DeliveryToADepot("Carrot Soup","I119",72,"Depot-A","Dodgy Food Inc",766), //100% for the depot
            DeliveryToADepot("Carrot Soup","I119",72,"Depot-B","Dodgy Food Inc",766)) //100% for the depot

        val result = EANRebateCalculator("E10001",deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(SupplierRebatePercent("Dodgy Food Inc",0.5),
                                                              SupplierRebatePercent("Dodgy Food Inc",0.5))
        assertEquals(expectedResult, result.suppliers)

    }
    @Test
    fun `A single EAN discounted, two delvieries to the shop, 2 different depots delivering different amounts with two suppliers supplying each depot with different amounts `() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E10001", "I118", 36, "Depot-A", 10), //25%
            DeliveryToAShop("Carrot Soup", "E10001", "I118", 36, "Depot-B", 30)) //75%

        val deliveriesToDepots = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Dodgy Food Inc",40), //40% or 10% of total
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Mary's Farm",60), //60% or 15% or total
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Dodgy Food Inc",40), //20% or 15% of total
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Mary's Farm",160)) //80% or 60% of total

        val result = EANRebateCalculator("E10001", deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(  SupplierRebatePercent("Dodgy Food Inc",0.1),
                                                                SupplierRebatePercent("Mary's Farm",0.15),
                                                                SupplierRebatePercent("Dodgy Food Inc",0.15),
                                                                SupplierRebatePercent("Mary's Farm",0.6))
        assertEquals(expectedResult, result.suppliers)

        val expectedResultGrouped = listOf(SupplierRebatePercent("Dodgy Food Inc",0.25),
                                                                    SupplierRebatePercent("Mary's Farm",0.75))
        assertEquals(expectedResultGrouped, result.suppliersGrouped)

    }
    @Test
    fun `A result of calculating the EAN that involves to suppliers at 2 depots is aggregated correctly`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E10001", "I118", 36, "Depot-A", 10), //25%
            DeliveryToAShop("Carrot Soup", "E10001", "I118", 36, "Depot-B", 30)) //75%

        val deliveriesToDepots = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Dodgy Food Inc",40), //40% or 10% of total
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Mary's Farm",60), //60% or 15% or total
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Dodgy Food Inc",40), //20% or 15% of total
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Mary's Farm",160)) //80% or 60% of total

        val result = EANRebateCalculator("E10001", deliveriesToAShop,deliveriesToDepots)
        val expectedResult = listOf(   SupplierRebatePercent("Dodgy Food Inc",0.25),
                                                                SupplierRebatePercent("Mary's Farm",0.75))
        assertEquals(expectedResult, result.suppliersGrouped)

    }

    @Test
    fun `rebates are calculated correctly for a list of EANs when there is a single supplier for each EAN`() {
        val discountsForEANs = listOf(
             DiscountsForAnEAN("Carrot Soup", "E10001",100.0 )
            ,DiscountsForAnEAN("Carrot Soup", "E10002",200.0 )
        )

        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E10001", "I118", 36, "Depot-A", 10),
            DeliveryToAShop("Carrot Soup", "E10002", "I118", 36, "Depot-B", 30))

        val deliveriesToDepots = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Dodgy Food Inc",40),
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Mary's Farm",60)
        )
        val result = calculateRebatesForEANs(discountsForEANs,deliveriesToAShop, deliveriesToDepots)
        val expectedResult = listOf(
            RebateForAnEAN("Carrot Soup", "E10001","Dodgy Food Inc",1.0,50.0 ),
            RebateForAnEAN("Carrot Soup", "E10002","Mary's Farm",1.0,100.0 )
        )
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rebates are calculated correctly for a list of EANs when there is more than one supplier for each EAN`() {
        val discountsForEANs = listOf(
            DiscountsForAnEAN("Carrot Soup", "E10001",100.0 )
            ,DiscountsForAnEAN("Carrot Soup", "E10002",200.0 )
        )

        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E10001", "I118", 36, "Depot-A", 10),
            DeliveryToAShop("Carrot Soup", "E10002", "I118", 36, "Depot-B", 30))

        val deliveriesToDepots = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Dodgy Food Inc",40), //25%
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-A","Mary's Farm",120), //75%
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Mary's Farm",60), //50%
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-B","Dodgy Food Inc",60) //50%
        )
        val result = calculateRebatesForEANs(discountsForEANs,deliveriesToAShop, deliveriesToDepots)
        val expectedResult = listOf(
            RebateForAnEAN("Carrot Soup", "E10001","Dodgy Food Inc",0.25,12.5 ),
            RebateForAnEAN("Carrot Soup", "E10001","Mary's Farm",0.75,37.5 ),
            RebateForAnEAN("Carrot Soup", "E10002","Mary's Farm",0.5,50.0 ),
            RebateForAnEAN("Carrot Soup", "E10002","Dodgy Food Inc",0.5,50.0 )
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Rebate is calculated correcrtly for single EAN, single delivery to shop and single delivery to depot on CSV files`() {
        val dataForOneEAN = "Tomato Soup,E10001,£126.19"
        val dataForOneDeliveryToShop = "Tomato Soup,E10001,I118,72,Depot-A,7"
        val dataForOneDeliveryToADepot = "Tomato Soup,I118,72,Depot-A,Dodgy Food Inc,766"

        val result = calculateRebate(dataForOneEAN, dataForOneDeliveryToShop, dataForOneDeliveryToADepot)
        val expectedResult = listOf(RebateForAProduct("Tomato Soup","Dodgy Food Inc",63.095))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `rebates for EANs are summed up correctly `() {
        val rebatesForEANs = listOf(
            RebateForAnEAN("Tomato Soup","E10001","Dodgy Food Inc",0.6,60.0),
            RebateForAnEAN("Tomato Soup","E10002","Dodgy Food Inc",0.3,30.0),
            RebateForAnEAN("Chicken Soup","E10004","Mary's Farm",0.25,130.0),
            RebateForAnEAN("Tomato Soup","E10003","Dodgy Food Inc",0.1,10.0))
        val result = rebatesForEANs.sumByProduct()
        val expectedResult = listOf(
            RebateForAProduct("Tomato Soup", "Dodgy Food Inc", 100.0),
            RebateForAProduct("Chicken Soup", "Mary's Farm", 130.0)
        )
        assertEquals(expectedResult, result)
    }
    @Test
    fun `Rebate is calculated correcrtly using full set of test data`() {

        val discountedEANs = exampleDiscountsForEachEAN.toListOfObjects(3, createDiscountForEANorNull)

        val result = calculateRebate(exampleDiscountsForEachEAN, exampleDeliveriesToAShop, exampleDeliveriesToDepots)
        val discountForProducts = discountedEANs.groupingBy { it.product }.aggregate { _, accumulator:Double?, element, _ -> (accumulator ?: 0.0) + element.discountValue  }

        for (discountedProduct in discountForProducts) {
            val rebateForProduct = result.filter { it.product == discountedProduct.key }.fold(0.0){acc, element -> element.rebateAmount + acc}
            //test total of all rebates adds up to 50% of discounts to customers
            assertEquals( (discountedProduct.value/2).roundToInt() , rebateForProduct.roundToInt() )
        }
    }
}
