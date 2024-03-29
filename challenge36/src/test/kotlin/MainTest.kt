import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.*

class MainTest:StringSpec ({
    "When model.UserDetails osVersion is nil the filtered list of cards is: ['Carousel', 'Banner', 'New departments list default']" {
        val userDetails = UserDetails(null)
        val filteredCards = filterUserDetails(userDetails, inputJson)
        filteredCards.size shouldBe  3
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "New departments list default"
    }

    "When model.UserDetails osVersion is “15.0.0” the filtered list of cards is: ['Carousel', 'Banner', 'Recommendations1', 'New departments list 15', 'Banner2']"{
        val userDetails = UserDetails(OperatingSystemVersion(15,0,0))
        val filteredCards = filterUserDetails(userDetails, inputJson)
        filteredCards.size shouldBe  5
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "Recommendations1"
        filteredCards[3].name shouldBe "New departments list 15"
        filteredCards[4].name shouldBe "Banner2"
    }

    "When model.UserDetails osVersion is “14.0.1” the filtered list of cards is: ['Carousel', 'Banner', 'New departments list default', 'Recommendations2']"{
        val userDetails = UserDetails(OperatingSystemVersion(14,0,1))
        val filteredCards = filterUserDetails(userDetails, inputJson)
        filteredCards.size shouldBe  4
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "New departments list default"
        filteredCards[3].name shouldBe "Recommendations2"
    }

    "When model.UserDetails osVersion is “13.0.0” the filtered list of cards is: ['Carousel', 'Banner', 'Old departments']"{
        val userDetails = UserDetails(OperatingSystemVersion(13,0,0))
        val filteredCards = filterUserDetails(userDetails, inputJson)
        filteredCards.size shouldBe  3
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "Old departments list"
    }

    "When the list of cards contains any filtered cards and a control group card with a unique groupId, removeControlGroupIfAnyCardsFiltered should not remove the control group card " {
        val filter1 = OsVersionGreaterThan(OperatingSystemVersion(1,1,1))
        val filter2 = OsVersionEquals(OperatingSystemVersion(1,1,1))
        val card0 = Card("c0","t0","name0", Filtering("gId0", listOf(filter1)))
        val card1 = Card("c1","t1","name1", Filtering("gId1", listOf(ControlGroup)))
        val card2 = Card("c2","t2","name2", Filtering("gId2", listOf(filter2)))

        val result = listOf(card0,card1,card2).removeControlGroupIfAnyCardsFilteredWithSameGroupId()
        result.size shouldBe 3
    }

    "When the list of cards contains a control group card and filtered card with the same groupId removeControlGroupIfAnyCardsFiltered should remove the control group card " {
        val filter1 = OsVersionGreaterThan(OperatingSystemVersion(1,1,1))
        val filter2 = OsVersionEquals(OperatingSystemVersion(1,1,1))
        val card0 = Card("c0","t0","name0", Filtering("gId0", listOf(filter1)))
        val card1 = Card("c1","t1","name1", Filtering("gId1", listOf(ControlGroup)))
        val card2 = Card("c2","t2","name2", Filtering("gId2", listOf(filter2)))
        val card3 = Card("c2","t2","name2", Filtering("gId1", listOf(filter2)))

        val result = listOf(card0,card1,card2, card3).removeControlGroupIfAnyCardsFilteredWithSameGroupId()
        result.size shouldBe 3
        result[0] shouldBe card0
        result[1] shouldBe card2
        result[2] shouldBe card3
    }

    "When the list of cards contains only control group card , removeControlGroupIfAnyCardsFiltered should not remove any cards " {
        val card0 = Card("c0","t0","name0", Filtering("gId0", listOf(ControlGroup)))
        val card1 = Card("c1","t1","name1", Filtering("gId1", listOf(ControlGroup)))
        val card2 = Card("c2","t2","name2", Filtering("gId2", listOf(ControlGroup)))

        val result = listOf(card0,card1,card2).removeControlGroupIfAnyCardsFilteredWithSameGroupId()
        result.size shouldBe 3
    }

    "when the list of cards contains filtering with the same groupId more than once, remove all cards with the groupId except the first one" {
        val card0 = Card("c0","t0","name0", Filtering("gId0", listOf()))
        val card1 = Card("c1","t1","name1", Filtering("gId1", listOf()))
        val card2 = Card("c2","t2","name2", Filtering("gId0", listOf()))
        val card3 = Card("c3","t3","name3", Filtering("gId0", listOf()))
        val card4 = Card("c4","t4","name4", Filtering("gId3", listOf()))

        val result = listOf(card0,card1,card2, card3, card4).removeDuplicateGroupId()
        result.size shouldBe 3
        result[0] shouldBe card0
        result[1] shouldBe card1
        result[2] shouldBe card4
    }

    //Alternate solution
    "Alternate when model.UserDetails osVersion is nil the filtered list of cards is: ['Carousel', 'Banner', 'New departments list default']" {
        val userDetails = UserDetails(null)
        val cards = Json.decodeFromString<List<DTO.Card>>(inputJson).map(::createCard)
        val filteredCards = CardChecker(userDetails, cards).filterUserDetails()
        filteredCards.size shouldBe  3
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "New departments list default"
    }

    "Alternate when model.UserDetails osVersion is “15.0.0” the filtered list of cards is: ['Carousel', 'Banner', 'Recommendations1', 'New departments list 15', 'Banner2']"{
        val userDetails = UserDetails(OperatingSystemVersion(15,0,0))
        val cards = Json.decodeFromString<List<DTO.Card>>(inputJson).map(::createCard)
        val filteredCards = CardChecker(userDetails, cards).filterUserDetails()
        filteredCards.size shouldBe  5
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "Recommendations1"
        filteredCards[3].name shouldBe "New departments list 15"
        filteredCards[4].name shouldBe "Banner2"
    }

    "Alternate model.UserDetails osVersion is “14.0.1” the filtered list of cards is: ['Carousel', 'Banner', 'New departments list default', 'Recommendations2']"{
        val userDetails = UserDetails(OperatingSystemVersion(14,0,1))
        val cards = Json.decodeFromString<List<DTO.Card>>(inputJson).map(::createCard)
        val filteredCards = CardChecker(userDetails, cards).filterUserDetails()
        filteredCards.size shouldBe  4
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "New departments list default"
        filteredCards[3].name shouldBe "Recommendations2"
    }

    "Alternate model.UserDetails osVersion is “13.0.0” the filtered list of cards is: ['Carousel', 'Banner', 'Old departments']"{
        val userDetails = UserDetails(OperatingSystemVersion(13,0,0))
        val cards = Json.decodeFromString<List<DTO.Card>>(inputJson).map(::createCard)
        val filteredCards = CardChecker(userDetails, cards).filterUserDetails()
        filteredCards.size shouldBe  3
        filteredCards[0].name shouldBe "Carousel"
        filteredCards[1].name shouldBe "Banner"
        filteredCards[2].name shouldBe "Old departments list"
    }
})