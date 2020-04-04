import com.beust.klaxon.FieldRenamer
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException

fun parseJsonIntoPubs(jsonString:String):List<Pub>{
    val renamer = object: FieldRenamer {
        override fun toJson(fieldName: String) = fieldName.capitalize()
        override fun fromJson(fieldName: String) = fieldName.toLowerCase()
    }
    val klaxon = Klaxon().fieldRenamer(renamer)

    return try {
        val pubData = klaxon.parse<PubData>(jsonString)
        pubData?.pubs ?: listOf()
    } catch (e:KlaxonException)  {
        listOf()
    }
}

