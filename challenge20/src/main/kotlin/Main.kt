import java.net.URL

fun httpRequest(url:String):String
    = try {
    URL(url)
        .openStream()
        .bufferedReader()
        .use { it.readText() }
    } catch (e:Error) {
        ""
    }
