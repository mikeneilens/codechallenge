

const carrier1 = {type:"cruiser",positions:[{column:'B',row:2},{column:'B',row:3},{column:'B',row:4},{column:'B',row:5},{column:'B',row:6}]};
const battleship1 = {type:"battleship",positions:[{column:'E',row:1},{column:'F',row:1},{column:'G',row:1},{column:'H',row:1}]};
const cruiser1 = {type:"cruiser",positions:[{column:'D',row:3},{column:'E',row:3},{column:'F',row:3}]};
const submarine11 = {type:"submarine",positions:[{column:'H',row:4},{column:'H',row:5}]};
const submarine12 = {type:"submarine",positions:[{column:'I',row:8},{column:'I',row:9}]};
const destroyer11 = {type:"destroyer",positions:[{column:'E',row:6}]};
const destroyer12 = {type:"destroyer",positions:[{column:'A',row:9}]};
const fleet1 = [carrier1, battleship1, cruiser1, submarine11, submarine12, destroyer11, destroyer12];

const carrier2 = {type:"cruiser",positions:[{column:'F',row:5},{column:'G',row:5},{column:'H',row:5},{column:'I',row:5},{column:'J',row:5}]};
const battleship2 = {type:"battleship",positions:[{column:'A',row:9},{column:'B',row:9},{column:'C',row:9},{column:'D',row:9}]};
const cruiser2 = {type:"cruiser",positions:[{column:'C',row:1},{column:'C',row:2},{column:'C',row:3}]};
const submarine21 = {type:"submarine",positions:[{column:'G',row:0},{column:'G',row:1}]};
const submarine22 = {type:"submarine",positions:[{column:'J',row:7},{column:'J',row:8}]};
const destroyer21 = {type:"destroyer",positions:[{column:'A',row:0}]};
const destroyer22 = {type:"destroyer",positions:[{column:'B',row:6}]};
const fleet2 = [carrier2, battleship2, cruiser2, submarine21, submarine22, destroyer21, destroyer22];

const carrier3 = {type:"cruiser",positions:[{column:'J',row:5},{column:'J',row:6},{column:'J',row:7},{column:'J',row:8},{column:'J',row:9}]};
const battleship3 = {type:"battleship",positions:[{column:'A',row:0},{column:'A',row:1},{column:'A',row:2},{column:'A',row:3}]};
const cruiser3 = {type:"cruiser",positions:[{column:'E',row:4},{column:'F',row:4},{column:'G',row:4}]};
const submarine31 = {type:"submarine",positions:[{column:'H',row:1},{column:'I',row:1}]};
const submarine32 = {type:"submarine",positions:[{column:'B',row:7},{column:'C',row:7}]};
const destroyer31 = {type:"destroyer",positions:[{column:'C',row:1}]};
const destroyer32 = {type:"destroyer",positions:[{column:'H',row:7}]};
const fleet3 = [carrier3, battleship3, cruiser3, submarine31, submarine32, destroyer31, destroyer32];

const shipsData = (game) => {
    switch(game) {
        case "TESTGAME": return fleet1;
        case "GAME1": return fleet2;
        case "GAME2": return fleet3;
        default: return fleet1;
    }
}

module.exports = {shipsData};