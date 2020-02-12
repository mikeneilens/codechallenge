import java.net.URL

typealias Httprequest = (String) -> String

fun httpRequest(url:String):String = try {
    URL(url)
        .openStream()
        .bufferedReader()
        .use { it.readText() }
    } catch (e:Error) {
        ""
    }

data class MapResponse (val referenceId:String, val points:List<String>)

fun parseResponse(text:String): MapResponse {
    val splitData = text.split(",")
    return MapResponse(splitData.first(), splitData.drop(1))
}

enum class Command{ M,L,R }

fun decideAction(mapResponse: MapResponse):Command {
    return Command.M
}