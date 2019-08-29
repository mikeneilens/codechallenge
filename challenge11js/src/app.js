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
    if (element1.sortKey > element2.sortKey) {return 1}
    if (element1.sortKey < element2.sortKey) {return -1}
    return 0;
};    
const sortKeyIsDescending =  (element1, element2) =>  {
    return -1 * sortKeyIsAscending(element1, element2);
};    

Array.prototype.removeDuplicates = function() {
    let mapOfPubs = new Map();
    let pub;
    for (pub of this) {
        if (!mapOfPubs.has(pub.uniqueKey)) {
            mapOfPubs.set(pub.uniqueKey, pub);
        }
    } 
    return Array.from( mapOfPubs.values() );
};

//Included in latest version of ES6 but not in the version used by Cloud9
Array.prototype.flatMap = function(lambda) { 
	return Array.prototype.concat.apply([], this.map(lambda)); 
};
	
const mapToRegularBeer = (listOfPubs) => {
    return listOfPubs.flatMap( pub => pub.regularBeers.map(regularBeer => new Beer(regularBeer, pub.name, pub.pubService, true) ) );
};

const mapToGuestBeer = (listOfPubs) => {
    return listOfPubs.flatMap( pub => pub.guestBeers.map(regularBeer => new Beer(regularBeer, pub.name, pub.pubService, false) ) );
};

Array.prototype.createFlatListOfBeer = function() {
    return  mapToRegularBeer(this).concat(mapToGuestBeer(this));
};

const obtainListOfBeers = (jsonString) => {
    const listOfPubs = parseJson(jsonString);

    return listOfPubs
            .sort(sortKeyIsDescending)
            .removeDuplicates()
            .createFlatListOfBeer()
            .sort(sortKeyIsAscending);
};

module.exports = { parseJson, sortKeyIsDescending, sortKeyIsAscending, mapToRegularBeer, obtainListOfBeers };