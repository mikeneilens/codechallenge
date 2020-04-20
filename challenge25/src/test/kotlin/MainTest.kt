import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `concatonating list of days containing only monday returns mon`(){
        val daysOfWeek = listOf("Monday")
        assertEquals("Mon",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of days containing monday and tuesday returns mon-tue`(){
        val daysOfWeek = listOf("Monday","Tuesday")
        assertEquals("Mon-Tue",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of days containing monday and wednesday returns mon,wed`(){
        val daysOfWeek = listOf("Monday","Wednesday")
        assertEquals("Mon,Wed",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of days containing monday, tuesday and wednesday returns mon-wed`(){
        val daysOfWeek = listOf("Monday","Tuesday","Wednesday")
        assertEquals("Mon-Wed",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of days containing monday, tuesday and thursday returns mon-tue,thu`(){
        val daysOfWeek = listOf("Monday","Tuesday","Thursday")
        assertEquals("Mon-Tue,Thu",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of days containing monday, wednesday and thursday returns mon,wed,thu`(){
        val daysOfWeek = listOf("Monday","Wednesday","Thursday")
        assertEquals("Mon,Wed-Thu",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of days containing monday, tuesday,thursday and friday returns mon-tue,thu-fri`(){
        val daysOfWeek = listOf("Monday","Tuesday","Thursday","Friday")
        assertEquals("Mon-Tue,Thu-Fri",daysOfWeek.concatenateDays())
    }
    @Test
    fun `concatonating list of opening times containing one day which is closed give Closed` () {
        val openingTimes:OpeningTimes = listOf(
            OpeningTime(listOf("Monday"),"00:00","00:00")
        )
        assertEquals("Mon: Closed", openingTimes.concatenate())
    }
    @Test
    fun `concatonating list of opening times containing one day which is closed and one day which is open give Closed and open day` () {
        val openingTimes:OpeningTimes = listOf(
            OpeningTime(listOf("Monday"),"00:00","00:00"),
            OpeningTime(listOf("Tuesday"),"11:00","22:30")
        )
        assertEquals("Mon: Closed | Tue: 11am-10:30pm", openingTimes.concatenate())
    }
    @Test
    fun `concatonating list of opening times containing one day which is closed and two days which are open at same time give Closed and open day` () {
        val openingTimes:OpeningTimes = listOf(
            OpeningTime(listOf("Monday"),"00:00","00:00"),
            OpeningTime(listOf("Tuesday","Wednesday"),"11:00","22:30")
        )
        assertEquals("Mon: Closed | Tue-Wed: 11am-10:30pm", openingTimes.concatenate())
    }
    @Test
    fun `concatonating list of opening times containing one day which is closed and two days which are open at different times give Closed and open days` () {
        val openingTimes:OpeningTimes = listOf(
            OpeningTime(listOf("Monday"),"00:00","00:00"),
            OpeningTime(listOf("Tuesday","Wednesday"),"11:00","22:30"),
            OpeningTime(listOf("Thursday","Saturday"),"13:00","23:00")
        )
        assertEquals("Mon: Closed | Tue-Wed: 11am-10:30pm | Thu,Sat: 1pm-11pm", openingTimes.concatenate())
    }
    @Test
    fun `Simple test from te challenge`() {
        val openingTimes:OpeningTimes = listOf(
            OpeningTime(listOf("Monday","Tuesday","Wednesday","Thursday","Friday"),"12:00","22:00"),
            OpeningTime(listOf("Saturday","Sunday"),"12:00","23:00"))

        assertEquals("Mon-Fri: 12pm-10pm | Sat-Sun: 12pm-11pm", openingTimes.concatenate())
    }
    @Test
    fun `Second test from te challenge`() {
        val openingTimes:OpeningTimes = listOf(
            OpeningTime(listOf("Monday"),"00:00","00:00"),
            OpeningTime(listOf("Tuesday","Wednesday","Thursday"),"17:00","22:00"),
            OpeningTime(listOf("Wednesday","Thursday"),"12:00","14:00"),
            OpeningTime(listOf("Friday","Saturday"),"12:00","22:30"),
            OpeningTime(listOf("Sunday"),"12:00","17:00"))

        assertEquals("Mon: Closed | Tue-Thu: 5pm-10pm | Wed-Thu: 12pm-2pm | Fri-Sat: 12pm-10:30pm | Sun: 12pm-5pm", openingTimes.concatenate())
    }
}