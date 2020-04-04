const { parseJson, sortKeyIsDescending, sortKeyIsAscending, mapToRegularBeer, obtainListOfBeers } = require("../src/app");
const { Pub } = require("../src/pub");
const { Beer } = require("../src/beer");
const { noPubs, singlePub, singlePubWithNoBeer, singlePubMissingFields ,manyPubs, invalidJson, jsonNotPubs} = require("../src/testdata");

var assert = require('assert');

describe('Test json parser', function () {

	it(' should return an empty list of Pubs if the jsonstring contains no Pubs', function () {
 		const listOfPubs = parseJson(noPubs);
        assert.equal(0, listOfPubs.length);
 	});

	it(' should return a list containing one Pubs if the jsonstring contains one Pub', function () {
 		const listOfPubs = parseJson(singlePub);
        assert.equal(1, listOfPubs.length);
        assert.equal("Cask and Glass", listOfPubs[0].name);
 	});

	 it(' should return a list containing many Pubs if the jsonstring contains many Pubs', function () {
		const listOfPubs = parseJson(manyPubs);
	   	assert.equal(6, listOfPubs.length);
		assert.equal("Phoenix", listOfPubs[0].name);
	});

	it(' should return an empty list of Pubs if the jsonstring is not valid json', function () {
		const listOfPubs = parseJson(invalidJson);
	   	assert.equal(0, listOfPubs.length);
	});

	it(' should return an empty list of Pubs if mandatory fields are missing', function () {
		const listOfPubs = parseJson(singlePubMissingFields);
	   	assert.equal(0, listOfPubs.length);
	});


	it(' should return an empty list of Pubs if the jsonstring does not contain pubs', function () {
		const listOfPubs = parseJson(jsonNotPubs);
	   	assert.equal(0, listOfPubs.length);
	});


});

describe('Test sortKeyIsDescending', function () {
	it(' list of pubs is sorted into descending sequence on key branch/id/createts', function () {
		const pub1 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:21", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub2 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:23", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub3 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub4 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub5 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id2", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const sortedPubs = [pub1, pub2, pub3, pub4, pub5].sort(sortKeyIsDescending);
	
		assert.equal(true, sortedPubs[0].sortKey >= sortedPubs[1].sortKey );
		assert.equal(true, sortedPubs[1].sortKey >= sortedPubs[2].sortKey );
		assert.equal(true, sortedPubs[2].sortKey >= sortedPubs[3].sortKey );
		assert.equal(true, sortedPubs[3].sortKey >= sortedPubs[4].sortKey );
	});

});

describe('Test sortKeyIsAscending', function () {
	it(' list of pubs is sorted into descending sequence on key branch/id/createts', function () {
		const pub1 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:21", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub2 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:23", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub3 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub4 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub5 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id2", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const sortedPubs = [pub1, pub2, pub3, pub4, pub5].sort(sortKeyIsAscending);
	
		assert.equal(true, sortedPubs[0].sortKey <= sortedPubs[1].sortKey );
		assert.equal(true, sortedPubs[1].sortKey <= sortedPubs[2].sortKey );
		assert.equal(true, sortedPubs[2].sortKey <= sortedPubs[3].sortKey );
		assert.equal(true, sortedPubs[3].sortKey <= sortedPubs[4].sortKey );
	});

});

describe('Test removeDuplicates', function () {
	it(' list of duplicates has duplciates removed', function () {
		const pub1 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:21", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub2 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:23", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub3 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub4 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub5 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id2", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		//const result = removeDuplicates([pub1,pub2,pub3,pub4,pub5]);
		const result =  [pub1,pub2,pub3,pub4,pub5].removeDuplicates();
		assert.equal(3, result.length);
		assert.equal(pub1, result[0]);
		assert.equal(pub4, result[1]);
		assert.equal(pub5, result[2]);
	});
});

