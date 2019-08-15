import Foundation

func createLocations() -> Go {

    var go = Go()
    let retailSite9 = RetailSite(group: Group.Red,name: "Site9", nextLocation: go, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let aWarehouse = FactoryOrWarehouse(name: "A Warehouse", nextLocation: retailSite9)
    let retailSite8 = RetailSite(group: Group.Red,name: "Site8", nextLocation: aWarehouse, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let retailSite7 = RetailSite(group: Group.Red,name: "Site7",nextLocation: retailSite8, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let freeparking = FreeParking(nextLocation: retailSite7)
    let retailSite6 = RetailSite(group: Group.Red,name: "Site6", nextLocation: freeparking, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let retailSite5 = RetailSite(group: Group.Red,name: "Site5", nextLocation: retailSite6, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let retailSite4 = RetailSite(group: Group.Red,name: "Site4", nextLocation: retailSite5, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let aFactory = FactoryOrWarehouse(name: "A Factory", nextLocation: retailSite4)
    let retailSite3 = RetailSite(group: Group.Red,name: "Site3", nextLocation: aFactory, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let retailSite2 = RetailSite(group: Group.Red,name: "Site2", nextLocation: retailSite3,purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    let retailSite1 = RetailSite(group: Group.Red,name: "Site1", nextLocation: retailSite2, purchasePrice: GBP(100),
                                 undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                                 miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                                 supermarket:DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                                 megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
    )
    go.add(location: retailSite1)
    return go
}

let go = createLocations()
