fun List<Pub>.removeDuplicates()= this.sortIntoDescendingOrder().distinctValues()

fun List<Pub>.sortIntoDescendingOrder() = this.sortedByDescending { it.branch + it.id + it.createTS }

fun List<Pub>.distinctValues():List<Pub> = this.distinctBy { it.branch + it.id }