describe('Test mapping a pubs into regular beers', function () {
	it(' pubs containing regular beers are mapped to an array of a list of beers', function () {
		const pub1 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"", "PubService":"pub1 service", "RegularBeers":["b1","b2","b3"], "GuestBeers":[]});		
		const pub2 = new Pub({"Name":"pub2", "Branch":"b1", "Id":"id1", "CreateTS":"", "PubService":"pub2 service", "RegularBeers":["b2","b3","b4"], "GuestBeers":[]});		
		const pub3 = new Pub({"Name":"pub3", "Branch":"b1", "Id":"id1", "CreateTS":"", "PubService":"pub3 service", "RegularBeers":["b4","b5"], "GuestBeers":[]});		
		const result = mapToRegularBeer([pub1,pub2,pub3]);
		assert.equal(8, result.length);
		assert.equal("b1",result[0].name);
		assert.equal("pub1",result[0].pubName);
		assert.equal("pub1 service",result[0].pubService);
		assert.equal(true,result[0].isRegularBeer);
		assert.equal("b2",result[1].name);
		assert.equal("b3",result[2].name);
		assert.equal("b2",result[3].name);
	});
});

describe('Test mapping a list of pubs into beers', function () {
	it(' pubs containing regular beers are mapped to an array of a list of beers', function () {
		const pub1 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"", "PubService":"pub1 service", "RegularBeers":["b1","b2","b3"], "GuestBeers":[]});		
		const pub2 = new Pub({"Name":"pub2", "Branch":"b1", "Id":"id1", "CreateTS":"", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":["b2","b3","b4"]});		
		const pub3 = new Pub({"Name":"pub3", "Branch":"b1", "Id":"id1", "CreateTS":"", "PubService":"pub3 service", "RegularBeers":["b4"], "GuestBeers":["b5"]});		
		const result = [pub1,pub2,pub3].createFlatListOfBeer().sort(sortKeyIsAscending);

		assert.equal("b1", result[0].name);
		assert.equal("pub1", result[0].pubName);
		assert.equal(true, result[0].isRegularBeer);
		
		assert.equal("b2", result[1].name);
		assert.equal("pub1", result[1].pubName);
		assert.equal(true, result[1].isRegularBeer);
		
		assert.equal("b2", result[2].name);
		assert.equal("pub2", result[2].pubName);
		assert.equal(false, result[2].isRegularBeer);
		
		assert.equal("b3", result[3].name);
		assert.equal("pub1", result[3].pubName);
		assert.equal(true, result[3].isRegularBeer);
		
		assert.equal("b3", result[4].name);
		assert.equal("pub2", result[4].pubName);
		assert.equal(false, result[4].isRegularBeer);
		
		assert.equal("b4", result[5].name);
		assert.equal("pub2", result[5].pubName);
		assert.equal(false, result[5].isRegularBeer);
		
		assert.equal("b4", result[6].name);
		assert.equal("pub3", result[6].pubName);
		assert.equal(true, result[6].isRegularBeer);
		
		assert.equal("b5", result[7].name);
		assert.equal("pub3", result[7].pubName);
		assert.equal(false, result[7].isRegularBeer);
		
	});
});

describe('Test obtainListOfBeers', function () {
	it(' string containing json should map correctly to a list of beers', function () {
		const beersForInvalidJson = obtainListOfBeers(invalidJson);
		assert.equal(0,beersForInvalidJson.length);
	

		const beersForPubWithNoBeers = obtainListOfBeers(singlePubWithNoBeer);
		assert.equal(0, beersForPubWithNoBeers.length);

		const beersForSinglePub = obtainListOfBeers(singlePub);
		assert.equal(5, beersForSinglePub.length);
		assert.equal('Shepherd Neame --seasonal--', beersForSinglePub[0].name);
		assert.equal('Cask and Glass', beersForSinglePub[0].pubName);
		assert.equal('Shepherd Neame --varies--', beersForSinglePub[1].name);
		assert.equal('Cask and Glass', beersForSinglePub[1].pubName);
		assert.equal('Shepherd Neame Whitstable Bay Pale Ale', beersForSinglePub[4].name);
		
		const beersForManyPubs = obtainListOfBeers(manyPubs);
		
		assert.equal(16, beersForManyPubs.length);
		assert.equal('Brakspear Bitter',beersForManyPubs[0].name);
		assert.equal('Sports Bar and Grill Victoria',beersForManyPubs[0].pubName);
		assert.equal('Fuller#039;s London Pride',beersForManyPubs[1].name);
		assert.equal('Willow Walk',beersForManyPubs[1].pubName);
		assert.equal('Greene King IPA',beersForManyPubs[4].name);
		assert.equal('Shakespeare',beersForManyPubs[4].pubName);
		assert.equal('Greene King IPA',beersForManyPubs[5].name);
		assert.equal('Willow Walk',beersForManyPubs[5].pubName);

	});
});