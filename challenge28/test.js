
var assert = require('assert');

const { shotHitsShip, shipIsSunk, resultOfShot, resultOfShots, parseShots, getResult, saveShots, formatShotData, scoreText, titleText} = require("./app.js");
const { shipsData} = require("./ships.js");

describe('shotHitsShip', function(){
    it('should return true when the co-ordinates of the shot match the co-ordinates of a ship', function(){
        const shot = {column:'A',row:1};
        const ship = {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]};
        
        assert.equal(true, shotHitsShip(shot, ship));
    });

    it('should return false when the co-ordinates of the shot match the co-ordinates of a ship', function(){
        const shot = {column:'A',row:2};
        const ship = {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]};
        
        assert.equal(false, shotHitsShip(shot, ship));
    });
});
describe('shipIsSunk', function() {
    it('should return true when the shots hit every part of the ship', function(){
        const shots =  [{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}];
        const ship = {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]};
        
        assert.equal(true, shipIsSunk(ship, shots));
    });
    it('should return false when the shots do not hit every part of the ship', function(){
        const shots =  [{column:'A',row:1},{column:'B',row:1},{column:'D',row:1}];
        const ship = {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]};
        
        assert.equal(false, shipIsSunk(ship, shots));
    });
});
describe('resultOfShot', function() {
    it('should return M when the shot does not hit any ship', function(){
        const shot =  {column:'B',row:2};
        const shots = [shot];
        const ships = [
                        {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]},
                        {type:"cruiser",positions:[{column:'B',row:3},{column:'B',row:4},{column:'B',row:5}]}
                      ];
        
        assert.equal("M", resultOfShot(shot, ships,shots));
    });

    it('should return H when the shot hits a ship without sinking it', function(){
        const shot =  {column:'B',row:1};
        const shots = [shot];
        const ships = [
                        {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]},
                        {type:"cruiser",positions:[{column:'B',row:3},{column:'B',row:4},{column:'B',row:5}]}
                      ];
        
        assert.equal("H", resultOfShot(shot, ships, shots));
    });

    it('should return S when the shot hits a ship which ends up sinking', function(){
        const shot1 =  {column:'B',row:3};
        const shot2 =  {column:'B',row:4};
        const shot3 =  {column:'B',row:5};
        const shots = [shot1, shot2, shot3];
        const ships = [
                        {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]},
                        {type:"cruiser",positions:[{column:'B',row:3},{column:'B',row:4},{column:'B',row:5}]}
                      ];
        
        assert.equal("S", resultOfShot(shot1, ships, shots));
    });
});

describe('resultOfShots', function() {
    it('should return the result of each shot', function(){
        const shot1 =  {column:'B',row:3}; //sinks the cruiser
        const shot2 =  {column:'B',row:2}; //miss
        const shot3 =  {column:'B',row:4}; //sinks the cruiser
        const shot4 =  {column:'B',row:5}; //sinks the cruiser
        const shot5 =  {column:'A',row:1}; //hits the battleship
        const shot6 =  {column:'C',row:1}; //hits the battleship

        const shots = [shot1,shot2,shot3,shot4,shot5,shot6];
        const ships = [
                        {type:"battleship",positions:[{column:'A',row:1},{column:'B',row:1},{column:'C',row:1},{column:'D',row:1}]},
                        {type:"cruiser",positions:[{column:'B',row:3},{column:'B',row:4},{column:'B',row:5}]}
                      ];
        
        const result1 = "S"; 
        const result2 = "M"; 
        const result3 = "S"; 
        const result4 = "S"; 
        const result5 = "H"; 
        const result6 = "H"; 
        const expectedResults = ["S","M","S","S","H","H"];
        const results = resultOfShots(shots, ships)
        for (let i = 0; i < expectedResults.length; i++) {
            assert.equal(expectedResults[i], results[i]);
        }
    });
});

describe('parseShots', function() {
    it('should return an empty array when the shotdata is empty', function(){
        const shotsData = "";
        const result = parseShots(shotsData);
        assert.equal(0, result.length);
    });
    it('should return an array with one position when the shotdata is contains one shot', function(){
        const shotsData = "A1";
        const result = parseShots(shotsData);
        assert.equal(1, result.length);
        assert.equal("A", result[0].column);
        assert.equal("1", result[0].row);
    });
    it('should return an array with two positions when the shotdata is contains two shots', function(){
        const shotsData = "A1B3";
        const result = parseShots(shotsData);
        assert.equal(2, result.length);
        assert.equal("A", result[0].column);
        assert.equal("1", result[0].row);
        assert.equal("B", result[1].column);
        assert.equal("3", result[1].row);
    });
    it('should return an empty array when the shotdata is invalid', function(){
        const shotsData = "A1B";
        const result = parseShots(shotsData);
        assert.equal(0, result.length);
    });
    it('should return an empty array when the shotdata contains duplicate shots', function(){
        const shotsData = "A1B3A1C4";
        const result = parseShots(shotsData);
        assert.equal(0, result.length);
    });
});

