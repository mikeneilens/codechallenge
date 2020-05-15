### Challenge 27

The main flow is in fireShotsUntilAllSunk. This is too big!

Results of "firing" at a position are stored on a mutable map.

I created a list of positions to fire at in random order. 
Its random to give a chance of not having to look at every space but makes the rest of the logic a bit more complicated.

Reading through each position:
1. Ignore the position if it already has a result on the map.
2. Fire at the position
3. If its a hit find all positions to the left, to the right, above and below the sucessful position, sequenced moving away from the position.
4. Fire in available positions above and below the last hit. Stop firing if the ship is sunk.
5. Fire in available positions to the left and right of the last hit. Stop firing if the ship is sunk. 

Surround any empty positions adjacent to 'S' on the map with water on the map so we know not to fire at them. 

Repeat until there are 18 'S's on the map.

Thats it.

Improvements:
The way the sunk ships are surrounded with water is a bit crude and repetitive.
Could pass round the player/game/requester and maybe the mutable map as a single object.