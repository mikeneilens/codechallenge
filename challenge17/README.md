### Challenge 17

In the game of Pontoon the biggest single issue seems to be how to cope with an Ace having two values, ie. One or Eleven. To cope with this I decided that any card can have a list of values so every card gets treated the same when calculating the total. To calculate the potential values of a hand do the following:
1. Start with a list containing 0. i.e. [0]. This is the accumulator.

2. With the first card, add every value for the card to every value in the accumulator. 
So if a card is a 2 of hearts add [2] to the [0], giving [2]. If a card is an Ace add 1 and 11 to [0] giving [1,11].

3. Repeat for every card in the hand. So if the accumulator contains [3,13] and the next card is an ace the new value of the accumulator is [(3+1),(3+11),(13 +1),(13 +11)] which evaluates to [4,14,14,24].

4. After adding every card to the accumulator sort into descending order and find the highest value below 22 to find the total for the hand. If there is no total below 22 then the hand is "bust".

A separate function is used to determine whether a hand is a Pontoon, Five Card Trick etc. These are added to a Sequence and processed sequentially by function `playerBeatsDealer` to determine whether the players hand or dealers hand has won.

A Swift version is in https://github.com/mikeneilens/codechallenge/blob/master/challenge17/challenge17.playground/Contents.swift