describe('getResult', function() {
    it('should return an empty array when the shotdata is empty', function(){
        const shotsData = "";
        const mockDatastore = createMockDataStore(null);      
        const result = getResult(mockDatastore, shotsData, shipsData).results;
        assert.equal(0, result.length);
    });
    it('should return an array with one result when the shotdata contains one shot', function(){
        const mockDatastore = createMockDataStore(null);      
        const shotsData = "A1";
        const result = getResult(mockDatastore, shotsData, shipsData).results;
        assert.equal(1, result.length);
        assert.equal("M", result[0]);
    });
    it('should return an array with two results when the shotdata contains two shots', function(){
        const mockDatastore = createMockDataStore(null);      
        const shotsData = "A1B3";
        const result = getResult(mockDatastore, shotsData).results;
        assert.equal(2, result.length);
        assert.equal("M", result[0]);
        assert.equal("H", result[1]);
    });
});

const createMockDataStore = () => {
    const mockDatastore = Object.create(null);        
    mockDatastore.key = () => {};
    mockDatastore.data = [];
    mockDatastore.upsert = (items) => {
        mockDatastore.data = items;
        return Promise.resolve(0)};
    return mockDatastore;    
}

describe('saveShots', function() {
    it('should should add nothing to the datastore when there are no shots', function(){
        const mockDatastore =createMockDataStore();                
        saveShots(mockDatastore, "p1", "g1", [], []);
        assert.equal(mockDatastore.data.length,0);
    });
    it('should should add one item to the datastore when there are is 1 shots', function(){
        const mockDatastore =createMockDataStore();        
        saveShots(mockDatastore, "p1", "g1", [{column:'A',row:1}], ["M"]);
        assert.equal(mockDatastore.data.length,1);
    });

});
describe('scoreText', function() {
    it('should return text showing the shots taken by a player correctly when the player is not empty', function(){
        assert.equal(scoreText("mike", 40, 15), "mike:40 shots, 15 hits <br>" );
    });
    it('should return an empty string when the player is empty', function(){
        assert.equal(scoreText("", 40, 15), "" );
    });
});
describe('titleText', function() {
    it('should show a new title if the current game does not match the latest game', function(){
        assert.equal(titleText("g1", "g2"), "<br>Game g2<br>=============================================<br>" );
    });
    it('should return an empty string if the current game matches the latest game', function(){
        assert.equal(titleText("g1", "g1"), "" );
    });
});
describe('formatShotData', function() {
    it('should return text reporting one game with one player if the data contains one row where the player missed', function(){
        const shots=[{game:'g1',player:'p1',result:"M"}];
        assert.equal(formatShotData(shots), "<br>Game g1<br>=============================================<br>p1:1 shots, 0 hits <br>" );
    });
    it('should return text reporting one game with one player if the data contains one row where the player hits a ship', function(){
        const shots=[{game:'g1',player:'p1',result:"H"}];
        assert.equal(formatShotData(shots), "<br>Game g1<br>=============================================<br>p1:1 shots, 1 hits <br>" );
    });
    it('should return text reporting one game with one player if the data contains one row where the player sinks a ship', function(){
        const shots=[{game:'g1',player:'p1',result:"S"}];
        assert.equal(formatShotData(shots), "<br>Game g1<br>=============================================<br>p1:1 shots, 1 hits <br>" );
    });
    it('should return text reporting one game with  one players if the data contains two rows for the same game and player', function(){
        const shots=[{game:'g1',player:'p1',result:"M"},{game:'g1',player:'p1',result:"H"}];
        assert.equal(formatShotData(shots), "<br>Game g1<br>=============================================<br>p1:2 shots, 1 hits <br>" );
    });
    it('should return text reporting one game with  two players if the data contains several rows for the same game and two different players', function(){
        const shots=[{game:'g1',player:'p1',result:"M"}
                    ,{game:'g1',player:'p1',result:"H"}
                    ,{game:'g1',player:'p2',result:"M"}
                    ,{game:'g1',player:'p2',result:"M"}];
        assert.equal(formatShotData(shots), "<br>Game g1<br>=============================================<br>p1:2 shots, 1 hits <br>p2:2 shots, 0 hits <br>" );
    });
    it('should return text reporting two games with  two players if the data contains two games', function(){
        const shots=[{game:'g1',player:'p1',result:"M"}
                    ,{game:'g1',player:'p2',result:"H"}
                    ,{game:'g2',player:'p1',result:"M"}
                    ,{game:'g2',player:'p2',result:"M"}];
        assert.equal(formatShotData(shots), "<br>Game g1<br>=============================================<br>p1:1 shots, 0 hits <br>p2:1 shots, 1 hits <br><br>Game g2<br>=============================================<br>p1:1 shots, 0 hits <br>p2:1 shots, 0 hits <br>" );
    });
});



