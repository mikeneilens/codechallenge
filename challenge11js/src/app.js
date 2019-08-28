class Pub {
    constructor(pubObject) {
        this.name = pubObject.Name;
        this.branch = pubObject.Branch;
        this.id = pubObject.Id;
        this.createTS = pubObject.CreateTS;
        this.pubService = pubObject.PubService;
        this.regularBeers = pubObject.RegularBeers;
        this.guestBeers = pubObject.GuestBeers;
    };
    get sortKey() { return this.branch + this.id + this.createTS;} 
    get secondKey() { return this.branch + this.id;} 
}

const parseJson = (jsonString) => { 
    try {
    const pubsObject = JSON.parse(jsonString);
    const pubs = pubsObject.Pubs.map( pubObject => new Pub(pubObject));
    return pubs;
    } catch (err) {
        return [];
    }
 };


const sortOnPubKey = (listOfPubs) => {
    const sortKeyIsDescending =  (element1, element2) =>  {
        if (element1.sortKey < element2.sortKey) {return 1};
        if (element1.sortKey > element2.sortKey) {return -1};
        return 0;
    };    

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

module.exports = { parseJson, Pub, sortOnPubKey, removeDuplicates };
