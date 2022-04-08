import kotlinx.serialization.Serializable
import model.*

//with Kotlin Serialization nullable types must be given a default value
class DTO {
    @Serializable
    data class FilterRange(
        val min: String,
        val max: String
    )

    @Serializable
    data class Filter(
        val filter: String,
        val value: String? = null,
        val range: FilterRange? = null
    )

    @Serializable
    data class Filtering(
        val groupId: String? = null,
        val filters: List<Filter>? = null
    )

    @Serializable
    data class Card(
        val id: String,
        val cardType: String,
        val name: String,
        val filtering: Filtering? = null
    )
}

fun createCard(card:DTO.Card): Card {
    val id = card.id
    val cardType = card.cardType
    val name = card.name
    val filtering =  if (card.filtering != null ) {
        val groupId = card.filtering.groupId
        val filters = card.filtering.filters?.map(DTO.Filter::toFilter) ?: emptyList()
        Filtering(groupId, filters)
    } else Filtering(null, emptyList())
    return Card(id, cardType, name, filtering)
}

fun DTO.Filter.toFilter() = when (filter) {
        "controlGroup" -> ControlGroup
        "osVersionEquals" -> OsVersionEquals(operatingSystemValue())
        "osVersionGreaterThan" -> OsVersionGreaterThan(operatingSystemValue())
        else -> throw IllegalArgumentException("invalid filter $filter")
    }

fun DTO.Filter.operatingSystemValue() =
    value?.toOperatingSystemVersion()
        ?: throw IllegalArgumentException("filter $filter had no value for operating system")

fun String.toOperatingSystemVersion(): OperatingSystemVersion {
    val parts = split(".")
    if (parts.size != 3) throw IllegalArgumentException("illegal operating system value $this")
    val major = parts[0].toIntOrNull() ?: throw IllegalArgumentException("illegal operating system value $this")
    val minor = parts[1].toIntOrNull() ?: throw IllegalArgumentException("illegal operating system value $this")
    val patch = parts[2].toIntOrNull() ?: throw IllegalArgumentException("illegal operating system value $this")
    return OperatingSystemVersion(major, minor, patch)
}