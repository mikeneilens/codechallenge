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
    card.filtering.filters.any {filter -> filter.shouldInclude(userDetails.osVersion) } || card.filtering.filters.isEmpty()

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
        card.filtering.groupId?.let { groupId -> if (groupId !in indexForGroupId) indexForGroupId[groupId] = index }
    }
    return indexForGroupId
}

