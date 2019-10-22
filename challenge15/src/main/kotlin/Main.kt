typealias StringContainingCSV = String

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:(List<String>)->ObjectType?): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties)
        .mapNotNull{objectCreator(it)}
