const express = require('express');
const app = express();

const challenge27 = require('./app.js');

const {Datastore} = require('@google-cloud/datastore');
const datastore = new Datastore();


app.get('/', (req, res) => {
  const shots = req.query.shots;
  let player = req.query.player;
  let game = req.query.game;
  if (!player) player = 'testPlayer';
  if (!game) game = 'testGame'; 
  res.setHeader("Access-Control-Allow-Origin", "*");
  if (shots) {
      res.send( challenge27.getResult(datastore, shots.toUpperCase(), player.toUpperCase(), game.toUpperCase()));
  } else {
      res.send( challenge27.getResult(datastore, "", player.toUpperCase(), game.toUpperCase()));
  }
});
app.get('/shots', (req, res) => {
  challenge27.report(datastore, function(err, shots, key) {
    if (err) return next(err);
    const shotData = shots.map( (shot) => Object.assign(shot, { id: shot.id || shot[key].id })  );
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.send( challenge27.formatShotData(shotData) );
  });   
});

// Listen to the App Engine-specified port, or 8080 otherwise
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}...`);
});