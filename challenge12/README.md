# Challenge 12

#### Design decisions

All functions that query the GameLedger are in GameLedgerQueries file. Functions are extensions on GameLedger.

###### balanceFor(Player) 

I created a new sealed class called Balance which contains a Credit and Debt sub classes which each have a property of gbp. To enable the total balance of a player to be calculated I incldued a plus function in the Balance class which automatically creates either a Credit or Debit when adding two balances together.
To get a total balance for the player I filter out all credit transactions for the player and sum them to make a Credit balance filter out all debit transactions to make a Debt blaance and then add Credit and Debt balances together.
 
###### locationsFor(Player)

I created a new class called OwnedLocation which gives the status of the location, e.g. what's been built on it and whether it is mortgaged.
I created a new getter called ownedLocations on GameLedger that does the following to work out who owns each location:

* map each transaction into an OwnedLocation. The status of each location can be derived directly from the transaction in most cases. For transactions where a building is sold the OwnedLocation needs to contain the property smaller than the one sold. Any new transaction types that deal with Location need to be added to the getter here.
 
* reverse the list, so newest ownedLocations appear first.

* use distinctBy to only include the latest status of each location.

* reverse the list back so its in its original order. This was just to help with testing.

###### ownerOf(Location)

Getting the owner of location was easy using the ownedLocations getter I had added to GameLedger as I just had to filter on location.
To calculate the rent payable I added a rentPayable getter to OwnedLocation. For locations that are Buildable then the correct rent had to be selected using a switch statement. For properties that are not buildable the basic rent property was returned. 
When I added an isMortgaged property to OwnedLocation I also ensurede rentPayable always returned zero.

#### Bonus functions

These were unexpectedly fiddly.

###### sellBuilding(Player, Location, GBP)

This was probably the easiest and just had to add a case to the ownedLocations getter on GameLedger to ensure the ownedLocation was correct. I think only unit tests had to be updated.

###### mortgageLocation(Player, Location, GBP)

This was trickier than I thought because I needed a place to indicate the state of a location. I thought I may be able to introduce a Building enum type of Mortgaged (transition states would be Mortgaged, Undeveloped, Minimarket, Supermarket, MegaStore). I was foiled by this as not all Purchaseable locations are Buildable, e.g. factories or warehouses can't have supermarkets on them. So I had to add a new property to OwnedLocation. A lot of the work was still tests though.

###### unmortgageLocation(Player, Location, GBP)

This was straightforward as creating mortgageLocation had dealt with the main issues. The unmortgaged location has an undeveloped state, so if the player didn't sell their buildings on the property before they mortgaged they will have lost them.

###### sellLocation(Player, Player, Location, GBP)

This was tricky as locations that are mortgaged can be sold and stay in their mortgaged state. The last transaction on a location is used to determine its state. A simple solution would be to have a sellMortgagedLocation() function but I didn't think that was very elegent. Instead I decided to do the following within the sellLocation() function:

* Get the status of the location before it was sold. I can use that to tell whether it is mortgaged.

* Create the sellLocation transaction.

* If the location was originally mortgaged, use mortgageLocatoion to create a mortgage transaction for the buyer of the property. The mortgage value is set to zero so the buyer doesn't get paid anything.

 

 

