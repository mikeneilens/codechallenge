data class Location(val id: Int, val name: String) {
    fun getAvailableSpace(bookingDate:String, locationRepository: LocationRepository ):LocationOnADate {
        val availableSpace = locationRepository.spacesAtLocation(this, bookingDate)
        val uniqueSpaces = availableSpace.spaces.distinct()
        return LocationOnADate(this, bookingDate, uniqueSpaces)
    }
}

data class LocationOnADate(val location:Location, val bookingDate:String, val spaces:List<Space>) {
    fun getAvailableSlots(space:Space, bookingRepository: BookingRepository):List<AvailableSlot> = bookingRepository.availableSlots(location, bookingDate, space).filter{ availableSlot -> availableSlot.quantity > 0 }

    fun getSlotsForEachSpace(bookingRepository: BookingRepository):List<AvailableSpace> =
        spaces.map { space ->
           AvailableSpace( space, getAvailableSlots(space, bookingRepository))
        }.filter{availableSpace -> availableSpace.availableSlots.size > 0}

    fun contains(space:Space)  = spaces.contains(space)
}

class AvailableSpace(val space:Space, val availableSlots:List<AvailableSlot> )

data class Space(val name:String, val capacity:Int) {
    fun canTake(visitors: List<Person>)  = (visitors.size <= capacity)
}

data class Slot(val description:String) {
    fun isAvailable(availableSlots: List<AvailableSlot>) = availableSlots.filter{it.quantity > 0}.map{it.slot}.contains(this)
}

data class AvailableSlot(val slot:Slot, val quantity:Int)

fun getLocations(locationRepository: LocationRepository):List<Location> = locationRepository.locations()

sealed class Person(val name:String, val emailAddress: String) {
    class Partner(name: String, emailAddress: String, val employeeNumber:Int):Person(name, emailAddress)
    class Attendee(name: String, emailAddress: String):Person(name, emailAddress)
    class ExternalBooker(name: String, emailAddress: String, val contactDetails:String):Person(name, emailAddress)
}
data class Booking(val reference:Int, val location:Location, val bookingDate:String, val space:Space, val slots:List<Slot>, val booker:Person, val vistiors:List<Person>)

sealed class BookingResult { //TODO giving a value to each type and error handling
    object NotAPartnerOrExternalBooker:BookingResult()
    object SpaceNotAvailableAtTheLocation:BookingResult()
    object SlotNotAvailableForTheSpace:BookingResult()
    object TooManyVisitorsForTheSpace:BookingResult()
    class Booked(val booking: Booking):BookingResult()
}

fun makeBooking(locationOnADate: LocationOnADate, space: Space, slots: List<Slot>, booker: Person, bookingRepository: BookingRepository, visitors: List<Person> = listOf(booker)
):BookingResult {
    if (booker !is Person.Partner && booker !is Person.ExternalBooker ) return BookingResult.NotAPartnerOrExternalBooker
    if (!space.canTake(visitors)) return  BookingResult.TooManyVisitorsForTheSpace

    if (!locationOnADate.contains(space)) return BookingResult.SpaceNotAvailableAtTheLocation

    val availableSlots = locationOnADate.getAvailableSlots(space, bookingRepository)

    slots.forEach { slot -> if (!slot.isAvailable( availableSlots)) return BookingResult.SlotNotAvailableForTheSpace  }

    return BookingResult.Booked(requestBooking(locationOnADate, space, slots, booker, bookingRepository, visitors))
}

private fun requestBooking(locationOnADate: LocationOnADate, space: Space, slots: List<Slot>, booker: Person, bookingRepository: BookingRepository, visitors: List<Person>):Booking {
    val reference = bookingRepository.getNextReference()
    val booking = Booking(reference, locationOnADate.location, locationOnADate.bookingDate, space, slots, booker, visitors )
    bookingRepository.saveBooking(booking)
    return booking
}

interface LocationRepository { //TODO error handling
    fun locations():List<Location>
    fun spacesAtLocation(location:Location, bookingDate:String):LocationOnADate
}

interface BookingRepository { //TODO error handling
    fun availableSlots(location:Location, bookingDate:String, space:Space):List<AvailableSlot>
    fun getNextReference():Int
    fun saveBooking(booking:Booking)
}
