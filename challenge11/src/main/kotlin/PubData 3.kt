class PubData(val pubs:List<Pub>)
class Pub(
    val name: String,
    val branch: String,
    val id: String,
    val createTS:String,
    val pubService:String,
    val regularBeers:List<String> = listOf<String>(),
    val guestBeers:List<String> = listOf<String>()
)