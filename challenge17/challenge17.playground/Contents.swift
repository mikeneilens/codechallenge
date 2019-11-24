enum Suit {
    case Clubs; case Diamonds; case Hearts; case Spades

    static func fromString(_ string:String)-> Suit {
        let suitMap = ["C":Suit.Clubs, "D":Suit.Diamonds, "H":Suit.Hearts, "S":Suit.Spades]
        return suitMap[string] ?? Suit.Clubs
    }
}

struct Card {
    let rank:Rank
    let suit:Suit
    init(_ string:String) {
        let first = String(string.first ?? Character(""))
        let last = String(string.last ?? Character(""))
        rank = Rank.fromString(first)
        suit = Suit.fromString(last)
    }
}

enum Rank {
    case Ace
    case Number(value:Int)
    case PictureCard(Picture)
    
    enum Picture {case Jack;case Queen;case King}
    
    func value() -> Array<Int> {
        switch self {
        case .Ace: return [1,11]
        case .PictureCard: return [10]
        case .Number(let value): return [value]
        }
    }

    static func fromString(_ string:String) -> Rank {
        let rankMap = ["T":Rank.Number(value: 10),"J":Rank.PictureCard(.Jack), "Q":Rank.PictureCard(.Queen),"K":Rank.PictureCard(.King),"A":Rank.Ace]
        if let rank = rankMap[string] {
            return rank
        } else {
            if let number =  Int(string) {
                return .Number(value:number)
            } else {
                return .Ace
            }
        }
    }
}

typealias Cards = Array<Card>

//This adds a list of Ints to a list of Ints. E.g. [3,4,5] + [1,11] gives [(3+1),(3+11),(4+1),(4+11),(5+1),(5+11)] which is [4,14,5,15,6,16]
func add(_ numbers:Array<Int>, _ card:Card)->  Array<Int> {
   return card.rank.value().flatMap{cardValue in  numbers.map{listItem in cardValue + listItem}}
}

extension Cards {
    func totalLessThan22() -> Int {
        return self.reduce([0]) { acc, card in add(acc,card) }
            .sorted().last { $0 < 22 } ?? 0
    }
}

struct HandResult{let isTheTypeOfHand:Bool; let value:Int
    init(isTheTypeOfHand:Bool) {
        self.isTheTypeOfHand = isTheTypeOfHand
        self.value = 0
    }
    init(isTheTypeOfHand:Bool, value:Int) {
        self.isTheTypeOfHand = isTheTypeOfHand
        self.value = value
    }
}

func isBust(cards:Cards) -> Bool { return (cards.totalLessThan22() == 0)}

typealias HandChecker = (Cards) -> HandResult
//These are the functions used to determine what type of hand a player has
func isPontoon(cards:Cards) -> HandResult {
    if cards.count != 2 {return HandResult(isTheTypeOfHand:false)}
    switch (cards[0].rank, cards[1].rank) {
        case (.Ace, .PictureCard):
            return HandResult(isTheTypeOfHand:true)
        case (.PictureCard, .Ace):
            return HandResult(isTheTypeOfHand:true)
        case (_, _):
            return HandResult(isTheTypeOfHand:false)
    }
}
func isFiveCardTrick(cards:Cards) -> HandResult { return HandResult(isTheTypeOfHand:((cards.count == 5) && (!isBust(cards:cards)))) }
func isNotBust(cards:Cards) -> HandResult {return HandResult(isTheTypeOfHand:!isBust(cards: cards), value: cards.totalLessThan22()) }

typealias HandCheckers = Array<(HandChecker,String)>

enum Winner{case Player;case Dealer}
struct Result{let winner:Winner; let description:String}

func playerBeatsDealer(playersCards:Cards, dealersCards:Cards, handCheckers:HandCheckers) -> Bool {
    let result:Bool? = handCheckers.compactMap { (check,_) in
        switch (true) {
        case (check(playersCards).isTheTypeOfHand && check(dealersCards).isTheTypeOfHand): return (check(playersCards).value > check(dealersCards).value)
        case (check(dealersCards).isTheTypeOfHand) : return false
        case (check(playersCards).isTheTypeOfHand) : return true
        default: return nil
        }
    }.first
    return result ?? false
}

func description(cards:Cards, handCheckers:HandCheckers) -> String {
    let result:String? = handCheckers.compactMap {(check,description) in
        if (check(cards).isTheTypeOfHand)  {
            if (check(cards).value > 0) {return  "\(description) \(check(cards).value)" } else {return description}
        } else {
            return nil
        }
    }.first
    return result ?? "Bust"
}

let handCheckers:HandCheckers = [(isPontoon,"Pontoon"),(isFiveCardTrick,"Five Card Trick"),(isNotBust,"Total value of")]

//This is the main function //////////////////////////////////////////////////////////////////
func determineWhoHasWon(_playersCards:Array<String>, _dealersCards:Array<String>) -> Result {
    let playersCards = _playersCards.map{Card($0)}
    let dealersCards = _dealersCards.map{Card($0)}
    if (playerBeatsDealer(playersCards: playersCards, dealersCards: dealersCards, handCheckers: handCheckers)) {
        return Result(winner:.Player, description: "Player wins with \(description(cards: playersCards,handCheckers: handCheckers))")
    }
    else {
        return Result(winner:.Dealer,description:"Dealer wins with \(description(cards: dealersCards,handCheckers: handCheckers))")
    }
}

//Some simple tests
let jackOfHearts = "JH"
let aceOfHearts = "AH"
let pontoonHand = [jackOfHearts,aceOfHearts]

let twoOfDiamnds = "2D"
let threeOfHearts = "3H"
let fourOfSpades = "4S"
let twoOfClubs = "2C"
let fiveCardHand = [aceOfHearts,twoOfDiamnds,threeOfHearts,fourOfSpades,twoOfClubs]
let tenOfHearts = "TH"
let twentyHand = [tenOfHearts,jackOfHearts]
let twentyOneHand = [tenOfHearts,aceOfHearts]
let bustHand = [tenOfHearts, jackOfHearts,threeOfHearts]

print("player has pontoon,dealer has pontoon", determineWhoHasWon(_playersCards: pontoonHand, _dealersCards: pontoonHand)) //"Dealer wins with Pontoon"

print("player has pontoon, dealer has five card trick", determineWhoHasWon(_playersCards: pontoonHand, _dealersCards: fiveCardHand)) //"Player wins with Pontoon"

print("player has five card trick, dealer has five card trick", determineWhoHasWon(_playersCards: fiveCardHand, _dealersCards: fiveCardHand)) //"Dealer wins with Five Card Trick"

print("player has five card trick, dealer has twenty", determineWhoHasWon(_playersCards: fiveCardHand, _dealersCards: twentyHand)) //"Player wins with Five Card Trick"

print("player has twenty, dealer has twenty", determineWhoHasWon(_playersCards: twentyHand, _dealersCards: twentyHand)) //"Dealer wins with Total value of 20"

print("player has twenty one, dealer has twenty", determineWhoHasWon(_playersCards: twentyOneHand, _dealersCards: twentyHand)) //"Player wins with Total value of 21"

print("player has bust hand, dealer has bust hand", determineWhoHasWon(_playersCards: bustHand, _dealersCards: bustHand)) //"Dealer wins with Bust"

print("player has twenty, dealer has bust hand", determineWhoHasWon(_playersCards: twentyHand, _dealersCards: bustHand)) //"Player wins with Total value of 20"
