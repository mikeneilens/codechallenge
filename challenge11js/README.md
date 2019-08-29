# Challenge 11 in Javascript

This is my first proper attempt at creating a tested javascript program.
My algorithm is:

* Parse string into a object - I took the array of json objects created by the parse to create a proper object based on a Pub Class.
 
* Sort the list of pubs into descending order and remove duplicates. 

* Use flatMap to convert a list pubs with each pub containing a list of beer into a single list of beer.

* Sorted the output.

Some design decisions:

* I didn't use the json object created by the parser as I wanted to trap any errors in the original json and didn't want to keep bumping into 'undefined'. This makes the constructor for Pub class quite long.

* I had to add my own FlatMap method into the code as I was using Cloud9 to run the javascript which seems to run on an older version of ES6 which doesn't contain the now standard flatMap.
