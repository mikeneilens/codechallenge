import XCTest

//Challene 8 data structures //
struct GBP:Equatable {
    let value:Int
    init (_ value:Int) {
        if value >= 0 {self.value = value}
        else {self.value = -value}
    }
}

enum Group {
    case Red
    case Green
    case Orange
}

struct DevelopmentType {
    struct RentOnly {let rent:GBP}
    struct BuildCostAndRent{let buildCost:GBP; let rent:GBP}
}

protocol Location {}

protocol Purchaseable:Location {
    var name:String { get }
    var purchasePrice:GBP { get }
}

protocol Buildable:Purchaseable {
    var miniStore:DevelopmentType.BuildCostAndRent { get }
    var supermarket:DevelopmentType.BuildCostAndRent { get }
    var megaStore:DevelopmentType.BuildCostAndRent { get }
}

struct FreeParking:Location {}

struct Go:Location {
    let fee = GBP(100)
}

struct FactoryOrWarehouse:Purchaseable{
    let name:String
    let purchasePrice:GBP = GBP(100)
    let rent = GBP(20)
}

struct RetailSite:Buildable {
    let group:Group
    let name:String
    let purchasePrice:GBP
    let undeveloped:DevelopmentType.RentOnly
    let miniStore:DevelopmentType.BuildCostAndRent
    let supermarket:DevelopmentType.BuildCostAndRent
    let megaStore:DevelopmentType.BuildCostAndRent
 }

//Challenge 9

struct Player:Equatable {
    let name:String
}

class GameLedger {
    enum Transaction{
        case creditPlayer(player:Player, amount:GBP)
        case payAnotherPlayer(fromPlayer:Player, toPlayer:Player, amount:GBP)
        case purchaseLocation(location:Purchaseable, player:Player, amount:GBP)
        case buildOnLocation(location:Buildable, building:Building, player:Player, amount:GBP)
    }

    enum Building {
        case minimarket
        case supermarket
        case megastore
    }

    static var transactions = Array<Transaction>()
    
    static func addNew(player:Player, startingBalance:GBP) {
        transactions.append(Transaction.creditPlayer(player: player, amount: startingBalance))
    }
    static func pay(fee:GBP, toPlayer player:Player) {
        transactions.append(Transaction.creditPlayer(player: player, amount: fee))
    }
    static func pay(rent:GBP, from fromPlayer:Player, to toPlayer:Player) {
        transactions.append(Transaction.payAnotherPlayer(fromPlayer: fromPlayer, toPlayer: toPlayer, amount: rent))
    }
    static func purchase(location:Purchaseable, buyer:Player, purchasePrice:GBP) {
        transactions.append(Transaction.purchaseLocation(location: location, player: buyer, amount: purchasePrice))
    }
    static func build(_ building:Building, onLocation location:Buildable, owner:Player, buildCost:GBP) {
        transactions.append(Transaction.buildOnLocation(location: location, building: building, player: owner, amount: buildCost))
    }
 }


class Challenge9Tests: XCTestCase {
    func test_adding_a_new_player() {
        let player = Player(name: "player1")
        GameLedger.addNew(player: player, startingBalance: GBP(150))
        
        if case .creditPlayer(let playerInTransaction, let amountInTransaction)? = GameLedger.transactions.last {
            XCTAssertEqual(playerInTransaction, player)
            XCTAssertEqual(amountInTransaction, GBP(150))
        } else {
            XCTAssertFalse(true)
        }
    }
    
    func test_paying_a_fee() {
        let player = Player(name: "player1")
        GameLedger.pay(fee: GBP(100), toPlayer: player)

        if case .creditPlayer(let playerInTransaction, let amountInTransaction)? = GameLedger.transactions.last {
            XCTAssertEqual(playerInTransaction, player)
            XCTAssertEqual(amountInTransaction, GBP(100))
        } else {
            XCTAssertFalse(true)
        }
    }
    
    func test_paying_rent() {
        let player1 = Player(name: "player1")
        let player2 = Player(name: "player2")
        GameLedger.pay(rent: GBP(20), from: player1, to: player2)
        
        if case .payAnotherPlayer(let fromPlayer, let toPlayer, let amount)? = GameLedger.transactions.last {
            XCTAssertEqual(fromPlayer, player1)
            XCTAssertEqual(toPlayer, player2)
            XCTAssertEqual(amount, GBP(20))
        } else {
            XCTAssertFalse(true)
        }
    }
    
    func test_purchasing_location() {
        let buyer = Player(name: "player1")
        let retailSite = RetailSite(group: .Green, name: "Victoria", purchasePrice: GBP(200),
                                  undeveloped: DevelopmentType.RentOnly(rent: GBP(10)),
                                  miniStore: DevelopmentType.BuildCostAndRent(buildCost: GBP(200), rent: GBP(50)),
                                  supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(500),rent:GBP(100)),
                                  megaStore: DevelopmentType.BuildCostAndRent(buildCost:GBP(1000),rent:GBP(300)))
        
        GameLedger.purchase(location: retailSite, buyer: buyer, purchasePrice: GBP(220))
        
        if case .purchaseLocation(let transactionLocation, let transactionPlayer, let transactionAmount)? = GameLedger.transactions.last {
            XCTAssertEqual(buyer, transactionPlayer)
            XCTAssertEqual(retailSite.name, transactionLocation.name)
            XCTAssertEqual(GBP(220), transactionAmount)
        } else {
            XCTAssertFalse(true)
        }
    }
}

Challenge9Tests.defaultTestSuite.run()
