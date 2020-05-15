import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.IOException
import java.net.URL

typealias Shot = Position
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

val randomShots:List<Shot> = (0..99).toList().shuffled().map{it.toPosition()}

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
                var shots = listOf(shot)
                fireShot(shots,resultsMap, requester, player, game)
                if (resultsMap[shot] == "H") {
                    shots = fireMoreShots(shot.above(), shots, resultsMap, requester, player, game)
                    shots = fireMoreShots(shot.below(), shots, resultsMap, requester, player, game)
                    shots = fireMoreShots(shot.toLeft(), shots, resultsMap, requester, player, game)
                    shots = fireMoreShots(shot.toRight(), shots, resultsMap, requester, player, game)

                    println("Ship sunk = $shots")
                    resultsMap.surroundSunkenShipsWithWater()
                }
            }
        }
        println("Shots = ${resultsMap.values.count { it == "S" || it == "M" }}")
        return resultsMap
}

// This returns all sucessful shots fired so far, i.e. shots resulting in H or S.
tailrec fun fireMoreShots(additionalShots:List<Shot> ,shots: List<Shot>, resultsMap: ResultMap, requester:Requester, player:String = "", game:String = ""):List<Shot> {
    if (resultsMap[shots.last()] != "H" || additionalShots.isEmpty() || resultsMap[additionalShots.first()] != null ) return shots

    val additionalShot =  additionalShots.first()
    fireShot(shots + additionalShot, resultsMap, requester, player, game)
    return if (resultsMap.hitOrSunk(additionalShot)) fireMoreShots(additionalShots.drop(1),shots + additionalShot, resultsMap, requester, player, game)
    else shots
}

fun ResultMap.surroundSunkenShipsWithWater(){
    toList().forEach { (shot, result) ->
        if (result == "S") {
            val surroundingPositions = shot.surrounding()
            surroundingPositions.forEach{position ->
                if (this[position] == null) this[position] = "."
            }
        }
    }
}

fun ResultMap.allSunk(noOfSquares:Int) = (values.filter{it == "S" || it == "H"}.size == noOfSquares)
fun ResultMap.hitOrSunk(shot:Shot) = this[shot] == "H" || this[shot] == "S"

fun ResultMap.print() {
    (0..9).forEach{col ->
        var data = ""
        (0..9).forEach{row ->
            data += (this[Position(col,row)] ?: ".")
        }
        println(data)
    }
}