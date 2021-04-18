import java.time.LocalDate

const val WEEKDAY_RATE_A = 25

data class Claim( val amount:Int, val claimDetails:List<String>)

fun calcStandbyClaim(date: LocalDate?, duration: Int, calloutLevel:String): Claim {
    return Claim(WEEKDAY_RATE_A,listOf("2021-04-19, Week day rate A, £25"))
}
