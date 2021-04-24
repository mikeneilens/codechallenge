val paschalFullMoonDates = listOf(
    "04-14",
    "04-3",
    "03-23",
    "04-11",
    "03-31",
    "04-18",
    "04-8",
    "03-28",
    "04-16",
    "04-5",
    "03-25",
    "04-13",
    "04-2",
    "03-22",
    "04-10",
    "03-30",
    "04-17",
    "04-7",
    "03-27"
)

fun List<String>.dateWith(year:Int) = ClaimDate("$year-${get(year % 19)}")