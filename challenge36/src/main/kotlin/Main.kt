import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.*

fun filterUserDetails(userDetails: UserDetails, json: String):List<Card> {
    val cards = Json.decodeFromString<List<DTO.Card>>(json).map(::createCard)
    return filterUserDetails(userDetails, cards )
}

fun filterUserDetails(userDetails: UserDetails, cards: List<Card>):List<Card> =
    cards.filter{ applyFilter(userDetails, it) }
        .removeControlGroupIfAnyCardsFiltered()
        .removeDuplicateGroupId()

fun applyFilter(userDetails: UserDetails, card:Card):Boolean =
    card.filtering.filters.any { filter -> filter.shouldInclude(userDetails.osVersion) } || card.filtering.filters.isEmpty()

fun List<Card>.removeControlGroupIfAnyCardsFiltered():List<Card> =
    if (any{card ->  card.filtering.filters.filter{filter -> filter != ControlGroup }.isNotEmpty()})
        filter{ card -> card.filtering.filters.none {filter -> filter == ControlGroup }}
    else this

fun List<Card>.removeDuplicateGroupId():List<Card> {
    val indexForGroupId = getFirstIndexForEachGroupId()
    return foldIndexed(emptyList()) { index, cards, card ->
        val groupId = card.filtering.groupId
        if (groupId != null && indexForGroupId[groupId] != index ) cards else cards + card
    }
}

private fun List<Card>.getFirstIndexForEachGroupId(): Map<String, Int> {
    val indexForGroupId = mutableMapOf<String, Int>()
    forEachIndexed { index, card ->
        card.filtering.groupId?.let { groupId -> if (groupId !in indexForGroupId) indexForGroupId[groupId] = index }
    }
    return indexForGroupId
}

