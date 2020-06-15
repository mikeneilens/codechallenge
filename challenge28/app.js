const { shipsData} = require("./ships.js");

const getResult = (datastore, shotData, player, game) => {
  const ships = shipsData(game);
  const shots = parseShots(shotData);
  const results = resultOfShots(shots, ships);
  if (player != "TESTPLAYER") saveShots(datastore, player, game, shots, results);
  return {results};
}

const parseShots = (shotData) => {
  if (shotData === "") return [];

  const dataChunks = shotData.match(/.{1,2}/g);
  let result = [];
  for (let i = 0; i < dataChunks.length; i++) {
    const chunk = dataChunks[i];
    if (chunk.length != 2) return [];
    const column = chunk.charAt(0);
    const row = chunk.charAt(1);
    const shot = {column, row};
    if (shotAlreadyInArray(shot, result)) return [];
    result.push(shot);
  }
  return result; 
}

const resultOfShots = (shots, ships) => {

  let results = [];
  shots.forEach((shot) => {
    results.push(resultOfShot(shot, ships, shots))
  });
  return results;
}

const resultOfShot = (shot, ships, shots) => {

  for (let i = 0; i < ships.length; i++) {
    if (shotHitsShip(shot, ships[i])) {
      if (shipIsSunk(ships[i], shots)) {
        return "S";
      }
      return "H";      
    }
  } 
  return "M";
}

const shotHitsShip = (shot, ship) => {
  
  let hasHit = false;

  ship.positions.forEach( (shipPosition) => { 
    if (shot.column === shipPosition.column && shot.row.toString() === shipPosition.row.toString()) hasHit = true;}  
  );

  return hasHit;
}

const shipIsSunk = (ship, shots) => {
  let hits = 0;

  shots.forEach( (shot) => {
    if (shotHitsShip(shot, ship)) hits++;
  });

  return hits == ship.positions.length;
}

const shotAlreadyInArray = (shot, shots) => {
  for (let i = 0; i < shots.length; i++) {
      if (shots[i].column === shot.column && shots[i].row === shot.row) return true; 
  }
  return false;
}

const saveShots = (datastore, player, game, shots, results) => {
  const shotItems = [];  
  for (let i = 0; i < shots.length; i++) {
    const shot = shots[i].column + shots[i].row;
    const result = results[i];
    const key = datastore.key(['Shot',game + player + shot]);
    const shotItem = { key, data: {
        game,
        player,
        shot,
        result
    }}; 
    shotItems.push(shotItem);
  } 
  datastore.upsert(shotItems).then( () => {console.log(`Logged ${shots.length} shots for player ${player}`);
  })
  .catch(err => {
    console.log('ERROR', err)
  });
}

const report = (datastore, callback) => {
  var query = datastore.createQuery(['Shot']).order('game');
  datastore.runQuery(query, (err, shots) => callback(err, shots, datastore.KEY));                                                   
}

const formatShotData = (shots) => {
  let result = "";
  let numberOfShots = 0;
  let numberOfHits = 0;
  let currentGame = "";
  let currentPlayer = "";
  for (let i = 0; i < shots.length; i++) {
    if (currentGame != shots[i].game || currentPlayer != shots[i].player) {
          result += scoreText(currentPlayer, numberOfShots, numberOfHits);
          result += titleText(currentGame, shots[i].game);
          numberOfShots = 0;
          numberOfHits = 0;
          currentPlayer = shots[i].player;
          currentGame = shots[i].game;
    }  
    numberOfShots++;
    if (shots[i].result === "H" || shots[i].result === "S" ) numberOfHits++;
  }
  result += scoreText(currentPlayer, numberOfShots, numberOfHits);
  return result;
}

const scoreText = (currentPlayer, numberOfShots, numberOfHits) => {
  if (currentPlayer != "") {
    return (currentPlayer + ":" + numberOfShots + " shots, " + numberOfHits + " hits <br>");            
  }
  return "";
}
const titleText = (currentGame, game) => {
  if (currentGame != game) {
    return ("<br>Game " + game + "<br>=============================================<br>") ;
  }
  return "";
}

module.exports = {shotHitsShip, shipIsSunk, resultOfShot, resultOfShots, parseShots, getResult, saveShots, report,  formatShotData, scoreText, titleText};
