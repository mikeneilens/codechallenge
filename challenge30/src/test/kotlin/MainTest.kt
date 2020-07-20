import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {

    val location1 = Location(1, "location1")
    val location2 = Location(2, "location2")
    val roomFor2People = Space("Room for 2", 2)
    val roomFor7People = Space("Room for 7", 7)
    val roomFor10People = Space("Room for 10", 7)
    val desk = Space("Single Desk", 1)

    @Test
    fun `getLocations returns no locations when then the repository returns none`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> = listOf()
            override fun spacesAtLocation(location: Location, bookingDate: String): LocationOnADate { TODO("Not yet implemented") }
        }
        val result = getLocations(locationRepository)
        assertEquals(0, result.size)
    }

    @Test
    fun `getLocations returns one locations when then the repository returns one`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> = listOf(location1)
            override fun spacesAtLocation(location: Location, bookingDate: String): LocationOnADate { TODO("Not yet implemented") }
        }
        val result = getLocations(locationRepository)
        assertEquals(1, result.size)
        assertEquals(location1, result[0])
    }

    @Test
    fun `getLocations returns two locations when then the repository returns two`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> = listOf(location1, location2)
            override fun spacesAtLocation(location: Location, bookingDate: String): LocationOnADate { TODO("Not yet implemented") }
        }
        val result = getLocations(locationRepository)
        assertEquals(2, result.size)
        assertEquals(location1, result[0])
        assertEquals(location2, result[1])
    }

    @Test
    fun `getAvailableSpaces returns LocationOnADate with no spaces when no spaces are available at the location`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> { TODO("Not yet implemented") }
            override fun spacesAtLocation(location: Location, bookingDate: String) = LocationOnADate(location, bookingDate, listOf())
        }
        val result = location1.getAvailableSpace("2020-07-20", locationRepository)
        assertEquals(0, result.spaces.size)
    }

    @Test
    fun `getAvailableSpaces returns LocationOnADate with one spaces when one spaces is available at the location`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> { TODO("Not yet implemented") }
            override fun spacesAtLocation(location: Location, bookingDate: String) = LocationOnADate(location, bookingDate, listOf(roomFor7People))
        }
        val result = location1.getAvailableSpace("2020-07-20", locationRepository)
        assertEquals(1, result.spaces.size)
        assertEquals(roomFor7People, result.spaces[0])
    }

    @Test
    fun `getAvailableSpaces returns LocationOnADate with two spaces when two spaces are available at the location`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> { TODO("Not yet implemented") }
            override fun spacesAtLocation(location: Location, bookingDate: String) =
                LocationOnADate(location, bookingDate, listOf(desk, roomFor10People))
        }
        val result = location1.getAvailableSpace("2020-07-20", locationRepository)
        assertEquals(2, result.spaces.size)
        assertEquals(desk, result.spaces[0])
        assertEquals(roomFor10People, result.spaces[1])
    }

    @Test
    fun `getAvailableSpaces returns LocationOnADate with two spaces when three spaces are available at the location but only two are unique`() {
        val locationRepository = object : LocationRepository {
            override fun locations(): List<Location> { TODO("Not yet implemented") }
            override fun spacesAtLocation(location: Location, bookingDate: String) =
                LocationOnADate(location, bookingDate, listOf(desk, roomFor10People, desk))
        }
        val result = location1.getAvailableSpace("2020-07-20", locationRepository)
        assertEquals(2, result.spaces.size)
        assertEquals(desk, result.spaces[0])
        assertEquals(roomFor10People, result.spaces[1])
    }

    @Test
    fun `getAvailableSlots returns an empty list if the space is not found`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> = emptyList()
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val result = locationOnADate.getAvailableSlots(desk, bookingRepository)
        assertEquals(0, result.size)
    }

    @Test
    fun `getAvailableSlots returns an empty list if the space is found but it has no available slots`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 0))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val result = locationOnADate.getAvailableSlots(desk, bookingRepository)
        assertEquals(0, result.size)
    }

    @Test
    fun `getAvailableSlots returns one slot if the space is found but it has one available slots`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 1))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val result = locationOnADate.getAvailableSlots(desk, bookingRepository)
        assertEquals(1, result.size)
        assertEquals(Slot("9an-11am"), result[0].slot)
    }

    @Test
    fun `getAvailableSlots returns two slots if the space is found and has two slots with availability`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 1), AvailableSlot(Slot("11an-1pm"), 4))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val result = locationOnADate.getAvailableSlots(desk, bookingRepository)
        assertEquals(2, result.size)
        assertEquals(Slot("9an-11am"), result[0].slot)
        assertEquals(Slot("11an-1pm"), result[1].slot)
    }

    @Test
    fun `getSlotsForEachSpace returns no spaces if there is one space but it has not slots with availability`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 0))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk))
        val result = locationOnADate.getSlotsForEachSpace(bookingRepository)
        assertEquals(0, result.size)
    }

    @Test
    fun `getSlotsForEachSpace returns one space if there is one space and it has one slot with availability`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 1))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk))
        val result = locationOnADate.getSlotsForEachSpace(bookingRepository)
        assertEquals(1, result.size)
    }

    @Test
    fun `getSlotsForEachSpace returns one space if there is one space and it has two slot with availability`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 3), AvailableSlot(Slot("11an-1pm"), 5))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk))
        val result = locationOnADate.getSlotsForEachSpace(bookingRepository)
        assertEquals(1, result.size)
    }

    @Test
    fun `getSlotsForEachSpace returns two spaces if there is two spaces and that both have a slot with availability`() {
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                if (space == desk)
                    listOf(AvailableSlot(Slot("9an-11am"), 3), AvailableSlot(Slot("11an-1pm"), 5))
                else
                    listOf(AvailableSlot(Slot("11an-1pm"), 5), AvailableSlot(Slot("1pm-3pm"), 4))

            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor7People))
        val result = locationOnADate.getSlotsForEachSpace(bookingRepository)
        assertEquals(2, result.size)
    }

    @Test
    fun `containsSpace returns true when the LocationOnADate contains the specified space `() {
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor7People))
        assertTrue(locationOnADate.contains(roomFor7People))
    }

    @Test
    fun `containsSpace returns false when the LocationOnADate does not contain the specified space `() {
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor7People))
        assertFalse(locationOnADate.contains(roomFor2People))
    }

    @Test
    fun `slot isAvailable is true when it is in the list of available slots`() {
        val slot = Slot("11an-1pm")
        val availableSlots = listOf(AvailableSlot(Slot("9an-11am"), 3), AvailableSlot(Slot("11an-1pm"), 5))
        assertTrue(slot.isAvailable(availableSlots))
    }

    @Test
    fun `slot isAvailable is false when it is not in the list of available slots`() {
        val slot = Slot("1pm-3pm")
        val availableSlots = listOf(AvailableSlot(Slot("9an-11am"), 3), AvailableSlot(Slot("11an-1pm"), 5))
        assertFalse(slot.isAvailable(availableSlots))
    }

    @Test
    fun `slot isAvailable is false when it is in the list of available slots with quantity of zero`() {
        val slot = Slot( "9an-11am")
        val availableSlots = listOf(AvailableSlot(Slot("9an-11am"), 0), AvailableSlot(Slot("11an-1pm"), 5))
        assertFalse(slot.isAvailable(availableSlots))
    }

    @Test
    fun `can take vistitors is false when number of visitors is greater than the capacity of the space`() {
        val visitor1 = Person.Partner("fred", "fred@johnlewis.co.uk", 1234)
        val person2 = Person.Attendee("sarah", "sarah@something.com")
        val person3 = Person.Attendee("stevros", "stavros@notjohnlewis.co.uk")
        val threeVisitors = listOf(visitor1, person2, person3)
        assertFalse(roomFor2People.canTake(threeVisitors))
    }

    @Test
    fun `can take visitors is true when number of visitors is equal to the capacity of the space`() {
        val visitor1 = Person.Partner("fred", "fred@johnlewis.co.uk", 1234)
        val person2 = Person.Attendee("sarah", "sarah@something.com")
        val twoVisitors = listOf(visitor1, person2)
        assertTrue(roomFor2People.canTake(twoVisitors))
    }

    @Test
    fun `making a booking with an invalid person returns an NotPartnerOrExternalBooker `() {
        val attendee = Person.Attendee("stevros", "stavros@notjohnlewis.co.uk")
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val slot = Slot("9an-11am")
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> { TODO("Not yet implemented") }
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }

        val result = makeBooking(locationOnADate, desk, listOf(slot), attendee, bookingRepository)
        assertEquals(BookingResult.NotAPartnerOrExternalBooker, result)
    }

    @Test
    fun `making a booking with too many visitors for the space returns an TooManyVisitorsForTheSpace `() {
        val booker = Person.ExternalBooker("stevros", "stavros@notjohnlewis.co.uk", "wormwood scrubs")
        val attendee = Person.Attendee("stevros", "stavros@notjohnlewis.co.uk")
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val slot = Slot("9an-11am")
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> { TODO("Not yet implemented") }
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }

        val result = makeBooking(locationOnADate, desk, listOf(slot), booker, bookingRepository, listOf(attendee, booker))
        assertEquals(BookingResult.TooManyVisitorsForTheSpace, result)
    }

    @Test
    fun `making a booking for a space that is not at the location returns SpaceNotAvalailable `() {
        val booker = Person.ExternalBooker("stevros", "stavros@notjohnlewis.co.uk", "wormwood scrubs")
        val attendee = Person.Attendee("stevros", "stavros@notjohnlewis.co.uk")
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val slot = Slot("9an-11am")
        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> { TODO("Not yet implemented") }
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }

        val result = makeBooking(locationOnADate, roomFor2People, listOf(slot), booker, bookingRepository, listOf(attendee, booker))
        assertEquals(BookingResult.SpaceNotAvailableAtTheLocation, result)
    }

    @Test
    fun `making a booking for a slot that is not available for the sapce at the location returns SlotNotAvalailable `() {
        val booker = Person.ExternalBooker("stevros", "stavros@notjohnlewis.co.uk", "wormwood scrubs")
        val attendee = Person.Attendee("stevros", "stavros@notjohnlewis.co.uk")
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val requestedSlot1 = Slot("9an-11am")
        val requestedSlot2 = Slot("1pm-3pm")

        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 3), AvailableSlot(Slot("11an-1pm"), 5))
            override fun getNextReference(): Int { TODO("Not yet implemented") }
            override fun saveBooking(booking: Booking) { TODO("Not yet implemented") }
        }

        val result = makeBooking(locationOnADate, roomFor7People, listOf(requestedSlot1, requestedSlot2), booker, bookingRepository, listOf(attendee, booker)
        )
        assertEquals(BookingResult.SlotNotAvailableForTheSpace, result)
    }

    @Test
    fun `making a booking when the booker is a Partner or an attendee and the space exists and all slots exist returns a booking `() {
        val external = Person.ExternalBooker("stevros", "stavros@notjohnlewis.co.uk", "wormwood scrubs")
        val partner = Person.Partner("mike", "mike@johnlewis.co.uk", 1234)
        val attendee = Person.Attendee("stevros", "stavros@notjohnlewis.co.uk")
        val locationOnADate = LocationOnADate(location1, "2020-07-20", listOf(desk, roomFor10People, roomFor7People))
        val requestedSlot1 = Slot("9an-11am")
        val requestedSlot2 = Slot("11an-1pm")

        val bookingRepository = object : BookingRepository {
            override fun availableSlots(location: Location, bookingDate: String, space: Space): List<AvailableSlot> =
                listOf(AvailableSlot(Slot("9an-11am"), 3), AvailableSlot(Slot("11an-1pm"), 5))

            override fun getNextReference(): Int = 99
            override fun saveBooking(booking: Booking) {}
        }

        listOf(partner, external).forEach { booker ->
            val result = makeBooking(locationOnADate, roomFor7People, listOf(requestedSlot1, requestedSlot2), booker, bookingRepository, listOf(attendee))
            val expectedBooking = Booking(99, locationOnADate.location, locationOnADate.bookingDate, roomFor7People, listOf(requestedSlot1, requestedSlot2), booker, listOf(attendee))
            assertTrue(result is BookingResult.Booked)
            assertEquals(expectedBooking, (result as BookingResult.Booked).booking)
        }
    }
}