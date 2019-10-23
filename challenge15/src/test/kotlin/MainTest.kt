import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
    fun `summing total deliveries of each EAN to a shop creates an empty map when there are no deliveries to a shop`() {
        val deliveriesToAShop = emptyList<DeliveryToAShop>()
        assertTrue(calculateTotalDeliveryForEachKey(deliveriesToAShop).isEmpty())
    }
    @Test
    fun `summing total deliveries of each EAN to a shop creates a map of one KeyValue when there is one deliveries to a shop`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E110011", "I118", 72, "Depot-A", 7)
        )
        assertEquals(mapOf("E110011" to (72 * 7) ),calculateTotalDeliveryForEachKey(deliveriesToAShop))
    }
    @Test
    fun `summing total deliveries of each EAN to a shop creates a map of one KeyValue when there is two deliveries with the same EAN`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E110011", "I118", 72, "Depot-A", 7) ,
            DeliveryToAShop("Carrot Soup", "E110011", "I119", 36, "Depot-A", 12)
        )
        assertEquals(mapOf("E110011" to ((72* 7) + (36 * 12)) ),calculateTotalDeliveryForEachKey(deliveriesToAShop))
    }
    @Test
    fun `summing total deliveries of each EAN to a shop creates a map of one KeyValue when there is three deliveries with two EANs`() {
        val deliveriesToAShop = listOf(
            DeliveryToAShop("Carrot Soup", "E110011", "I119", 36, "Depot-A", 12) ,
            DeliveryToAShop("Chicken Soup","E110004", "I105", 96, "Depot-B", 6),
            DeliveryToAShop("Carrot Soup", "E110011", "I118", 72, "Depot-A", 7)
        )
        val expectedResult = mapOf(
            "E110011" to ((72* 7) + (36 * 12)),
            "E110004" to (96* 6))

        assertEquals(expectedResult,calculateTotalDeliveryForEachKey(deliveriesToAShop))
    }

    @Test
    fun `summing total deliveries of each item  to each depot creates an empty map if the list of deliveries is empty`() {
        val deliveriesToEachDepot = emptyList<DeliveryToADepot>()

        assertTrue(calculateTotalDeliveryForEachKey(deliveriesToEachDepot).isEmpty())
    }

    fun `summing total deliveries of each item to each depot creates a map containing one item if the list of deliveries contains one DepotItem `() {
        val deliveriesToEachDepot = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-C","Dodgy Food Inc",766)
        )
        val expectedResult = mapOf("Depot-C I118" to (72 * 766))

        assertEquals( expectedResult, calculateTotalDeliveryForEachKey(deliveriesToEachDepot))
    }
    @Test
    fun `summing total deliveries of each item to each depot creates a map containing one item if the list of deliveries contains two DepotItem that are the same `() {
        val deliveriesToEachDepot = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-C","Dodgy Food Inc",766),
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-C","Mary's Farm",97)
        )
        val expectedResult = mapOf("Depot-C I118" to ((72 * 766)+(72 * 97)))

        assertEquals( expectedResult, calculateTotalDeliveryForEachKey(deliveriesToEachDepot))
    }
    @Test
    fun `summing total deliveries of each item to each depot creates a map containing one item if the list of deliveries contains three DepotItem that are different `() {
        val deliveriesToEachDepot = listOf(
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-C","Dodgy Food Inc",766),
            DeliveryToADepot("Carrot Soup","I118",72,"Depot-C","Mary's Farm",97),
            DeliveryToADepot("Chicken Soup","I106",96,"Depot-A","Dodgy Food Inc",171)
        )
        val expectedResult = mapOf("Depot-C I118" to ((72 * 766)+(72 * 97)), "Depot-A I106" to (96 * 171))

        assertEquals( expectedResult, calculateTotalDeliveryForEachKey(deliveriesToEachDepot))
    }

    @Test
    fun `A single EAN discounted, a single delviery to the shop for the same EAN and a single delivery to depot for the same DepotItem that delivered to the shop gives 100% to the supplier`() {
        val discountsForAnEAN = listOf(DiscountsForAnEAN("Carrot Soup", "E10001", 126.19))
        val deliveriesToAShop = listOf(DeliveryToAShop("Carrot Soup", "E10001", "I119", 36, "Depot-A", 12))
        val deliveriesToDepots = listOf(DeliveryToADepot("Carrot Soup","I119",72,"Depot-A","Dodgy Food Inc",766))

        val expectedResult = Result("Carrot Soup","E10001","I119","Depot-A","Dodgy Food Inc", 432,55152,432,55152)
        val result= calculateResult(discountsForAnEAN, deliveriesToAShop, deliveriesToDepots)
        assertEquals(expectedResult, result.first())
    }
}
