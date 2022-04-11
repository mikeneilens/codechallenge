//
//  CardsChecker.swift
//  Filtering
//
//  Created by Michael Neilens on 09/04/2022.
//

import Foundation

func filterUserDetails(userDetails: UserDetails, cardData: String ) throws -> Array<Card> {
    let cards = try parseJson(cardData).map(createCard(card:))
    return filterUserDetails(userDetails: userDetails, cards: cards )
}

func filterUserDetails(userDetails: UserDetails, cards: Array<Card> ) -> Array<Card> {
    let initialResult = matchCardsAgainstUserDetails(userDetails: userDetails, cards: cards)
    return initialResult.removeDuplicateGroupIds()
}

func matchCardsAgainstUserDetails( userDetails: UserDetails, cards: Array<Card> ) -> InitialResult {
    let groupIdCardPosition = GroupIdCardPosition()
    var filteredCards = Array<Card>()

    for card in cards {
        if applyFilter(card: card, userDetails: userDetails) {
            groupIdCardPosition.updateWith(card: card, at: filteredCards.count)
            filteredCards.append(card)
        }
    }
    return InitialResult(cards: filteredCards, groupIdPosition: groupIdCardPosition)
}

func applyFilter(card: Card, userDetails: UserDetails) -> Bool {
    card.filtering.filters.count == 0 || card.filtering.filters.contains{filter in filter.shouldInclude(userDetails.osVersion) }
}

struct InitialResult {
    let cards: Array<Card>
    let groupIdPosition: GroupIdCardPosition
        
    func removeDuplicateGroupIds() -> Array<Card> {
        var result = Array<Card>()
        for (index, card) in cards.enumerated() {
            if groupIdPosition.isNotADuplicate(card: card, at: index) { result.append(card) }
        }
        return result
    }
}
    
class GroupIdCardPosition {
    var forFilters = Dictionary<String, Int>()
    var forControlGroups = Dictionary<String, Int>()
    
    func updateWith(card:Card, at index:Int ) {
        if  let groupId =  card.filtering.groupId {
            if (card.filtering.containsControlGroup() && forFilters[groupId] == nil ) {
                forControlGroups.putIfAbsent(groupId, index )
            }  else {
                forControlGroups.removeValue(forKey: groupId)
                forFilters.putIfAbsent(groupId, index)
            }
        }
    }
    
    func isNotADuplicate(card: Card, at index: Int) -> Bool {
        if  let groupId = card.filtering.groupId  {
            return (card.filtering.containsControlGroup()) ? (index == forControlGroups[groupId]) : (index == forFilters[groupId])
        }
        return true
    }
}

extension Dictionary {
    mutating func putIfAbsent(_ k:Key, _ v:Value) {
        if (self[k] == nil ) { self[k] = v }
    }
}
