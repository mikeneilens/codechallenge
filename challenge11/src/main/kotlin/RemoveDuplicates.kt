fun removeDuplicates(pubs:List<Pub>) {
    val sortedPubs = sortIntoDescendingOrder(pubs)

}

fun sortIntoDescendingOrder(pubs:List<Pub>):List<Pub> = pubs.sortedByDescending { it.branch + it.id + it.createTS }

fun distinctValues(pubs:List<Pub>):List<Pub> = pubs.distinctBy { it.branch + it.id }
