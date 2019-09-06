# Challenge 12

#### Design decisions

All functions that query the GameLedger are in GameLedgerQueries file.

###### BalanceFor(Player)

I created a new sealed class called Balance which contains a Credit and Debt sub classes which each have a property of gbp. To enable the total balance of a player to be calculated I incldued a plus function in the Balance class which automatically creates either a Credit or Debit when adding two balances together.
To get a total balance for the player I filter out all credit transactions for the player and sum them to make a Credit balance filter out all debit transactions to make a Debt blaance and then add Credit and Debt balances together.
 
###### BalanceFor(Playe