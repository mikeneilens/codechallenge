The ProcessSokobanMove function uses two main structs:

GameMap 
- this converts a list of strings into a Dictionary (called a Grid) of Position:MapTile and vice-versa. 
- it also contains a delegate to process moves which conforms to protocol MapTileMover.

MapTileMover
- this deals with moving a player tile on a grid. It contains all the logic to prevent the person moving through walls or pushing person tiles into block tiles.

Separating the object to create a grid from the object that manages how a person is moved on the grid adds complexity but allows the two concerns to be tested separately and games with different rules to be created by just adding another MapTileMover.

The MapTile enum respresents the different types of tiles that can appear on a grid. It is tightly coupled to the MapTileMover so extending that would requiree updating any MapTileMovers - not sure how that could be separated.

All of the logic for controlling how the game is displayed is in class ViewController which dynamically creates and adds UIViews to the main view depending on the size of the grid within the GameMap.
