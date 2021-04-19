import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

data class ClaimDate(private val value:String) {

    private val localDate = LocalDate.parse(value)
    val dayOfWeek  = localDate.dayOfWeek
    val dayOfMonth = localDate.dayOfMonth
    val month: Month = localDate.month
    val year = localDate.year

    override fun toString(): String = value
    operator fun plus(days:Number) = localDate.plusDays(days.toLong()).toClaimDate()
    operator fun minus(days:Number) = localDate.minusDays(days.toLong()).toClaimDate()

    fun isFriday() = dayOfWeek == DayOfWeek.FRIDAY
    fun isWeekend() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
}

fun LocalDate.toClaimDate():ClaimDate = ClaimDate("$this")
