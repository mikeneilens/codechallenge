import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.OperatingSystemVersion
import model.UserDetails

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
        filteredCards[2].name shouldBe "Recommendatons1"
        filteredCards[3].name shouldBe "New departments list 15"
        filteredCards[4].name shouldBe "Banner2"
    }
})