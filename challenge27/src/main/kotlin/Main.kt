import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

typealias Shot = Position
typealias ResultMap = MutableMap<Position, Known>

val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

val randomShots:List<Shot> = (0..99).toList().shuffled().map{it.toPosition()}

fun Config.fireShot(shots: List<Shot>, resultsMap: ResultMap): ResultMap {
    val shotsJoined = shots.joinToString("")
    var param = "shots=$shotsJoined" + optionalParameters
    val results = makeRequest(param)
    shots.zip(results).forEach{(shot, result) -> resultsMap[shot] = result }
    return resultsMap
}

fun Config.fireShotsUntilAllSunk(resultsMap: ResultMap):ResultMap {
        var index = 0
        while (!allShipsSunk()) {
            val shot = randomShots[++index]
            if (resultsMap[shot] !is Known) {
                val shots = listOf(shot)
                fireShot(shots,resultsMap)
                if (resultsMap[shot] == Known.Hit) {
                    val hitsOnShip = shots
                        .fireMoreShots(shot.above(), resultsMap, this)
                        .fireMoreShots(shot.below(), resultsMap, this)
                        .fireMoreShots(shot.toLeft(), resultsMap, this)
                        .fireMoreShots(shot.toRight(), resultsMap, this)
                    sinkShip(hitsOnShip, resultsMap)
                } else {
                    if (resultsMap[shot] == Known.Sunk) sinkShip(shots, resultsMap)
                }
            }
        }
        resultsMap.print()
        return resultsMap
}

fun Config.sinkShip(sunkenShip:List<Shot>, resultsMap: ResultMap) {
    println("Ship sunk = $sunkenShip")
    sunkenShip.surroundSunkenShipsWithWater(resultsMap)
    removeShip(sunkenShip)
}

// This returns all successful shots fired so far, i.e. shots resulting in H or S.
fun List<Shot>.fireMoreShots(additionalShots:List<Shot>, resultsMap: ResultMap, config:Config):List<Shot> {

    tailrec fun fireMoreShots(additionalShots:List<Shot> ,shots: List<Shot>, resultsMap: ResultMap, requester:Requester, player:String = "", game:String = ""):List<Shot> {
        //Return if last shot missed or sunk the ship, if there are no additional shots left, if the shots fired is as big as the largest ship or the result of the next shot is known.
        if (resultsMap[shots.last()] != Known.Hit || additionalShots.isEmpty() || resultsMap[additionalShots.first()] is Known || (shots.size >= config.maxShipSize() ) ) return shots
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
            if (resultsMap[position] !is Known) resultsMap[position] = Known.DMZ
        }
    }
}

fun ResultMap.hitOrSunk(shot:Shot) = this[shot] == Known.Hit || this[shot] == Known.Sunk

fun ResultMap.print() {

    println("Shots = ${values.count { it == Known.Sunk || it == Known.Water }}")
    println()
    (0..9).forEach{col ->
        var data = ""
        (0..9).forEach{row ->
            data += this[Position(col,row)]?.text ?: " "
        }
        println(data)
    }
}