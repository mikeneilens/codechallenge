//
//  CardsChecker.swift
//  Filtering
//
//  Created by Michael Neilens on 09/04/2022.
//

import Foundation

func filterUserDetails(userDetails: UserDetails, cardData: String ) throws -> Array<Card> {
    let cards = try parseJson(cardData).map(createCard(card:))
    return CardsChecker().filterUserDetails(userDetails: userDetails, cards: cards )
}

protocol CardAnalyser{
    func updateWith(card: Card, at index: Int)
    func shouldSelect(card :Card, at index:Int) -> Bool
}

struct CardsChecker {
    let cardAnalyser:CardAnalyser = GroupIdCardPosition()

    func filterUserDetails(userDetails: UserDetails, cards: Array<Card> ) -> Array<Card> {
        let filteredCards = matchCardsAgainstUserDetails(userDetails: userDetails, cards: cards)
        return removeDuplicateGroupIds(cards: filteredCards)
    }

    func matchCardsAgainstUserDetails( userDetails: UserDetails, cards: Array<Card> ) -> Array<Card> {
        var filteredCards = Array<Card>()

        for card in cards {
            if applyFilter(card: card, userDetails: userDetails) {
                cardAnalyser.updateWith(card: card, at: filteredCards.count)
                filteredCards.append(card)
            }
        }
        return filteredCards
    }

    func applyFilter(card: Card, userDetails: UserDetails) -> Bool {
        card.filtering.filters.count == 0 || card.filtering.filters.contains{filter in filter.shouldInclude(userDetails.osVersion) }
    }

    func removeDuplicateGroupIds(cards: Array<Card>) -> Array<Card> {
        var result = Array<Card>()
        for (index, card) in cards.enumerated() {
            if cardAnalyser.shouldSelect(card: card, at: index) { result.append(card) }
        }
        return result
    }

}

class GroupIdCardPosition: CardAnalyser {
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
    
    func shouldSelect(card: Card, at index: Int) -> Bool {
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
