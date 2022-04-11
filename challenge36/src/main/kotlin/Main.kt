import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.*

fun filterUserDetails(userDetails: UserDetails, json: String):List<Card> {
    val cards = Json.decodeFromString<List<DTO.Card>>(json).map(::createCard)
    return filterUserDetails(userDetails, cards )
}

fun filterUserDetails(userDetails: UserDetails, cards: List<Card>):List<Card> =
    cards.matchCardsAgainstUserDetails(userDetails)
        .removeControlGroupIfAnyCardsFilteredWithSameGroupId()
        .removeDuplicateGroupId()

fun List<Card>.matchCardsAgainstUserDetails(userDetails: UserDetails) = filter{card -> applyFilter(userDetails, card) }

fun applyFilter(userDetails: UserDetails, card:Card):Boolean =
    card.filtering.filters.isEmpty() || card.filtering.filters.any {filter -> filter.shouldInclude(userDetails.osVersion) }

fun List<Card>.removeControlGroupIfAnyCardsFilteredWithSameGroupId():List<Card> =
    filter{card -> card.filtering.filters.none(Filter::isControlGroup) || !listContainsFilterWithSameGroupId(card)  }

private fun List<Card>.listContainsFilterWithSameGroupId(card: Card):Boolean {
    val groupId = card.filtering.groupId
    return any{cardInList -> cardInList.filtering.groupId == groupId && cardInList.filtering.filters.none(Filter::isControlGroup)}
}

fun List<Card>.removeDuplicateGroupId():List<Card> {
    val firstIndexForGroupId = getFirstIndexForEachGroupId()
    return filterIndexed { index, card ->
        val groupId = card.filtering.groupId
        groupId == null || (firstIndexForGroupId[groupId] == index)
    }
}

private fun List<Card>.getFirstIndexForEachGroupId(): Map<String, Int> {
    val indexForGroupId = mutableMapOf<String, Int>()
    forEachIndexed { index, card ->
        card.filtering.groupId?.let { groupId -> indexForGroupId.putIfAbsent(groupId, index) }
    }
    return indexForGroupId
}

//alternate solution that iterates through the list of cards twice...

interface CardAnalyser {
    fun updateWithCard(card:Card, index:Int)
    fun shouldInclude(card: Card, index: Int):Boolean
}

class DuplicateAnalyser:CardAnalyser {
    val indexForGroupIdFilter = mutableMapOf<String, Int>()
    val indexForGroupIdControlGroup = mutableMapOf<String, Int>()

    override fun updateWithCard(card:Card, index:Int) {
        if (card.filtering.groupId != null) {
            if (ControlGroup in card.filtering.filters && card.filtering.groupId !in indexForGroupIdFilter) {
                indexForGroupIdControlGroup.putIfAbsent(card.filtering.groupId, index )
            }  else {
                indexForGroupIdFilter.putIfAbsent(card.filtering.groupId, index)
                indexForGroupIdControlGroup.remove(card.filtering.groupId)
            }
        }
    }

    override fun shouldInclude(card: Card, index: Int) =
        if (card.filtering.groupId != null) {
            if (ControlGroup in card.filtering.filters) {
                (index == indexForGroupIdControlGroup[card.filtering.groupId])
            } else {
                (index == indexForGroupIdFilter[card.filtering.groupId])
            }
        } else true
}

class CardChecker(val userDetails: UserDetails, val cards: List<Card>, val cardAnalyser:CardAnalyser = DuplicateAnalyser()) {

    fun filterUserDetails():List<Card> = matchCardsAgainstUserDetails().filterIndexed {index, card -> cardAnalyser.shouldInclude(card, index)}

    fun matchCardsAgainstUserDetails():List<Card> {
        var index = 0
        return cards.filter { card ->
            if (applyFilter(userDetails, card)) {
                cardAnalyser.updateWithCard(card, index++)
                true
            } else false
        }
    }

}

