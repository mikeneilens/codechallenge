
fun possibleLunch(fruits:List<Fruit>, result:Set<Set<Fruit>> = emptySet()):Set<Set<Fruit>> =
    if (fruits.isEmpty()) result
    else {
        val lunches = result + result.map{ it + fruits.first()} + setOf(setOf(fruits.first())) 
        possibleLunch(fruits.drop(1), lunches)
    }