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
    const pubSortKeyDescending =  (pub1, pub2) =>  {
        if (pub1.sortKey < pub2.sortKey) {return 1};
        if (pub1.sortKey > pub2.sortKey) {return -1};
        return 0;
    };    

    return listOfPubs.sort(pubSortKeyDescending);
}

module.exports = { parseJson, Pub, sortOnPubKey };
