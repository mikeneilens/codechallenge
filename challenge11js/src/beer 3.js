class Beer {
    constructor(_name, _pubName, _pubService, _isRegularBeer) {
        this.name = _name;
        this.pubName = _pubName;
        this.pubService = _pubService;
        this.isRegularBeer = _isRegularBeer;
    }
    get sortKey() { return this.name + this.pubName;} 
}

module.exports = {Beer };