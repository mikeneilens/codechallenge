import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt

class MainTest {

    @Test
    fun `DiscountForAnEAN object creator creates the object correctly from an array of strings`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001", "£126.19")

        assertEquals(DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10001"), 126.19), DiscountsForAnEAN.createObjectOrNull(dataForOneEAN))
    }

    @Test
    fun `DiscountForAnEAN object creator creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001")
        assertNull(DiscountsForAnEAN.createObjectOrNull(dataForOneEAN))
    }

    @Test
    fun `DiscountForAnEAN object creator creates a null if the value for discount is not prefixed with a pound`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001", "126.19")
        assertNull(DiscountsForAnEAN.createObjectOrNull(dataForOneEAN))
    }

    @Test
    fun `DiscountForAnEAN object creator creates a null if the value for discount invalid`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001", "£XX26.19")
        assertNull(DiscountsForAnEAN.createObjectOrNull(dataForOneEAN))
    }

    @Test
    fun `an empty string creates an empty ListOfDiscountsForAnEAN`() {
        val csvData = ""
        val listOfDiscountForAnEAN = csvData.toListOfObjects(DiscountsForAnEAN)
        assertTrue(listOfDiscountForAnEAN.isEmpty())
    }

    @Test
    fun `a string containing CSV data for one DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing one element`() {
        val csvData = "Tomato Soup,E10001,£126.19"
        val listOfDiscountForAnEAN = csvData.toListOfObjects(DiscountsForAnEAN)
        assertTrue(listOfDiscountForAnEAN.size == 1)
        assertEquals(listOf(DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10001"), 126.19)), listOfDiscountForAnEAN)
    }

    @Test
    fun `a string containing CSV data for several DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing several elements`() {
        val csvData = "Tomato Soup,E10001,£126.19,Tomato Soup,E10002,£123.80,Chicken Soup,E10004,£127.70"
        val listOfDiscountForAnEAN = csvData.toListOfObjects(DiscountsForAnEAN)
        val expectedResult = listOf(
            DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10001"), 126.19),
            DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10002"), 123.80),
            DiscountsForAnEAN(Product("Chicken Soup"), EAN("E10004"), 127.70)
        )
        assertEquals(expectedResult, listOfDiscountForAnEAN)
    }

    @Test
    fun `a string containing CSV data for several DiscountForAnEAN including an invalid discount value creates a ListOfDiscountsForAnEAN containing several elements`() {
        val csvData = "Tomato Soup,E10001,£126.19,Tomato Soup,E10002,123.80,Chicken Soup,E10004,£127.70"
        val listOfDiscountForAnEAN = csvData.toListOfObjects(DiscountsForAnEAN)
        val expectedResult = listOf(
            DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10001"), 126.19),
            DiscountsForAnEAN(Product("Chicken Soup"), EAN("E10004"), 127.70)
        )
        assertEquals(expectedResult, listOfDiscountForAnEAN)
    }

    @Test
    fun `DeliveryToAShop object creator creates the object correctly when given a valid array of strings`() {
        val dataForOneEAN = listOf("Carrot Soup", "E110011", "I118", "72", "Depot-A", "7")

        assertEquals(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E110011"), Item("I118"), 72, Depot("Depot-A"), 7),
            DeliveryToAShop.createObjectOrNull(dataForOneEAN)
        )
    }

    @Test
    fun `DeliveryToAShop object creator creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup", "E10001")
        assertNull(DeliveryToAShop.createObjectOrNull(dataForOneEAN))
    }

    @Test
    fun `DeliveryToAShop object creator creates a null if either qty is invalid`() {
        val dataWithInvalidCaseSize = listOf("Carrot Soup", "E110011", "I118", "x72","Depot-A", "7")
        assertNull(DeliveryToAShop.createObjectOrNull(dataWithInvalidCaseSize))

        val dataWithInvalidQtyDelivered = listOf("Carrot Soup", "E110011", "I118", "72", "Depot-A", "xx7")
        assertNull(DeliveryToAShop.createObjectOrNull(dataWithInvalidQtyDelivered))
    }

    @Test
    fun `DeliveryToADepot object creator creates the object correctly when given a valid array of strings`() {
        val dataForOneItem = listOf("Carrot Soup","I118","72","Depot-C","Dodgy Food Inc","766")

        assertEquals(
            DeliveryToADepot(Product("Carrot Soup"),Item("I118"),72, Depot("Depot-C"), Supplier("Dodgy Food Inc"),766),
            DeliveryToADepot.createObjectOrNull(dataForOneItem)
        )
    }
    @Test
    fun `DeliveryToADepot object creator creates a null if the data contains wrong number of elements`() {
        val dataForOneItem = listOf("Carrot Soup","I118","72", "Depot-C")
        assertNull(DeliveryToADepot.createObjectOrNull(dataForOneItem))
    }
    @Test
    fun `DeliveryToADepot object creator creates a null if either qty is invalid`() {
        val dataWithInvalidCaseSize = listOf("Carrot Soup","I118","xx72", "Depot-C","Dodgy Food Inc","766")
        assertNull(DeliveryToADepot.createObjectOrNull(dataWithInvalidCaseSize))

        val dataWithInvalidQtyDelivered = listOf("Carrot Soup","I118","72", "Depot-C","Dodgy Food Inc","xx766")
        assertNull(DeliveryToADepot.createObjectOrNull(dataWithInvalidQtyDelivered))
    }

    @Test
    fun `A single EAN discounted, a single delivery to the shop for the same EAN and a single delivery to depot for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val deliveriesToAShop = listOf(DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I119"), 36, Depot("Depot-A"), 12))
        val deliveriesToDepots = listOf(DeliveryToADepot(Product("Carrot Soup"), Item("I119"),72, Depot("Depot-A"), Supplier("Dodgy Food Inc"),766))

        val result = EANRebateCalculator(EAN("E10001"),deliveriesToAShop,deliveriesToDepots)
        val expectedResult = listOf(SupplierRebatePercent(Supplier("Dodgy Food Inc"),1.0))
        assertEquals(expectedResult, result.suppliers)

    }
    @Test
    fun `A single EAN discounted, two delivery to the shop for the same EAN and a single delivery to depot for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val deliveriesToAShop = listOf(
                                                                DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I119"), 36, Depot("Depot-A"), 12),  //50%
                                                                DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I119"), 36, Depot("Depot-A"), 12))  //50%

        val deliveriesToDepots = listOf(DeliveryToADepot(Product("Carrot Soup"), Item("I119"),72, Depot("Depot-A"), Supplier("Dodgy Food Inc"),766))

        val result = EANRebateCalculator(EAN("E10001"),deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.5),
                                                            SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.5))
        assertEquals(expectedResult, result.suppliers)
    }

    @Test
    fun `A single EAN discounted, one delivery to the shop for the same EAN and two deliveries to the depot for the same DepotItem that delivered to the shop gives 50% to each supplier`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I119"), 36, Depot("Depot-A"), 12))

        val deliveriesToDepots = listOf(
            DeliveryToADepot(Product("Carrot Soup"), Item("I119"),72,Depot("Depot-A"), Supplier("Dodgy Food Inc"),766), //50%
            DeliveryToADepot(Product("Carrot Soup"), Item("I119"),72,Depot("Depot-A"), Supplier("Mary's Farm"),766)) //50%

        val result = EANRebateCalculator(EAN("E10001"),deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.5),
                                                            SupplierRebatePercent(Supplier("Mary's Farm"),0.5))
        assertEquals(expectedResult, result.suppliers)

    }

    @Test
    fun `A single EAN discounted, two deliveries to the shop from different depots for the same EAN and one delivery to each depot from one supplier for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I119"), 36, Depot("Depot-A"), 12), //50%
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I119"), 36, Depot("Depot-B"), 12)) //50%

        val deliveriesToDepots = listOf(
            DeliveryToADepot(Product("Carrot Soup"), Item("I119"),72,Depot("Depot-A"), Supplier("Dodgy Food Inc"),766), //100% for the depot
            DeliveryToADepot(Product("Carrot Soup"), Item("I119"),72,Depot("Depot-B"), Supplier("Dodgy Food Inc"),766)) //100% for the depot

        val result = EANRebateCalculator(EAN("E10001"),deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.5),
                                                              SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.5))
        assertEquals(expectedResult, result.suppliers)

    }
    @Test
    fun `A single EAN discounted, two deliveries to the shop, 2 different depots delivering different amounts with two suppliers supplying each depot with different amounts `() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I118"), 36, Depot("Depot-A"), 10), //25%
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I118"), 36, Depot("Depot-B"), 30)) //75%

        val deliveriesToDepots = listOf(
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Dodgy Food Inc"),40), //40% or 10% of total
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Mary's Farm"),60), //60% or 15% or total
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Dodgy Food Inc"),40), //20% or 15% of total
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Mary's Farm"),160)) //80% or 60% of total

        val result = EANRebateCalculator(EAN("E10001"), deliveriesToAShop,deliveriesToDepots)
        val expectedResult =  listOf(  SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.1),
                                                                SupplierRebatePercent(Supplier("Mary's Farm"),0.15),
                                                                SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.15),
                                                                SupplierRebatePercent(Supplier("Mary's Farm"),0.6))
        assertEquals(expectedResult, result.suppliers)

        val expectedResultGrouped = listOf(SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.25),
                                                                    SupplierRebatePercent(Supplier("Mary's Farm"),0.75))
        assertEquals(expectedResultGrouped, result.suppliersGrouped)

    }
    @Test
    fun `A result of calculating the EAN that involves to suppliers at 2 depots is aggregated correctly`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I118"), 36, Depot("Depot-A"), 10), //25%
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I118"), 36, Depot("Depot-B"), 30)) //75%

        val deliveriesToDepots = listOf(
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Dodgy Food Inc"),40), //40% or 10% of total
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Mary's Farm"),60), //60% or 15% or total
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Dodgy Food Inc"),40), //20% or 15% of total
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Mary's Farm"),160)) //80% or 60% of total

        val result = EANRebateCalculator(EAN("E10001"), deliveriesToAShop,deliveriesToDepots)
        val expectedResult = listOf(   SupplierRebatePercent(Supplier("Dodgy Food Inc"),0.25),
                                                                SupplierRebatePercent(Supplier("Mary's Farm"),0.75))
        assertEquals(expectedResult, result.suppliersGrouped)

    }

    @Test
    fun `rebates are calculated correctly for a list of EANs when there is a single supplier for each EAN`() {
        val discountsForEANs = listOf(
             DiscountsForAnEAN(Product("Carrot Soup"), EAN("E10001"),100.0 )
            ,DiscountsForAnEAN(Product("Carrot Soup"), EAN("E10002"),200.0 )
        )

        val deliveriesToAShop = listOf(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I118"), 36, Depot("Depot-A"), 10),
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10002"), Item("I118"), 36, Depot("Depot-B"), 30))

        val deliveriesToDepots = listOf(
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Dodgy Food Inc"),40),
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Mary's Farm"),60)
        )
        val result = calculateRebatesForEANs(discountsForEANs,deliveriesToAShop, deliveriesToDepots)
        val expectedResult = listOf(
            RebateForAnEAN(Product("Carrot Soup"), EAN("E10001"), Supplier("Dodgy Food Inc"),1.0,50.0 ),
            RebateForAnEAN(Product("Carrot Soup"), EAN("E10002"), Supplier("Mary's Farm"),1.0,100.0 )
        )
        assertEquals(expectedResult, result)
    }
    @Test
    fun `rebates are calculated correctly for a list of EANs when there is more than one supplier for each EAN`() {
        val discountsForEANs = listOf(
            DiscountsForAnEAN(Product("Carrot Soup"), EAN("E10001"),100.0 )
            ,DiscountsForAnEAN(Product("Carrot Soup"), EAN("E10002"),200.0 )
        )

        val deliveriesToAShop = listOf(
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10001"), Item("I118"), 36, Depot("Depot-A"), 10),
            DeliveryToAShop(Product("Carrot Soup"), EAN("E10002"), Item("I118"), 36, Depot("Depot-B"), 30))

        val deliveriesToDepots = listOf(
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Dodgy Food Inc"),40), //25%
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-A"), Supplier("Mary's Farm"),120), //75%
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Mary's Farm"),60), //50%
            DeliveryToADepot(Product("Carrot Soup"), Item("I118"),72,Depot("Depot-B"), Supplier("Dodgy Food Inc"),60) //50%
        )
        val result = calculateRebatesForEANs(discountsForEANs,deliveriesToAShop, deliveriesToDepots)
        val expectedResult = listOf(
            RebateForAnEAN(Product("Carrot Soup"), EAN("E10001"), Supplier("Dodgy Food Inc"),0.25,12.5 ),
            RebateForAnEAN(Product("Carrot Soup"), EAN("E10001"), Supplier("Mary's Farm"),0.75,37.5 ),
            RebateForAnEAN(Product("Carrot Soup"), EAN("E10002"), Supplier("Mary's Farm"),0.5,50.0 ),
            RebateForAnEAN(Product("Carrot Soup"), EAN("E10002"), Supplier("Dodgy Food Inc"),0.5,50.0 )
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Rebate is calculated correctly for single EAN, single delivery to shop and single delivery to depot on CSV files`() {
        val dataForOneEAN = "Tomato Soup,E10001,£126.19"
        val dataForOneDeliveryToShop = "Tomato Soup,E10001,I118,72,Depot-A,7"
        val dataForOneDeliveryToADepot = "Tomato Soup,I118,72,Depot-A,Dodgy Food Inc,766"

        val result = calculateRebate(dataForOneEAN, dataForOneDeliveryToShop, dataForOneDeliveryToADepot)
        val expectedResult = listOf(RebateForAProduct(Product("Tomato Soup"), Supplier("Dodgy Food Inc"),63.095))
        assertEquals(expectedResult, result)
    }

    @Test
    fun `rebates for EANs are summed up correctly `() {
        val rebatesForEANs = listOf(
            RebateForAnEAN(Product("Tomato Soup"),EAN("E10001"), Supplier("Dodgy Food Inc"),0.6,60.0),
            RebateForAnEAN(Product("Tomato Soup"),EAN("E10002"), Supplier("Dodgy Food Inc"),0.3,30.0),
            RebateForAnEAN(Product("Chicken Soup"),EAN("E10004"), Supplier("Mary's Farm"),0.25,130.0),
            RebateForAnEAN(Product("Tomato Soup"),EAN("E10003"), Supplier("Dodgy Food Inc"),0.1,10.0))
        val result = rebatesForEANs.sumByProduct()
        val expectedResult = listOf(
            RebateForAProduct(Product("Tomato Soup"), Supplier("Dodgy Food Inc"), 100.0),
            RebateForAProduct(Product("Chicken Soup"), Supplier("Mary's Farm"), 130.0)
        )
        assertEquals(expectedResult, result)
    }
    @Test
    fun `Rebate is calculated correctly using full set of test data`() {

        val discountedEANs = exampleDiscountsForEachEAN.toListOfObjects(DiscountsForAnEAN)

        val result = calculateRebate(exampleDiscountsForEachEAN, exampleDeliveriesToAShop, exampleDeliveriesToDepots)
        val discountForProducts = discountedEANs.groupingBy { it.product }
            .aggregate { _, accumulator: Double?, element, _ -> (accumulator ?: 0.0) + element.discountValue }

        for (discountedProduct in discountForProducts) {
            val rebateForProduct = result.filter { it.product == discountedProduct.key }
                .fold(0.0) { acc, element -> element.rebateAmount + acc }
            //test total of all rebates adds up to 50% of discounts to customers
            assertEquals((discountedProduct.value / 2).roundToInt(), rebateForProduct.roundToInt())
        }
    }

    @Test
    fun `function that calculates discounts for each EAN in a promotion returns zero if the input list is empty`() {
        val salesTransaction = emptyList<EANSold>()

        val result = calculateDiscountForEachEAN(salesTransaction)
        assertEquals(emptyList<DiscountsForAnEAN>(), result)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of no EAN if the input list contains two EAN`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,1 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.20,1 )
        val salesTransaction = listOf(EAN1, EAN2)

        val result = calculateDiscountForEachEAN(salesTransaction)
        val expectedResult = emptyList<DiscountsForAnEAN>()

        assertEquals(expectedResult, result)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of one EAN if the input list contains three EANs`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,1 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.20,1 )
        val EAN3 = EANSold(EAN("E10003"),Product("Tomato Soup"),1.20,1 )
        val salesTransaction = listOf(EAN1,EAN2,EAN3)

        val result = calculateDiscountForEachEAN(salesTransaction)
        val expectedResult = listOf(DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10003"), 0.6))

        assertEquals(expectedResult, result)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of one EAN if the input list contains three EANs, with cheapest not last`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,1 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.00,1 )
        val EAN3 = EANSold(EAN("E10003"),Product("Tomato Soup"),1.20,1 )
        val salesTransaction = listOf(EAN1,EAN2,EAN3)

        val result = calculateDiscountForEachEAN(salesTransaction)
        val expectedResult = listOf(DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10002"), 0.5))

        assertEquals(expectedResult, result)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of one EAN if the input list contains five EANs, with cheapest not last`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,1 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.00,1 )
        val EAN3 = EANSold(EAN("E10003"),Product("Tomato Soup"),1.20,1 )
        val EAN4 = EANSold(EAN("E10004"),Product("Tomato Soup"),1.20,1 )
        val EAN5 = EANSold(EAN("E10005"),Product("Tomato Soup"),1.20,1 )
        val salesTransaction = listOf(EAN1,EAN2,EAN3,EAN4,EAN5)

        val result = calculateDiscountForEachEAN(salesTransaction)
        assertEquals(0.6 , result[0].discountValue)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of two EAN if the input list contains six EANs, with cheapest not last`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,1 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.00,1 )
        val EAN3 = EANSold(EAN("E10003"),Product("Tomato Soup"),0.90,1 )
        val EAN4 = EANSold(EAN("E10004"),Product("Tomato Soup"),1.20,1 )
        val EAN5 = EANSold(EAN("E10005"),Product("Tomato Soup"),1.20,1 )
        val EAN6 = EANSold(EAN("E10006"),Product("Tomato Soup"),1.20,1 )
        val salesTransaction = listOf(EAN1,EAN2,EAN3,EAN4,EAN5,EAN6)

        val result = calculateDiscountForEachEAN(salesTransaction)
        assertEquals(0.60 , result[0].discountValue)
        assertEquals(0.45 , result[1].discountValue)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of two EAN if the input list contains seven EANs, with cheapest not last`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,1 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.00,1 )
        val EAN3 = EANSold(EAN("E10003"),Product("Tomato Soup"),0.90,1 )
        val EAN4 = EANSold(EAN("E10004"),Product("Tomato Soup"),1.20,1 )
        val EAN5 = EANSold(EAN("E10005"),Product("Tomato Soup"),1.20,1 )
        val EAN6 = EANSold(EAN("E10006"),Product("Tomato Soup"),1.20,1 )
        val EAN7 = EANSold(EAN("E10006"),Product("Tomato Soup"),1.20,1 )
        val salesTransaction = listOf(EAN1,EAN2,EAN3,EAN4,EAN5,EAN6,EAN7)

        val result = calculateDiscountForEachEAN(salesTransaction)
        assertEquals(0.60 , result[0].discountValue)
        assertEquals(0.50 , result[1].discountValue)
    }
    @Test
    fun `function that calculates discounts for each EAN in a promotion returns list of two EAN if the input list contains two EANs, with each qty more than 1`() {
        val EAN1 = EANSold(EAN("E10001"),Product("Tomato Soup"),1.20,3 )
        val EAN2 = EANSold(EAN("E10002"),Product("Tomato Soup"),1.00,4 )
        val salesTransaction = listOf(EAN1,EAN2)

        val result = calculateDiscountForEachEAN(salesTransaction)
        val expectedResult = listOf(DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10001"), 0.6),
                                    DiscountsForAnEAN(Product("Tomato Soup"), EAN("E10002"), 0.5))
        assertEquals(expectedResult , result)
    }
}
