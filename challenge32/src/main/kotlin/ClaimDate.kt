import kotlinx.datetime.*

data class ClaimDate(private val value:String) {

    private val localDate = LocalDate.parse(value)
    val dayOfWeek = localDate.dayOfWeek
    val dayOfMonth = localDate.dayOfMonth
    val month = localDate.month
    val year = localDate.year

    override fun toString() = value
    operator fun plus(days:Number) = localDate.plus(days.toLong(), DateTimeUnit.DAY).toClaimDate()
    operator fun minus(days:Number) = localDate.plus(-days.toLong(), DateTimeUnit.DAY).toClaimDate()

    fun isFriday() = dayOfWeek == DayOfWeek.FRIDAY
    fun isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
}

fun LocalDate.toClaimDate():ClaimDate = ClaimDate("$this")
