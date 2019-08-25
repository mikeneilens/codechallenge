import com.beust.klaxon.FieldRenamer
import com.beust.klaxon.Klaxon

fun parseJsonIntoPubs(jsonString:String):List<Pub>{
    val renamer = object: FieldRenamer {
        override fun toJson(fieldName: String) = fieldName.capitalize()
        override fun fromJson(fieldName: String) = fieldName.toLowerCase()
    }
    val klaxon = Klaxon().fieldRenamer(renamer)

    val pubData = klaxon.parse<PubData>(jsonString)
    return pubData?.pubs ?: listOf<Pub>()
}

