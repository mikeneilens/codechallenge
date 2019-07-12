import XCTest

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

struct FreeParking:Location {}

struct Go:Location {
    let fee = GBP(100)
}

struct FactoryOrWarehouse:Location{
    let name:String
    let purchasePrice:GBP = GBP(100)
    let rent = GBP(20)
}

struct RetailSite:Location {
    let group:Group
    let name:String
    let purchasePrice:GBP
    let undeveloped:DevelopmentType.RentOnly
    let miniStore:DevelopmentType.BuildCostAndRent
    let supermarket:DevelopmentType.BuildCostAndRent
    let megaStore:DevelopmentType.BuildCostAndRent
}

class Challenge8Tests: XCTestCase {
    func test_FreeParking_Location() {
        let freeParking = FreeParking()
        
        XCTAssertTrue(freeParking is FreeParking)
    }
    
    func test_Go_Location() {
        let go = Go()
        
        XCTAssertTrue(go is Go)
        XCTAssertEqual(GBP(100), go.fee)
    }

    func test_FactoryWarehouse_Location() {
        let warehouse = FactoryOrWarehouse(name: "Magna Park")
        
        XCTAssertTrue(warehouse is FactoryOrWarehouse)
        XCTAssertEqual("Magna Park", warehouse.name)
        XCTAssertEqual(GBP(100), warehouse.purchasePrice)
        XCTAssertEqual(GBP(20), warehouse.rent)
    }
    
    func test_RetailSite() {
        let retailSite = RetailSite(group: .Green, name: "Victoria", purchasePrice: GBP(200),
                                             undeveloped: DevelopmentType.RentOnly(rent: GBP(10)),
                                             miniStore: DevelopmentType.BuildCostAndRent(buildCost: GBP(200), rent: GBP(50)),
                                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(500),rent:GBP(100)),
                                             megaStore: DevelopmentType.BuildCostAndRent(buildCost:GBP(1000),rent:GBP(300)))
        
        XCTAssertTrue(retailSite is RetailSite)
        XCTAssertEqual(Group.Green , retailSite.group)
        XCTAssertEqual("Victoria", retailSite.name)
        XCTAssertEqual(GBP(200), retailSite.purchasePrice)
        XCTAssertEqual(GBP(10), retailSite.undeveloped.rent)
        XCTAssertEqual(GBP(200), retailSite.miniStore.buildCost)
        XCTAssertEqual(GBP(50), retailSite.miniStore.rent)
        XCTAssertEqual(GBP(500), retailSite.supermarket.buildCost)
        XCTAssertEqual(GBP(100), retailSite.supermarket.rent)
        XCTAssertEqual(GBP(1000), retailSite.megaStore.buildCost)
        XCTAssertEqual(GBP(300), retailSite.megaStore.rent)
    }
    
}

//usage
func printTypeOf(location:Location) {
    switch location {
        case is FreeParking: print("Free Parking")
        case let go as Go: print("Go. Fee is \(go.fee)")
        case let factory as  FactoryOrWarehouse: print("Factory or warehouse. Rent is \(factory.rent)")
        case let retailSite as RetailSite: print("Retail Site. Name is \(retailSite.name)")
        default: print("illegal type")
    }
}

let f = FactoryOrWarehouse(name: "Beechams")
let g = Go()
printTypeOf(location: f)
printTypeOf(location: g)
let listOfLocations:Array<Location> = [f,g]

Challenge8Tests.defaultTestSuite.run()
