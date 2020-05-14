import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.IOException
import java.net.URL

typealias Shot = String
typealias Result = String
typealias ResultMap = MutableMap<Shot, Result>

val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

data class Data(
    val results: List<String>
)

interface Requester {
    fun makeRequest(param:String="shots=E4"):List<Result>
}

object RequestObject:Requester {
    override fun makeRequest(param:String):List<Result> {
        val response = try {
            URL("https://challenge27.appspot.com/?$param")
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
        }
        val data:Data = mapper.readValue(response)
        return data.results
    }
}

val rows = listOf("A","B","C","D","E","F","G","H","I","J")
val cols = listOf("0","1","2","3","4","5","6","7","8","9")

fun Int.asCoordinate():Shot = rows[this % 10] + cols[this / 10]
fun Shot.asInt():Int {
    val rowAsInt =  rows.indexOf(this[0].toString())
    val colAsInt =  cols.indexOf(this[1].toString())
    return rowAsInt + colAsInt * 10
}

val randomShots:List<Shot> = (0..99).toList().shuffled().map{it.asCoordinate()}

fun fireShot(shots: List<Shot>, resultsMap: ResultMap, requester:Requester, player:String = "", game:String = ""): ResultMap {
    val shotsJoined = shots.joinToString("")
    var param = "shots=$shotsJoined"
    if (player.isNotEmpty()) param = "$param&player=$player"
    if (game.isNotEmpty()) param = "$param&game=$game"
    val results = requester.makeRequest(param)
    results.forEachIndexed { index, result ->
        resultsMap[shots[index]] = result
    }
    return resultsMap
}
fun fireShotsUntilAllSunk(resultsMap: ResultMap, requester: Requester = RequestObject, noOfSquares:Int = 18, player:String = "", game:String = ""):ResultMap {
        var index = 0
        while (!resultsMap.allSunk(noOfSquares)) {
            val shot = randomShots[++index]
            if (resultsMap[shot] == null) {
                val shots = listOf(shot)
                fireShot(shots,resultsMap, requester, player, game)
                if (resultsMap[shot] == "H") {
                    fireMoreShots(Shot::shotToRightOrNull, shots, resultsMap, requester, player, game)
                    fireMoreShots(Shot::shotToLeftOrNull, shots, resultsMap, requester, player, game)
                    fireMoreShots(Shot::shotUpOrNull, shots, resultsMap, requester, player, game)
                    fireMoreShots(Shot::shotDownOrNull, shots, resultsMap, requester, player, game)

                    resultsMap.sinkShips()
                    resultsMap.surroundSunkenShipsWithWater()
                }
            }
        }
        return resultsMap
}

tailrec fun fireMoreShots(getAdditionalShot:Shot.(ResultMap) -> Shot? ,shots: List<Shot>, resultsMap: ResultMap, requester:Requester, player:String = "", game:String = ""):List<Shot> {
    if (resultsMap[shots.last()] != "H" || shots.last().getAdditionalShot(resultsMap) == null ) return shots

    val additionalShot =  shots.last().getAdditionalShot(resultsMap)
    if (additionalShot == null) return shots

    fireShot(shots + additionalShot, resultsMap, requester, player, game)

    return if (resultsMap.hitOrSunk(additionalShot)) fireMoreShots(getAdditionalShot,shots + additionalShot, resultsMap, requester, player, game)
    else shots
}

fun ResultMap.sinkShips() {
    toList().forEach{(shot, result) -> if (result == "H") this[shot] = "S"}
}

fun ResultMap.surroundSunkenShipsWithWater(){
    toList().forEach { (shot, result) ->
        if (result == "S") {
            val locationToReplace = listOf(shot.toRowAbove, shot.toRowBelow, shot.toTheLeft, shot.toTheRight, shot.toLeftAndDown, shot.toLeftAndUp,shot.toRightAndDown, shot.toRightAndUp)
            locationToReplace.forEach { replaceEmptyLocation(it)}
        }
    }
}
fun ResultMap.replaceEmptyLocation(shot:Shot?) {
    if (shot != null && this[shot] == null) this[shot] = "m"
}

