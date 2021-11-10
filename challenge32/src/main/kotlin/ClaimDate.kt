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

    val isFriday = dayOfWeek == DayOfWeek.FRIDAY
    val isWeekend = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY

    val isChristmasDay = dayOfMonth == 25 && month == Month.DECEMBER
    val isBoxingDay = dayOfMonth == 26 && month == Month.DECEMBER
    val isNewYearsDay = dayOfMonth == 1 && month == Month.JANUARY

    val christmasDayIsAtTheWeekend  by lazy {ClaimDate("$year-12-25").isWeekend}
    val boxingDayIsAtTheWeekend by lazy {ClaimDate("$year-12-26").isWeekend}

}

fun LocalDate.toClaimDate():ClaimDate = ClaimDate("$this")
