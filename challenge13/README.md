## Challenge 13 

### Design Decisions.

I used geodesy library to calculate distances between two geolocations. The challenge didn't need such precision but had never included 3rd party java library in Kotlin project from scratch so used it as a bit of a learning exercise.

I used a `Shop` class to represent a shop. I included a property to represent the distance to the previous shop (which is set to zero by default). I didn't think it was worth creating a new class called something like 'OrderedShop' that contains a `Shop` and the distance to the previous `Shop`. 

The code is broken down into 3 parts:

* `String.toShops()` which converts the string of CSV data into a list of shops.
This validates the input to ensure that lat/lng are valid Doubles and that csv data is in blocks of four values, rather than letting the program crash.

* `List<Shop>.createRoute()` which converts the list of random shops into a list which forms a route.

* `List<Shop>.calculateJourneyTime()`. This depends on each shop knowing how far away it is from the previous shop. This uses `TimeAccumulator` class to cumlatively add the time taken to visit each shop, taking into account the length of the working day and how much time must be spent in each shop. 