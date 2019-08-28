const { Pub, parseJson, sortOnPubKey } = require("../src/app");
const { noPubs, singlePub, singlePubWithNoBeer, manyPubs, invalidJson, jsonNotPubs} = require("../src/testdata");

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

	it(' should return an empty list of Pubs if the jsonstring does not contain pubs', function () {
		const listOfPubs = parseJson(jsonNotPubs);
	   	assert.equal(0, listOfPubs.length);
	});


});

describe('Test sortOnPubKey', function () {
	it(' list of pubs is sorted into descending sequence on key branch/id/createts', function () {
		const pub1 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:21", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub2 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:23", "PubService":"pub1 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub3 = new Pub({"Name":"pub1", "Branch":"b1", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub4 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id1", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const pub5 = new Pub({"Name":"pub4", "Branch":"b2", "Id":"id2", "CreateTS":"2019-05-16 19:31:22", "PubService":"pub2 service", "RegularBeers":[], "GuestBeers":[]});		
		const sortedPubs = sortOnPubKey([pub1, pub2, pub3, pub4, pub5]);
	
		assert.equal(true, sortedPubs[0].sortKey > sortedPubs[1].sortKey );
		assert.equal(true, sortedPubs[1].sortKey > sortedPubs[2].sortKey );
		assert.equal(true, sortedPubs[2].sortKey > sortedPubs[3].sortKey );
		assert.equal(true, sortedPubs[3].sortKey > sortedPubs[4].sortKey );
	});

});