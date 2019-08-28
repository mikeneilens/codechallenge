const { Pub } = require("./pub");
const { Beer } = require("./beer");

const parseJson = (jsonString) => { 
    try {
    const pubsObject = JSON.parse(jsonString);
    const pubs = pubsObject.Pubs.map( pubObject => new Pub(pubObject));
    return pubs;
    } catch (err) {
        return [];
    }
 };


const sortKeyIsAscending =  (element1, element2) =>  {
    if (element1.sortKey > element2.sortKey) {return 1};
    if (element1.sortKey < element2.sortKey) {return -1};
    return 0;
};    
const sortKeyIsDescending =  (element1, element2) =>  {
    return -1 * sortKeyIsAscending(element1, element2)
};    

const sortOnPubKey = (listOfPubs) => {
    return listOfPubs.sort(sortKeyIsDescending);
}

const removeDuplicates = (listOfPubs) => {
    let mapOfPubs = new Map();
    let pub;
    for (pub of listOfPubs) {
        if (!mapOfPubs.has(pub.secondKey)) {
            mapOfPubs.set(pub.secondKey, pub);
        }
    } 
    return Array.from( mapOfPubs.values() );
}

Array.prototype.flatMap = function(lambda) { 
	return Array.prototype.concat.apply([], this.map(lambda)); 
};
	
const mapToRegularBeer = (listOfPubs) => {
    return listOfPubs.flatMap( pub => pub.regularBeers.map(regularBeer => new Beer(regularBeer, pub.name, pub.pubService, true) ) );
}

const mapToGuestBeer = (listOfPubs) => {
    return listOfPubs.flatMap( pub => pub.guestBeers.map(regularBeer => new Beer(regularBeer, pub.name, pub.pubService, false) ) );
}

const createFlatListOfBeer = (listOfPubs) => {
    return  mapToRegularBeer(listOfPubs).concat(mapToGuestBeer(listOfPubs));
}

module.exports = { parseJson, Pub, Beer, sortOnPubKey, removeDuplicates, mapToRegularBeer, createFlatListOfBeer, sortKeyIsAscending };
