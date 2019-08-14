## Challenge 10

It is assumed the final application will include a constant List of Location to represent all the locations used by the game in the correct sequence.

**Dice** class uses Kotlin standard random function to generate two values between 1 and 6.

**BoardLocation** class can be initialised with either just a List of Location or a List of Location and the starting position for the player (locationIndex). The starting position is always mod'd to ensure it is never larger than the list size. A **hasPassedGo** property of BoardLocation is set to true if the locationIndex provided to initialise BoardLocation is after the end of the list of locations. I've provided a funciton to create a new BoardLocation by adding a Dice to an existing BoardLocation.

**Player** class contains a private BoardLocation that can be updated by using the  **move(dice)** method. I've created Getters for currentLocation and hasPassedGo to comply with the _Law Of Demeter_.   


n.b. To complete this challenge I copied code from Challenge 8 to enable Locations to be set up for testing.