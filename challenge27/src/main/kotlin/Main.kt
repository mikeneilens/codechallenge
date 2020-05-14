import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.IOException
import java.net.URL

val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

data class Data(
    val results: List<String>
)

interface Requestor {
    fun makeRequest(parm:String="shots=E4"):List<String>
}

object RequestObject:Requestor {
    override fun makeRequest(parm:String):List<String> {
        val response = try {
            URL("https://challenge27.appspot.com/?$parm")
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
        }
        var data:Data = mapper.readValue(response)
        return data.results
    }
}

