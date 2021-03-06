The ProcessSokobanMove function uses a struct, a protocol and a class:

GameMap (struct)
- this converts a list of strings into a Dictionary (called a Grid) of Position:MapTile and vice-versa. 
- it also contains a delegate to process moves which conforms to protocol MapTileMover.

MapTile (class and sub classes)
- MapTile class (and sub clasess) represents items on the grid. You can create new types of MapTiles by sub-classing MapTile.
- Each MapTile can be placed on top of another MapTile (or nil). A Person or a Block is usually on either an Empty MapTile or a Storage MapTile.

MapTileMover (protocol)
- this deals with moving a player tile on a grid. It contains all the logic to prevent the person moving through walls or pushing person tiles into block tiles. It is implemented using PlayerMover.

Separating the object to create a grid from the object that manages how a person is moved on the grid adds complexity but allows them to be tested separately and games with different rules to be created by just adding another MapTileMover.
