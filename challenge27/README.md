### Challenge 27

The main flow is in fireShotsUntilAllSunk. This is too big!

Results of "firing" at a position are stored on a mutable map.

I created a list of positions to fire at in random order. 
Its random to give a chance of not having to look at every space but makes the rest of the logic a bit more complicated.

Reading through each position:
1. Ignore the position if it already has a result on the map.
2. Fire at the position
3. If its a hit find all positions to the left, to the right, above and below the sucessful position, sequenced moving away from the position.
4. Stop firing if the ship is sunk.

Surround any empty positions adjacent to sunk ships on the map with a DMZ on the map so we know not to fire at those positions. 

Repeat until there are 18 'S's on the map.

Thats it.

#### Potential Improvements:
The code will try and find the biggest possible ship even if there are only destroyers left. Should be possbile to only fire off enough shots to sink the biggest remaining ship.