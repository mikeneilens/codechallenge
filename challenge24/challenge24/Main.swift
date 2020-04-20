//
//  Main.swift
//  challenge24
//
//  Created by Michael Neilens on 04/04/2020.
//  Copyright © 2020 Michael Neilens. All rights reserved.
//

import Foundation

typealias Card = String
typealias Cards = Array<Card>

extension String {
    var rank:Int {
        switch (prefix(1)) {
            case "T": return 10
            case "J": return 11
            case "Q": return 12
            case "K": return 13
            case "A": return 14
            default: return Int(prefix(1)) ?? 0
        }
    }
    var suit:String {
        return String(suffix(1))
    }
    var aceLowRank:Int {
        return  (rank == 14) ? 1 : rank
    }
}

func getHighestCard(cards:Cards) -> Cards? {
    return Array(cards.sorted{return $0.rank > $1.rank }.prefix(5))
}

func getPair(cards:Cards) -> Cards? {
    guard let pair =  cards.getMatches(qty:2) else {return nil}
    let remainingCards:Cards = cards - pair
    return  pair + remainingCards.sorted{return $0.rank > $1.rank }.prefix(3)
}

func getTwoPair(cards:Cards) -> Cards? {
    guard let firstPair =  cards.getMatches(qty:2) else {return nil}
    guard let secondPair =  getPair(cards:(cards - firstPair)) else {return nil}
    return  firstPair + secondPair.first(3)
}

func getThree(cards:Cards) -> Cards? {
    return cards.getMatches(qty:3) 
}

func getStraight(cards:Cards) -> Cards? {
    return cards.removeDuplicateRank().getMatches(qty: 5, {$0.rank == $1.rank + 1})
}

func getAceLowStraight(cards:Cards) -> Cards? {
    return cards.removeDuplicateRank().getMatches(qty: 5, {$0.aceLowRank == $1.aceLowRank + 1}, {$0.aceLowRank > $1.aceLowRank})
}

struct Hand:Equatable {
    let cards:Cards
    let handRank:Int
    var value:String {return cards.value}
    
    static func > (left:Hand, right:Hand) -> Bool {
        return (left.handRank > right.handRank || (left.handRank == right.handRank && left.value > right.value))
    }
}

extension Cards {
    
    func bestHand()->Hand {
        let rules = [
                      (getStraight,5)
                    , (getAceLowStraight,4)
                    , (getThree,3)
                    , (getTwoPair,2)
                    , (getPair,1)
                    , (getHighestCard,0)
                    ]
        let results:Array<Hand> = rules.compactMap{rule, handRank in
            if let cards = rule(self) {
                return Hand(cards:cards, handRank: handRank)
            } else {
                return nil
            }
        }
        return results[0]
    }
    
    //defaults to return when consecutive cards are the same
    func getMatches(qty:Int,_ matcher:(Card,Card)->Bool = { $0.rank == $1.rank}, _ sortBy:(Card,Card)->Bool = { $0.rank > $1.rank}) -> Cards? {
        let matches = sorted(by:sortBy)
            .reduce(Array<String>()){cardSequence, card in
                if cardSequence.count == qty {return cardSequence}
                if let lastCard = cardSequence.last {
                    if matcher(lastCard , card) {return cardSequence + [card]}
                }
                return [card]
            }
        return  (matches.count == qty ) ? matches : nil
    }
    
    static func - (left:Cards, right:Cards) -> Cards { //warning will remove cards that are identical.
        return Array(Set(left).subtracting(Set(right)))
    }

    func removeDuplicateRank() -> Cards {
        return sorted{$0 < $1}
            .reduce(Array<String>()){cardSequence, card in
            if let lastCard = cardSequence.last {
                if (lastCard == card) {return cardSequence }
            }
            return cardSequence + [card]
        }
    }
    
    var value:String {
        return reduce(""){result, card in result + String(format: "%02d", card.rank) }
    }
    
    func first(_ n:Int) -> Cards { // avoids having to keep casting array slices back to arrays
        return Array(prefix(n))
    }
}