fun Shot.shotToRightOrNull(resultsMap: ResultMap):Shot? {
    if (toTheRight != null && toTheRight?.toTheRight != null && resultsMap.hitOrSunk(toTheRight?.toTheRight!!) ) return null
    if (toTheLeft != null && resultsMap[toTheLeft!!] == "H" && toTheRight!= null && resultsMap[toTheRight!!] == null ) return toTheRight
    if (( (toRowAbove != null  && resultsMap[toRowAbove!!] != "H") || toRowAbove == null) && ((toRowBelow != null && resultsMap[toRowBelow!!] != "H") || toRowBelow == null)) return toTheRight
    return null
}
fun Shot.shotToLeftOrNull(resultsMap: ResultMap):Shot? {
    if (toTheLeft != null && toTheLeft?.toTheLeft != null && resultsMap.hitOrSunk(toTheLeft?.toTheLeft!!) ) return null
    if (toTheRight != null && resultsMap[toTheRight!!] == "H" && toTheLeft!= null && resultsMap[toTheLeft!!] == null ) return toTheLeft
    if (( (toRowAbove != null  && resultsMap[toRowAbove!!] != "H") || toRowAbove == null) && ((toRowBelow != null && resultsMap[toRowBelow!!] != "H") || toRowBelow == null)) return toTheLeft
    return null
}
fun Shot.shotUpOrNull(resultsMap: ResultMap):Shot? {
    if (toRowAbove != null && toRowAbove?.toRowAbove != null && resultsMap.hitOrSunk(toRowAbove?.toRowAbove!!) ) return null
    if (toRowBelow != null && resultsMap[toRowBelow!!] == "H" && toRowAbove!= null && resultsMap[toRowAbove!!] == null ) return toRowAbove
    if (( (toTheLeft != null  && resultsMap[toTheLeft!!] != "H") || toTheLeft == null ) && ((toTheRight != null && resultsMap[toTheRight!!] != "H") || toTheRight == null)) return toRowAbove
    return null
}
fun Shot.shotDownOrNull(resultsMap: ResultMap):Shot? {
    if (toRowBelow != null && toRowBelow?.toRowBelow != null && resultsMap.hitOrSunk(toRowBelow?.toRowBelow!!) ) return null
    if (toRowAbove != null && resultsMap[toRowAbove!!] == "H" && toRowBelow!= null && resultsMap[toRowBelow!!] == null ) return toRowBelow
    if (( (toTheLeft != null  && resultsMap[toTheLeft!!] != "H") || toTheLeft == null ) && ((toTheRight != null && resultsMap[toTheRight!!] != "H") || toTheRight == null)) return toRowBelow
    return null
}

fun ResultMap.allSunk(noOfSquares:Int) = (values.filter{it == "S" || it == "H"}.size == noOfSquares)
fun ResultMap.hitOrSunk(shot:Shot) = this[shot] == "H" || this[shot] == "S"

val Shot.toTheRight get() = if (this != "J9" && right[1] == this[1] ) right else null
val Shot.toTheLeft get() = if (this != "A0" && left[1] == this[1] ) left else null
val Shot.toRowAbove get() = if (this[1] != '0' && up[0] == this[0] ) up else null
val Shot.toRowBelow get() = if (this[1] != '9' && down[0] == this[0] ) down else null
val Shot.toRightAndDown:Shot? get() = if (toTheRight != null) toTheRight?.toRowBelow else null
val Shot.toLeftAndDown:Shot? get() = if (toTheLeft != null) toTheLeft?.toRowBelow else null
val Shot.toRightAndUp:Shot? get() = if (toTheRight != null) toTheRight?.toRowAbove else null
val Shot.toLeftAndUp:Shot? get() = if (toTheLeft != null) toTheLeft?.toRowAbove else null


val Shot.left get() = (asInt() - 1).asCoordinate()
val Shot.right get() = (asInt() + 1).asCoordinate()
val Shot.up get() = (asInt() - 10).asCoordinate()
val Shot.down get()  = (asInt() + 10).asCoordinate()

fun ResultMap.print() {
    cols.forEach{number ->
        var data = ""
        rows.forEach{letter ->
            data += (this["$letter$number"] ?: ".")
        }
        println(data)
    }
}