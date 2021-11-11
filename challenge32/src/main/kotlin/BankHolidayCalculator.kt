import kotlinx.datetime.*

interface SimpleDate {
    val dayOfWeek:DayOfWeek
    val dayOfMonth:Int
    val month:Month
    val year:Int

    operator fun plus(days:Number):SimpleDate
    operator fun minus(days:Number):SimpleDate
}

interface BankHolidayCalculator {
    fun isBankHoliday(date:SimpleDate):Boolean
    fun isChristmasDayHoliday(date:SimpleDate):Boolean
    fun isBoxingDayHoliday(date: SimpleDate):Boolean
    fun isNewYearsDayHoliday(date: SimpleDate):Boolean
}

object UKHolidayCalculator:BankHolidayCalculator {
    private val holidayValidators by lazy { listOf(
        ::isNewYearsDayHoliday,
        ::isGoodFridayHoliday,
        ::isEasterMondayHoliday,
        ::isMayDayHoliday,
        ::isSpringHoliday,
        ::isSummerHoliday,
        ::isChristmasDayHoliday,
        ::isBoxingDayHoliday,
    )}

    override fun isBankHoliday(date:SimpleDate) = holidayValidators.any{it(date)}

    override fun isChristmasDayHoliday(date: SimpleDate) =
        date.month == Month.DECEMBER && (
                  (date.dayOfMonth == 25 && date.dayOfWeek.isWeekDay())
                ||(date.dayOfMonth == 26 && date.dayOfWeek == DayOfWeek.MONDAY )
                ||(date.dayOfMonth == 27 && date.dayOfWeek == DayOfWeek.MONDAY ))

    override fun isBoxingDayHoliday(date: SimpleDate) =
        date.month == Month.DECEMBER && (
                  (date.dayOfMonth == 26 && date.dayOfWeek.isWeekDay() && date.dayOfWeek != DayOfWeek.MONDAY)
                ||(date.dayOfMonth == 28 && date.dayOfWeek == DayOfWeek.MONDAY )
                ||(date.dayOfMonth == 27 && date.dayOfWeek == DayOfWeek.TUESDAY )
                ||(date.dayOfMonth == 28 && date.dayOfWeek == DayOfWeek.TUESDAY ))

    override fun isNewYearsDayHoliday(date: SimpleDate) =
        date.month == Month.JANUARY && (
                    (date.dayOfMonth == 1 && date.dayOfWeek.isWeekDay() )
                            ||(date.dayOfMonth == 2 && date.dayOfWeek == DayOfWeek.MONDAY )
                            ||(date.dayOfMonth == 3 && date.dayOfWeek == DayOfWeek.MONDAY ))

    fun isMayDayHoliday(date: SimpleDate) =
        date.month == Month.MAY && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth < 8

    fun isSpringHoliday(date: SimpleDate) =
        date.month == Month.MAY && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth > 24

    fun isSummerHoliday(date: SimpleDate) =
        date.month == Month.AUGUST && date.dayOfWeek == DayOfWeek.MONDAY && date.dayOfMonth > 24

    fun isEasterMondayHoliday(date: SimpleDate) = (date -1).month == easterSunday(date.year).month && (date -1).dayOfMonth == easterSunday(date.year).dayOfMonth

    fun isGoodFridayHoliday(date: SimpleDate) = (date + 2).month == easterSunday(date.year).month && (date + 2).dayOfMonth == easterSunday(date.year).dayOfMonth

    fun easterSunday(year:Int) = sundayAfter(paschalFullMoonDates.dateWith(year))

    private fun sundayAfter(date:LocalDate) = date - (date.dayOfWeek.value % 7) + 7

    private fun DayOfWeek.isWeekDay() = this in listOf(DayOfWeek.MONDAY,DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY,DayOfWeek.FRIDAY)

    private operator fun LocalDate.plus(days:Int) = plus(days.toLong(),DateTimeUnit.DAY)
    private operator fun LocalDate.minus(days:Int) = minus(days.toLong(),DateTimeUnit.DAY)
}
