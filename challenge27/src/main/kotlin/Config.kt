import com.fasterxml.jackson.module.kotlin.readValue
import java.io.IOException
import java.net.URL

class Config (private val requester:Requester = RequestObject,
              val game:String = "",
              val player:String = "",
              private val ships:MutableList<Int> = mutableListOf(5,4,3,2,2,1,1) ) :Requester {

    override fun makeRequest(param: String) = requester.makeRequest(param)

    val optionalParameters:String  by lazy {
         (if (player.isNotEmpty()) "&player=$player" else "") +
         (if (game.isNotEmpty()) "&game=$game" else "")
    }

    fun removeShip(positions:List<Position>) {
        ships.remove(positions.size)
    }

    fun maxShipSize() = ships.max() ?: 0

    fun noOfShipsRemaining() = ships.size
}

interface Requester {
    fun makeRequest(param:String="shots=E4"):List<Known>
}

data class WebServiceData(
    val results: List<String>
)

object RequestObject:Requester {
    override fun makeRequest(param:String):List<Known> =
        mapper.readValue<WebServiceData>(
            try {
                URL("https://challenge27.appspot.com/?$param")
                    .openStream()
                    .bufferedReader()
                    .use { it.readText() }
            } catch (e: IOException) {
                "Error with ${e.message}."
            }
        ).results.map{it.toKnown()}
}
