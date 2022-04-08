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

class CardChecker(val userDetails: UserDetails, val cards: List<Card>) {
    val firstIndexForGroupIdFilter = mutableMapOf<String, Int>()
    val firstIndexForGroupIdControlGroup = mutableMapOf<String, Int>()

    fun filterUserDetails():List<Card> = matchCardsAgainstUserDetails().filterIndexed {index, card -> isNotADuplicate(index, card)}

    fun matchCardsAgainstUserDetails():List<Card> {
        var index = 0
        firstIndexForGroupIdFilter.clear()
        firstIndexForGroupIdControlGroup.clear()
        return cards.filter { card ->
            if (applyFilter(userDetails, card)) {
                addCardToMap(card, index++)
                true
            } else false
        }
    }

    fun addCardToMap(card:Card, index:Int) {
        if (card.filtering.groupId != null) {
            if (ControlGroup in card.filtering.filters) {
                firstIndexForGroupIdControlGroup.putIfAbsent(card.filtering.groupId, index )
            }  else {
                firstIndexForGroupIdFilter.putIfAbsent(card.filtering.groupId, index)
            }
        }
    }

    fun isNotADuplicate(index:Int, card:Card) =
        if (card.filtering.groupId == null) true
        else {
            if (ControlGroup in card.filtering.filters) {
                (card.filtering.groupId !in firstIndexForGroupIdFilter) && (index == firstIndexForGroupIdControlGroup[card.filtering.groupId])
            } else {
                (index == firstIndexForGroupIdFilter[card.filtering.groupId])
            }
        }
}

