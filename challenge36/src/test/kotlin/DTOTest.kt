import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class DTOTest: StringSpec({
    "card with no filters parses correctly" {
        val cardJson = """
               {
                  "id": "card3",
                  "cardType": "departments",
                  "name": "Old departments list"
               }
        """.trimIndent()

        val card = Json.decodeFromString<DTO.Card>(cardJson)
        card shouldBe DTO.Card("card3","departments","Old departments list", null)
    }
    "a list of cards with no filters parse correctly" {
        val cardsJson = """
            [
                {
                    "id": "card1",
                    "cardType": "carousel",
                    "name": "Carousel"
                },
                {
                    "id": "card2",
                    "cardType": "banner",
                    "name": "Banner"
                }
            ]
        """.trimIndent()
        val cards = Json.decodeFromString<List<DTO.Card>>(cardsJson)
        cards.size shouldBe 2
        cards[0] shouldBe  DTO.Card("card1","carousel","Carousel",null)
        cards[1] shouldBe  DTO.Card("card2","banner","Banner",null)
    }
    "a card with a filter that has no groupId parses correctly" {
        val cardJson = """
               {
                    "id": "card3",
                    "cardType": "departments",
                    "name": "Old departments list"
                    "filtering": {
                        "filters": [
                            {
                                "filter": "controlGroup"
                            }
                        ]
                    }
               }
        """.trimIndent()
        val card = Json.decodeFromString<DTO.Card>(cardJson)
        val expectedFilter = DTO.Filter("controlGroup", null, null)
        val expectedFiltering = DTO.Filtering(null, listOf(expectedFilter))
        card shouldBe DTO.Card("card3","departments","Old departments list", expectedFiltering)
    }
    "a card with a filter that has no value or range parses correctly" {
        val cardJson = """
               {
                    "id": "card3",
                    "cardType": "departments",
                    "name": "Old departments list"
                    "filtering": {
                        "groupId": "departmentGroup",
                        "filters": [
                            {
                                "filter": "controlGroup"
                            }
                        ]
                    }
               }
        """.trimIndent()
        val card = Json.decodeFromString<DTO.Card>(cardJson)
        val expectedFilter = DTO.Filter("controlGroup", null, null)
        val expectedFiltering = DTO.Filtering("departmentGroup", listOf(expectedFilter))
        card shouldBe DTO.Card("card3","departments","Old departments list", expectedFiltering)
    }
    "a card with a filter that has value but no range parses correctly" {
        val cardJson = """
               {
                    "id": "card4",
                    "cardType": "recommendations",
                    "name": "Recommendations1",
                    "filtering": {
                        "groupId": "recsGroup",
                        "filters": [
                            {
                                "filter": "osVersionGreaterThan",
                                "value": "14.9.0"
                            }
                        ]
                    }
               }
        """.trimIndent()
        val card = Json.decodeFromString<DTO.Card>(cardJson)
        val expectedFilter = DTO.Filter("osVersionGreaterThan", "14.9.0", null)
        val expectedFiltering = DTO.Filtering("recsGroup", listOf(expectedFilter))
        card shouldBe DTO.Card("card4","recommendations","Recommendations1", expectedFiltering)
    }
    "a card with a filter that has range but no value parses correctly" {
        val cardJson = """
               {
                    "id": "card4",
                    "cardType": "recommendations",
                    "name": "Recommendations1",
                    "filtering": {
                        "groupId": "recsGroup",
                        "filters": [
                            {
                                "filter": "osVersionBetween",
                                "range": {
                                    "min": "14.9.0",
                                    "max": "15.2.0"
                                 }
                            }
                        ]
                    }
               }
        """.trimIndent()
        val card = Json.decodeFromString<DTO.Card>(cardJson)
        val expectedRange = DTO.FilterRange("14.9.0", "15.2.0")
        val expectedFilter = DTO.Filter("osVersionBetween", null, expectedRange)
        val expectedFiltering = DTO.Filtering("recsGroup", listOf(expectedFilter))
        card shouldBe DTO.Card("card4","recommendations","Recommendations1", expectedFiltering)
    }
    "the full input json should parse correctly" {
        val cardData = Json.decodeFromString<List<DTO.Card>>(inputJson)
    }
})