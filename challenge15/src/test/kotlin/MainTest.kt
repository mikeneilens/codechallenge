import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `createDiscountForAnEAN creates the object correctly from an array of strings`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001","£126.19")

        assertEquals(DiscountForAnEAN("Tomato Soup", "E10001",126.19), createDiscountForEANorNull(dataForOneEAN))
    }
    @Test
    fun `createDiscountForAnEAN creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001")
        assertNull( createDiscountForEANorNull(dataForOneEAN))
    }
    @Test
    fun `createDiscountForAnEAN creates a null if the value for discount is not prefixed with a pound`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001","126.19")
        assertNull( createDiscountForEANorNull(dataForOneEAN))
    }

    @Test
    fun `an empty string creates an empty ListOfDiscountsForAnEAN`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("")
        assertTrue(listOfDiscountForAnEAN.isEmpty())
    }

    @Test
    fun `a string containing CSV data for one DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing one element`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("Tomato Soup,E10001,£126.19")
        assertTrue(listOfDiscountForAnEAN.size == 1)
        assertEquals(listOf(DiscountForAnEAN("Tomato Soup", "E10001",126.19)), listOfDiscountForAnEAN )
    }
    @Test
    fun `a string containing CSV data for several DiscountForAnEAN creates a ListOfDiscountsForAnEAN containing several elements`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("Tomato Soup,E10001,£126.19,Tomato Soup,E10002,£123.80,Chicken Soup,E10004,£127.70")
        val expectedResult = listOf (
            DiscountForAnEAN("Tomato Soup","E10001",126.19),
            DiscountForAnEAN("Tomato Soup","E10002",123.80),
            DiscountForAnEAN("Chicken Soup","E10004",127.70))
        assertEquals(expectedResult, listOfDiscountForAnEAN )
    }
    @Test
    fun `a string containing CSV data for several DiscountForAnEAN including an invalid discount value creates a ListOfDiscountsForAnEAN containing several elements`() {
        val listOfDiscountForAnEAN = createListOfDiscountsForAnEAN("Tomato Soup,E10001,£126.19,Tomato Soup,E10002,123.80,Chicken Soup,E10004,£127.70")
        val expectedResult = listOf (
            DiscountForAnEAN("Tomato Soup","E10001",126.19),
            DiscountForAnEAN("Chicken Soup","E10004",127.70))
        assertEquals(expectedResult, listOfDiscountForAnEAN )
    }
    
    @Test
    fun `createDeliveryToAShop creates the object correctly when given a valid array of strings`() {
        val dataForOneEAN = listOf("Carrot Soup","E110011","I118","72","Depot-A","7")
        
        assertEquals(DeliveryToAShop("Carrot Soup","E110011","I118",72,"Depot-A",7), createDeliveryToAShopOrNull(dataForOneEAN))
    }

    @Test
    fun `createDeliveryToAShop creates a null if the data contains wrong number of elements`() {
        val dataForOneEAN = listOf("Tomato Soup","E10001")
        assertNull( createDeliveryToAShopOrNull(dataForOneEAN))
    }
    @Test
    fun `createDeliveryToAShop creates a null if either qty is invalid`() {
        val dataWithInvalidCaseSize = listOf("Carrot Soup","E110011","I118","x72","Depot-A","7")
        assertNull( createDiscountForEANorNull(dataWithInvalidCaseSize))

        val dataWithInvalidQtyDelivered = listOf("Carrot Soup","E110011","I118","72","Depot-A","xx7")
        assertNull( createDiscountForEANorNull(dataWithInvalidQtyDelivered))
    }

    @Test
    fun `an empty string creates an empty ListOfDeliveryToAShop`() {
        val listOfDeliveryToAShop = createListOfDeliveryToShop("")
        assertTrue(listOfDeliveryToAShop.isEmpty())
    }

    @Test
    fun `a string containing CSV data for one DeliveryToAShop creates a ListOfDeliveryToAShop containing one element`() {
        val listOfDeliveryToAShop = createListOfDeliveryToShop("Carrot Soup,E110011,I118,72,Depot-A,7")
        assertTrue(listOfDeliveryToAShop.size == 1)
        assertEquals(listOf(DeliveryToAShop("Carrot Soup","E110011","I118",72,"Depot-A",7)), listOfDeliveryToAShop )
    }
    @Test
    fun `a string containing CSV data for several DeliveryToAShop creates a ListOfDeliveryToAShop containing several elements`() {
        val listOfDeliveryToAShop = createListOfDeliveryToShop("Carrot Soup,E110013,I122,96,Depot-B,11,Carrot Soup,E110013,I122,96,Depot-C,13,Chicken Soup,E110004,I105,96,Depot-A,10")
        val expectedResult = listOf (
            DeliveryToAShop("Carrot Soup","E110013","I122",96,"Depot-B",11),
            DeliveryToAShop("Carrot Soup","E110013","I122",96,"Depot-C",13),
            DeliveryToAShop("Chicken Soup","E110004","I105",96,"Depot-A",10)
        )
        assertEquals(expectedResult, listOfDeliveryToAShop )
    }
    @Test
    fun `a string containing CSV data for several DeliveryToAShop including an invalid deliveryQty creates a ListOfDeliveryToAShop containing several elements`() {
        val listOfDeliveryToAShop = createListOfDeliveryToShop("Carrot Soup,E110013,I122,96,Depot-B,11,Carrot Soup,E110013,I122,x96,Depot-C,13,Chicken Soup,E110004,I105,96,Depot-A,10")
        val expectedResult = listOf (
            DeliveryToAShop("Carrot Soup","E110013","I122",96,"Depot-B",11),
            DeliveryToAShop("Chicken Soup","E110004","I105",96,"Depot-A",10)
        )
        assertEquals(expectedResult, listOfDeliveryToAShop )
    }


}
