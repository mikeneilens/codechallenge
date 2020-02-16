val easyProblem = listOf(
    7,0,9,0,0,2,6,8,0,
    0,0,2,0,5,0,7,0,4,
    0,0,0,0,0,0,2,0,0,
    1,9,0,0,0,7,0,6,0,
    8,6,7,1,9,5,0,4,0,
    5,0,4,0,0,0,0,9,0,
    4,3,5,7,8,0,0,2,0,
    0,0,6,4,0,0,0,0,1,
    9,8,0,5,0,6,0,0,3)

val unsolvable = listOf(
    7,8,1,5,4,3,9,2,6,
    0,0,6,1,7,9,5,0,0,
    9,5,4,6,2,8,7,3,1,
    6,9,5,8,3,7,2,1,4,
    1,4,8,2,6,5,3,7,9,
    3,2,7,9,1,4,8,0,0,
    4,1,3,7,5,2,6,9,8,
    0,0,2,0,0,0,4,0,0,
    5,7,9,4,8,6,1,0,3
)

val hardProblem = listOf(
    0,0,0,0,7,4,3,1,6,
    0,0,0,6,0,3,8,4,0,
    0,0,0,0,0,8,5,0,0,
    7,2,5,8,0,0,0,3,4,
    0,0,0,0,3,0,0,5,0,
    0,0,0,0,0,2,7,9,8,
    0,0,8,9,4,0,0,0,0,
    0,4,0,0,8,5,9,0,0,
    9,7,1,3,2,6,4,8,5
)

val multipleAnswerProblem = listOf(
    0,8,0,0,0,9,7,4,3,
    0,5,0,0,0,8,0,1,0,
    0,1,0,0,0,0,0,0,0,
    8,0,0,0,0,5,0,0,0,
    0,0,0,8,0,4,0,0,0,
    0,0,0,3,0,0,0,0,6,
    0,0,0,0,0,0,0,7,0,
    0,3,0,5,0,0,0,8,0,
    9,7,2,4,0,0,0,5,0
)

val dodgyProblem = listOf( //This may cause an incorrect answer depending on how you solve it as positions 34 and 43 could both contain 3.
    2,8,6,1,5,9,7,4,3,
    0,5,0,6,0,8,9,1,2,
    0,1,0,2,0,0,0,6,0,
    8,0,0,0,0,5,0,0,0,
    0,0,0,8,0,4,0,0,0,
    0,0,0,3,0,0,0,0,6,
    0,0,0,0,0,0,0,7,0,
    0,3,0,5,0,0,0,8,0,
    9,7,2,4,0,0,0,5,1
)