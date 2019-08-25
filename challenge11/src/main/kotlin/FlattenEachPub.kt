fun List<Pub>.flattenPub() {
    val beers = mutableListOf<Beer>()
    for ( pub in this  ) {
        beers += pub.flattenRegularBeers()
        beers += pub.flattenGuestBeers()
    }
}

fun Pub.flattenRegularBeers():List<Beer> {
    val beers = mutableListOf<Beer>()
    for (beerText in this.regularBeers) {
        beers.add(Beer(beerText, name, pubService, true))
    }
    return beers
}
fun Pub.flattenGuestBeers():List<Beer> {
    val beers = mutableListOf<Beer>()
    for (beerText in this.guestBeers) {
        beers.add(Beer(beerText, name, pubService, false))
    }
    return beers
}