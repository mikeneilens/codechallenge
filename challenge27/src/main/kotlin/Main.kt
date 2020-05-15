import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

typealias Shot = Position
typealias ResultMap = MutableMap<Position, Result>

val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

val randomShots:List<Shot> = (0..99).toList().shuffled().map{it.toPosition()}

fun Config.fireShot(shots: List<Shot>, resultsMap: ResultMap): ResultMap {
    val shotsJoined = shots.joinToString("")
    var param = "shots=$shotsJoined" + optionalParameters
    val results = makeRequest(param)
    shots.zip(results).forEach{(shot, result) -> resultsMap[shot] = result }
    return resultsMap
}

fun Config.fireShotsUntilAllSunk(resultsMap: ResultMap, noOfSquares:Int = 18):ResultMap {
        var index = 0
        while (!resultsMap.allSunk(noOfSquares)) {
            val shot = randomShots[++index]
            if (resultsMap[shot] !is Result) {
                val shots = listOf(shot)
                fireShot(shots,resultsMap)
                if (resultsMap[shot] == Result.Hit) {
                    val sunkenShip = shots
                        .fireMoreShots(shot.above(), resultsMap, this)
                        .fireMoreShots(shot.below(), resultsMap, this)
                        .fireMoreShots(shot.toLeft(), resultsMap, this)
                        .fireMoreShots(shot.toRight(), resultsMap, this)

                    println("Ship sunk = $sunkenShip")
                    sunkenShip.surroundSunkenShipsWithWater(resultsMap)
                }
            }
        }
        resultsMap.print()
        return resultsMap
}

// This returns all successful shots fired so far, i.e. shots resulting in H or S.
fun List<Shot>.fireMoreShots(additionalShots:List<Shot>, resultsMap: ResultMap, config:Config):List<Shot> {

    tailrec fun fireMoreShots(additionalShots:List<Shot> ,shots: List<Shot>, resultsMap: ResultMap, requester:Requester, player:String = "", game:String = ""):List<Shot> {
        if (resultsMap[shots.last()] != Result.Hit || additionalShots.isEmpty() || resultsMap[additionalShots.first()] is Result ) return shots

        val additionalShot =  additionalShots.first()
        config.fireShot(shots + additionalShot, resultsMap)
        return if (resultsMap.hitOrSunk(additionalShot)) fireMoreShots(additionalShots.drop(1),shots + additionalShot, resultsMap, requester, player, game)
        else shots
    }

    return fireMoreShots(additionalShots,this, resultsMap, config)
}

fun List<Shot>.surroundSunkenShipsWithWater(resultsMap:ResultMap){
    forEach { shot ->
        val surroundingPositions = shot.surrounding()
        surroundingPositions.forEach{position ->
            if (resultsMap[position] !is Result) resultsMap[position] = Result.DMZ
        }
    }
}

fun ResultMap.allSunk(noOfSquares:Int) = (values.filter{it == Result.Sunk || it == Result.Hit}.size == noOfSquares)
fun ResultMap.hitOrSunk(shot:Shot) = this[shot] == Result.Hit || this[shot] == Result.Sunk

fun ResultMap.print() {

    println("Shots = ${values.count { it == Result.Sunk || it == Result.Water }}")
    println()
    (0..9).forEach{col ->
        var data = ""
        (0..9).forEach{row ->
            data += this[Position(col,row)]?.toString() ?: " "
        }
        println(data)
    }
}