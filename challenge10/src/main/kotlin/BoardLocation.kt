class BoardLocation(locations:List<Location>) {
    val locations:List<Location>
    private var locationIndex = 0

    fun currentLocation():Location = this.locations[locationIndex]

    init {
        this.locations = locations
    }

    constructor (locations:List<Location>, locationIndex:Int):this(locations) {
        this.locationIndex = locationIndex
    }

    operator fun plus(other:Dice):BoardLocation {
        val newLocation = (this.locationIndex + other.totalValue) % locations.size
        return BoardLocation(this.locations, newLocation)
    }
}

val aFactory = FactoryOrWarehouse("A Factory")
val aWarehouse = FactoryOrWarehouse("A Warehouse")
val retailSite1 = RetailSite(Group.Red,"Site1", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite2 = RetailSite(Group.Red,"Site2", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite3 = RetailSite(Group.Red,"Site3", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite4 = RetailSite(Group.Red,"Site4", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite5 = RetailSite(Group.Red,"Site5", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite6 = RetailSite(Group.Red,"Site6", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite7 = RetailSite(Group.Red,"Site7", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite8 = RetailSite(Group.Red,"Site8", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)
val retailSite9 = RetailSite(Group.Red,"Site9", GBP(100),
    undeveloped = DevelopmentType.RentOnly(GBP(10)),
    miniStore = DevelopmentType.BuildCostAndRent(GBP(100),GBP(10)),
    supermarket =  DevelopmentType.BuildCostAndRent(GBP(200),GBP(20)),
    megastore = DevelopmentType.BuildCostAndRent(GBP(300),GBP(30))
)

val locations = listOf(Go,retailSite1,retailSite2,aFactory,retailSite3, retailSite4, retailSite5, retailSite6, FreeParking,
    retailSite7, retailSite8, aWarehouse, retailSite9)