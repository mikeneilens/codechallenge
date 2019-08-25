fun obtainListOfBeers(jsonString:String): List<Beer> = parseJsonIntoPubs(jsonString).removeDuplicates().flattenEachPub()

