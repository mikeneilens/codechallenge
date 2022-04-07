import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import model.*
import java.lang.IllegalArgumentException

class DTOTest: WordSpec({
    "when parsing json into the DTO" should ({
        "card with no filters parses correctly" {
            val cardJson = """
               {
                  "id": "card3",
                  "cardType": "departments",
                  "name": "Old departments list"
               }
            """.trimIndent()

            val card = Json.decodeFromString<DTO.Card>(cardJson)
            card shouldBe DTO.Card("card3", "departments", "Old departments list", null)
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
            cards[0] shouldBe DTO.Card("card1", "carousel", "Carousel", null)
            cards[1] shouldBe DTO.Card("card2", "banner", "Banner", null)
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
            card shouldBe DTO.Card("card3", "departments", "Old departments list", expectedFiltering)
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
            card shouldBe DTO.Card("card3", "departments", "Old departments list", expectedFiltering)
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
            card shouldBe DTO.Card("card4", "recommendations", "Recommendations1", expectedFiltering)
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
            card shouldBe DTO.Card("card4", "recommendations", "Recommendations1", expectedFiltering)
        }
        "the full input json should parse correctly" {
            val cardData = Json.decodeFromString<List<DTO.Card>>(inputJson)
            cardData.size shouldBe 8
        }
    })

    "when converting DTO to the model" should ({
        "filter DTO with value 1.2.3 is converted to a operationSystemVersion correctly"{
            val dtoValue = DTO.Filter("test", "1.2.3")
            dtoValue.operatingSystemValue() shouldBe OperatingSystemVersion(1,2,3)
        }
        "filter DTO with no value throws an error if trying to convert it to an operatingSystemVersion"{
            val exception = shouldThrow<IllegalArgumentException> {
                DTO.Filter("test_filter", null, null).operatingSystemValue()
            }
            exception.message shouldBe "filter test_filter had no value for operating system"
        }
        "filter DTO with non-numeric value throws an error if trying to convert it to an operatingSystemVersion"{
            val exception = shouldThrow<IllegalArgumentException> {
                DTO.Filter("test_filter", "a.b.c", null).operatingSystemValue()
            }
            exception.message shouldBe "illegal operating system value a.b.c"
        }
        "filter DTO with no patch version throws an error if trying to convert it to an operatingSystemVersion"{

            val exception = shouldThrow<IllegalArgumentException> {
                DTO.Filter("test_filter", "1.2", null).operatingSystemValue()
            }
            exception.message shouldBe "illegal operating system value 1.2"
        }
        "DTO.Card can be used to create a Card when there is no filters" {
            val card =  createCard(DTO.Card("id1","cardType1","name1",null))
            card.id shouldBe "id1"
            card.cardType shouldBe "cardType1"
            card.name shouldBe "name1"
            card.filtering.groupId shouldBe null
            card.filtering.filters.size shouldBe 0
        }
        "DTO.Card can be used to create a Card when there is valid filters" {
            val filtering = DTO.Filtering("groupId1", listOf(DTO.Filter("controlGroup"),DTO.Filter("osVersionEquals","1.2.3")))
            val card =  createCard(DTO.Card("id1","cardType1","name1",filtering))
            card.id shouldBe "id1"
            card.cardType shouldBe "cardType1"
            card.name shouldBe "name1"
            card.filtering.groupId shouldBe "groupId1"
            val filtersForCard = card.filtering.filters
            filtersForCard.size shouldBe 2
            filtersForCard[0] shouldBe ControlGroup
            filtersForCard[1] should beInstanceOf<OsVersionEquals>()
        }
        "DTO.Card throws an error creating a Card when there is an invalid filters" {
            val filtering = DTO.Filtering("groupId1", listOf(DTO.Filter("control_Group"),DTO.Filter("osVersionEquals","1.2.3")))
            val exception = shouldThrow<IllegalArgumentException> {
                createCard(DTO.Card("id1", "cardType1", "name1", filtering))
            }
            exception.message shouldBe "invalid filter control_Group"
        }
        "should be possible to convert list of cards on input json to a list of cards " {
            val dtoCards = Json.decodeFromString<List<DTO.Card>>(inputJson)
            val cards = dtoCards.map{createCard(it)}
            cards.size shouldBe 8
            cards[0].id shouldBe "card1"
        }
    })
})