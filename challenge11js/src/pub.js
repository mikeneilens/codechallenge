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

module.exports = {Pub };