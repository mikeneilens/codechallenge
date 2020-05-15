### Challenge 27

The main flow is in fireShotsUntilAllSunk. This is too big!

Results of "firing" at a position are stored on a mutable map.

I created a list of positions to fire at in random order. 
Its random to give a chance of not having to look at every space but makes the rest of the logic a bit more complicated.

Reading through each position:
1. Ignore the position if it already has a result on the map.
2. Fire at the position
3. If its a hit find all positions to the left, to the right, above and below the sucessful position, sequenced moving away from the position.
4. If we know there is no ship above or below the position then try firing in the left or right positions. I think this maybe a pointless check.
5. If we know there is no ship left or right of the position then try firing in the above or below positions. 

After steps 3-5 we have sunk the ship so mark any hits on the map with an 'S'.
Surround any empty positions adjacent to 'S' on the map with water on the map so we know not to fire at them. 

Repeat until there are 18 'S's on the map.

Thats it.

Improvements:
In theory could keep a record of ships that have been hit, e.g. its pointless firing more than 3 shots in a row if the 3 biggest shots are already sunk.
Could also pass round the player/game/requester and maybe the mutable map as a single object.