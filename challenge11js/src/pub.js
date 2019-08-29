class Pub {
    constructor(pubObject) {
        if (pubObject.Name == undefined) {
            throw "no Name in the json";
        }
        this.name = pubObject.Name;

        if (pubObject.Branch == undefined) {
            throw "no Branch in the json";
        }
        this.branch = pubObject.Branch;

        if (pubObject.Id == undefined) {
            throw "no Id in the json";
        }
        this.id = pubObject.Id;

        if (pubObject.CreateTS == undefined) {
            throw "no CreateTS in the json";
        }
        this.createTS = pubObject.CreateTS;
        
        if (pubObject.PubService == undefined) {
            throw "no PubService in the json";
        }
        this.pubService = pubObject.PubService;

        if (pubObject.RegularBeers == undefined) {
            this.regularBeers = [];
        } else {
            this.regularBeers = pubObject.RegularBeers;
        }
        if (pubObject.GuestBeers == undefined) {
            this.guestBeers = [];
        } else {
            this.guestBeers = pubObject.GuestBeers;
        }

    }
    get sortKey() { return this.branch + this.id + this.createTS;} 
    get uniqueKey() { return this.branch + this.id;} 
}

module.exports = {Pub };