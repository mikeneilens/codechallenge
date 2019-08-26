
const { parseJson } = require("../src/app");
const { noPubs, singlePub, singlePubWithNoBeer, manyPubs} = require("../src/testdata");

var assert = require('assert');
describe('Test json parser', function () {

	it(' should return an empty list of Pubs if the jsonstring contains no Pubs', function () {
 		const result = parseJson(noPubs);
        assert.equal(0, result.Pubs.length);
 	});

	it(' should return a list containing one Pubs if the jsonstring contains one Pubs', function () {
 		const result = parseJson(singlePub);
 		console.log(result);
        assert.equal(1, result.Pubs.length);
        assert.equal("Cask and Glass", result.Pubs[0].Name);
 	});

});