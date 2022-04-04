import kotlinx.serialization.Serializable

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