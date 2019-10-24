typealias StringContainingCSV = String
typealias ObjectCreator<Object> = (List<String>)->Object?

fun <ObjectType:Any>StringContainingCSV.toListOfObjects(noOfProperties:Int, objectCreator:ObjectCreator<ObjectType>): List<ObjectType> =
    this.split(",")
        .windowed(noOfProperties)
        .mapNotNull{objectCreator(it)}


