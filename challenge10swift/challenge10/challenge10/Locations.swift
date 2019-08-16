
let aFactory = FactoryOrWarehouse(name: "A Factory")
let aWarehouse = FactoryOrWarehouse(name: "A Warehouse")
let retailSite1 = RetailSite(group: Group.Red,name: "Site1", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket:DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite2 = RetailSite(group: Group.Red,name: "Site2", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite3 = RetailSite(group: Group.Red,name: "Site3", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite4 = RetailSite(group: Group.Red,name: "Site4", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite5 = RetailSite(group: Group.Red,name: "Site5", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite6 = RetailSite(group: Group.Red,name: "Site6", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite7 = RetailSite(group: Group.Red,name: "Site7", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite8 = RetailSite(group: Group.Red,name: "Site8", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)
let retailSite9 = RetailSite(group: Group.Red,name: "Site9", purchasePrice: GBP(100),
                             undeveloped:DevelopmentType.RentOnly(rent: GBP(10)),
                             miniStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(100),rent: GBP(10)),
                             supermarket: DevelopmentType.BuildCostAndRent(buildCost: GBP(200),rent: GBP(20)),
                             megaStore:DevelopmentType.BuildCostAndRent(buildCost: GBP(300),rent: GBP(30))
)

let locations:Array<Location> = [Go(),retailSite1,retailSite2,aFactory,retailSite3, retailSite4, retailSite5, retailSite6, FreeParking(),retailSite7, retailSite8, aWarehouse, retailSite9]
