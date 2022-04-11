# Card Filtering Challenge

This is the card filtering challenge. 

Given that I have a list of cards in an input.json file, and some user data, return me a list of cards specific to the input user data.

The card data has been restricted to `id`, `cardType` and `name` to make processing simpler and consistent these will all be string values, also each card can have an optional `filtering` property.

Card type can have these valid options ["carousel", "banner", "departments", "recommendations"] and will only include valid options for simplicity.

UserDetails will contain an optional `osVersion` version property of type OperatingSystemVersion.

The OperatingSystemVersion type will include the following integer properties: majorVersion, minorVersion, patchVersion

The `filtering` property is of type Filtering, this contains a `groupId` as an optional string, and a property called `filters` which is an array of Filter objects.

A Filter object consists of a `filter` which is an enumeration of the types of filter, an optional `value` string, and optional `range` of type FilterRange. Filter will usually contain either a value or a range, but if the filter type is "controlGroup" then both will be nil.

In this sample project, assume that any non nil "value" will be in the format "0.0.0" always. Any of the number values can be any number like 1, 10 or 1000 e.g. "10.5.999"

FilterRange has 2 properties `min` and `max` which are both of type double.

FilterType has the following options:

• controlGroup        // acts as the default option if nothing else matches

• osVersionEquals
• osVersionMajorEquals
• osVersionMinorEquals
• osVersionGreaterThan
• osVersionMajorGreaterThan
• osVersionMinorGreaterThan
• osVersionLessThan
• osVersionMajorLessThan
• osVersionMinorLessThan

## GroupID

The business logic around `groupId` is as follows:

If a `filtering` object has no `groupId` then that card should be validated individually and returned or not returned based on it's own filter option(s).

If a `filtering` object has a `groupId` then you should extract ALL the cards with a matching `groupId`, and return "at most" 1 card at the positional location that it appears in the `input.json` file. e.g. if there are 2 cards at the top with the same `groupId`, and 1 card at the very bottom of the file with several cards in between, and the card at the bottom validates true and the others do not, then the valid card at the bottom of the file should be returned but at the bottom of the list of returned cards (or its inherent position in the list), not at the first position that you happen to come across the `groupId`.

If you have a list of grouped cards, and the subset contains a card with a `filter` type of `controlGroup`, then by default this card should be returned if non of the other filtered cards within that group validate true, otherwise the first valid non `controlGroup` card should be used.

Again to reiterate for groups of cards, a maximum of 1 valid card should be returned.

# Expectations

Write a function that takes a `UserDetails` object and an array of `Card` objects and returns a filtered list of `Card` objects.

Using the `input.json` file
Given the `UserDetails` where `osVersion` is nil
I would expect a filtered list of cards (just going to use the "name" value for brevity):
["Carousel", "Banner", "New departments list default"]

Using the `input.json` file
Given the `UserDetails` where `osVersion` is "15.0.0"
I would expect a filtered list of cards (just going to use the "name" value for brevity):
["Carousel", "Banner", "Recommendations1", "New departments list 15", "Banner2"]

Using the `input.json` file
Given the `UserDetails` where `osVersion` is "14.0.1"
I would expect a filtered list of cards (just going to use the "name" value for brevity):
["Carousel", "Banner", "New departments list default", "Recommendations2"]

Using the `input.json` file
Given the `UserDetails` where `osVersion` is "13.0.0"
I would expect a filtered list of cards (just going to use the "name" value for brevity):
["Carousel", "Banner", "Old departments list"]
