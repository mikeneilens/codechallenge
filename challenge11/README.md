# Challenge 11

I chose to use Klaxon to convert json directly into an object defined using a kotlin class. This seemed to require less code that either Jackson or Moshi to map title case json fields to lowercase kotlin variable names.

Once I had the data in a kotlin object I went through the following steps:

* Removed duplciates by sorting into key/date order and using the kotlin distinctBy function to remove duplicates.

* Used flatMap to create a list of regular beers and list of guest beers.

* Sorted the combined list of beers into beer/pub order.
