# Challenge 12

#### Design decisions

All functions that query the GameLedger are in GameLedgerQueries file. Functions are extensions on GameLedger.

###### balanceFor(Player) 

I created a new sealed class called Balance which contains a Credit and Debt sub classes which each have a property of gbp. To enable the total balance of a player to be calculated I incldued a plus function in the Balance class which automatically creates either a Credit or Debit when adding two balances together.
To get a total balance for the player I filter out all credit transactions for the player and sum them to make a Credit balance, filter out all debit transactions to make a Debt blaance and then add Credit and Debt balances together.
 
###### locationsFor(Player)

I created a new class called LocationStatus which gives the status of the location, e.g. what's been built on it and whether it is mortgaged.
I created a new getter called locationStatuses on GameLedger that does the following to work out who owns each location:

* map each transaction into an LocationStatus. The status of each location can be derived directly from the transaction in most cases. For transactions where a building is sold the LocationStatus needs to contain the property smaller than the one sold. Any new transaction types that deal with Location need to be added to the getter here.
 
* reverse the list, so newest locationStatuses appear first.

* use distinctBy to only include the latest status of each location.

* reverse the list back so its in its original order. This was just to help with testing.

###### ownerOf(Location)

Getting the owner of location was easy using the locationStatuses getter I had added to GameLedger as I just had to filter on location.
To calculate the rent payable I added a rentPayable getter to LocationStatus. For locations that are Buildable then the correct rent had to be selected using a switch statement. For properties that are not buildable the basic rent property was returned. 
When I added an isMortgaged property to LocationStatus I also ensurede rentPayable always returned zero.

#### Bonus functions

These were unexpectedly fiddly.

###### sellBuilding(Player, Location, GBP)

This was probably the easiest and just had to add a case to the locationStatuses getter on GameLedger to ensure the locationStatuse was correct. I think only unit tests had to be updated.

###### mortgageLocation(Player, Location, GBP)

This was trickier than I thought because I needed a place to indicate the state of a location. I thought I may be able to introduce a Building enum type of Mortgaged (transition states would be Mortgaged, Undeveloped, Minimarket, Supermarket, MegaStore). I was foiled by this as not all Purchaseable locations are Buildable, e.g. factories or warehouses can't have supermarkets on them. So I had to add a new property to LocationStatus. A lot of the work was still tests though.

###### unmortgageLocation(Player, Location, GBP)

This was straightforward as creating mortgageLocation had dealt with the main issues. The unmortgaged location has an undeveloped state, so if the player didn't sell their buildings on the property before they mortgaged they will have lost them.

###### sellLocation(Player, Player, Location, GBP)

The complication here is that if you can sell a mortgaged location. To get around this I created a seperate transaction and function for selling mortgaged locations.
 
###### sellMortgagedLocation(Player, Player, Location, GBP)

This was straightforward
 

