## Challenge 30 ##

This is the domain model for reserving rooms or desks in any Partnership building. I tried to use a DDD type approach.

After much experimentation I decided the Bounded Context was the customer reservation rather than the whole room and desk management system.

This resulted in these principle classes: 

+ **Location** : Each Partnership Location where people can reserve a space. Just contains an id and description.

+ **LocatinForADate**: Contains each space in a Location on a particular date

+ **Space**: This is the specification for a space. It contains a string and the amount available. It does not describe the actual physical entity so has no id.

+ **Slot**: This is a time slot, but its just a description. I originally gave it an id but it served no purpose in the model although probably is needed to allow for the booking system changing the text. 

+ **Booking**: This describes the reservation. Maybe it should be called Reservation! Customers can reserve a space (e.g. Room for 8 people) and one or more time slots (e.g. "9am - 1pm" and "1pm -5pm") for a date/location.

A booking does not indicate which particular room or desk has been earmarked for the customer. It is assumed that is in a different Bounded Context, or maybe when the people turn up someone just walks them to an empty desk or room of the right specifacation. 


The functions that make the model useful are:

+ **getLocations()** - does what it says

+ **Location.getAvailableSpace()** - gets a LocationForADate which contains all the spaces in a location on a particular date.

+ **LocationOnADate.getAvailableSlots(space)** - gets the slots that can be reserved for a particular space for a LocationDate

+ **LocationOnADate.getSlotsForEachSpace()** - gets every possible slot for every space for a LocationOnADate

+ **makeBooking(location, date, space, customer etc.)** - makes a reservation for a customer. Maybe should be part of LocationOnADate. 


I've ignored the "desk bank" facility as that seems optional. I don't think it would be problematic adding that to the model.

The model defines interfaces for repositories that would contain information about locations and bookings.

The model ignores error handling!