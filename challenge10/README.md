## Challenge 10

**Dice** class uses Kotlin standard random function to generate two values between 1 and 6.

It is assumed the final application will include a constant List of Location to represent all the board locations in correct sequence.

**BoardLocation** class can be initialised with either just a List of Location or a List of Location and the starting position for the player (locationIndex). The starting position is always mod'd to ensure it is never larger than the list size.

A **hasPassedGo** property of BoardLocation is set to true if the locationIndex provided to initialise BoardLocation is after the end of the list. 

n.b. To complete this challenge I copied code from Challenge 8 to enable Locations to be set up for testing.