typealias Lunches = Set<Fruit>

fun possibleLunch(fruits:List<Fruit>, result:Set<Lunches> = emptySet()):Set<Lunches> =
    if (fruits.isEmpty()) result
    else {
        val lunches = result.addFruit(fruits.first())
        possibleLunch(fruits.drop(1), lunches)
    }

private fun Set<Lunches>.addFruit(fruit: Fruit) = this + map { it + fruit } + setOf(setOf(fruit))