fun obtainListOfBeers(jsonString:String): List<Beer> = parseJsonIntoPubs(jsonString).removeDuplicates().flattenEachPub()

fun List<Pub>.removeDuplicates()= this.sortIntoDescendingOrder().removeDuplicatePubs()

fun List<Pub>.sortIntoDescendingOrder() = this.sortedByDescending {pub -> pub.branch + pub.id + pub.createTS }

fun List<Pub>.removeDuplicatePubs() = this.distinctBy {pub ->  pub.branch + pub.id }

fun List<Pub>.flattenEachPub():List<Beer> {
    val beers =  flatMap{pub -> pub.mapRegularBeers()} + this.flatMap{pub -> pub.mapGuestBeers()}
    return beers.sortedBy { it.name + it.pubName }
}

fun Pub.mapRegularBeers():List<Beer> = this.regularBeers.map{regularBeer ->  Beer(regularBeer, this.name, this.pubService, true)}

fun Pub.mapGuestBeers():List<Beer> = this.guestBeers.map{guestBeer -> Beer(guestBeer, this.name, this.pubService, false)}
