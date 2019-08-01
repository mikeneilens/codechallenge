**Challenge 9 - Kotlin**

The GameLedger is created as a singleton simply by creating a object rather than a class and then an instance of the class.

GameLedger contains a mutable list of _Transaction_. 
I have followed a similar pattern to Challenge 8 as I have used a hierarchy of interfaces instead of abstract classes or sealed classes to define different types of Transaction.

The _Transaction_ interface contains a single property which is _amount_:GBP as these are financial transactions. 

Every transaction has either a _playerCredited_ or _playerDebited_ property which are represented by intefaces _CreditTransaction_ and _DebitTransaction_ which are both children of Transaction. 

The interface for when one player pays rent to another, _PlayerPayingAnotherPlayer_, is a child of both CreditTransaction and DebitTransaction interfaces.
Other interfaces are _PlayerPurchasingProperty_ and _PlayerBuildingOnLocation_ which are both children of DebitTransaction.

Using this approach it should be possible to determine all money paid to a player by filtering on all transactions of type CreditTransaction rather than having to filter on lots of different types of transaction and updating the filter each time a new transaction type is invented.

As all transactions are indepent entities that only contain properties defined in their interfaces I've not bothered creating any classes for each different type of transaction and just added objects directly to the list of transactions in the GameLedger.

**Testing**

Using a Singleton for GameLedger had an interesting side effect when testing. As the unit tests can run in any sequence (unless you put them in one big test function) you have to either interogate the last element added to the list of transactions rather than element at position [x] as its not possible to know what [x] should be. There is a risk that two tests run concurrently which would mess up that approach but I've not encountered that problem. 

**Challenge 9 - Swift**

I've taken a different approach to the approach I took with Kotlin. The GameLedger is a struct containing static properties including a mutable array of _Transaction_.

Transaction is an enum with associated values, with each case representing a different kind of transaction. This is very similar to a Sealed Class in Kotlin.

This approach works well when creating transactions and makes the code nice and compact.
 
I did a test to obtain the total amount that a player has been credited with in the GameLedger which required much more code than doing the same in Kotlin using an interface based appraoch. This was because I had to create a switch statement that checks every possible case of Transaction, using pattern matching to get info about the player and amount for each transaction of the right type. Adding new types of transactions would break the code as Swift (helpfully) insists that the switch contains every possible case. Adding a default case would shorten the code but it's usually better for the code to break when new transactions are added than to continue working (potentually incorrectly).
