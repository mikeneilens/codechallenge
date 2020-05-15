import com.fasterxml.jackson.module.kotlin.readValue
import java.io.IOException
import java.net.URL

class Config (private val requester:Requester = RequestObject, val game:String = "", val player:String = "") :Requester {
    override fun makeRequest(param: String) = requester.makeRequest(param)
}

interface Requester {
    fun makeRequest(param:String="shots=E4"):List<Result>
}

data class WebServiceData(
    val results: List<String>
)

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
        val data:WebServiceData = mapper.readValue(response)
        return data.results.map{it.toResult()}
    }
}
