import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun`description of a list containing A is A Null`() {
        val firstNode = Node("A")
        assertEquals("A Null",getDescription(firstNode))
    }
    @Test
    fun`description of a list containing A, B is A B Null`() {
        val firstNode = Node("A", Node("B"))
        assertEquals("A B Null",getDescription(firstNode))
    }
    @Test
    fun`description of a list containing A, B, C is A B C Null`() {
        val firstNode = Node("A", Node("B",Node("C")))
        assertEquals("A B C Null",getDescription(firstNode))
    }


    @Test
    fun `adding B to a node containing A results in A,B`() {
        val firstNode = Node("A")
        addToList(firstNode,"B")
        assertEquals("A B Null", getDescription(firstNode))
    }
    @Test
    fun `adding C to a node containing A,B results in A,B,C`() {
        val firstNode = Node("A", Node("B"))
        addToList(firstNode,"C")
        assertEquals("A B C Null", getDescription(firstNode))
    }

    @Test
    fun `mapping a list of one string to a list of integers returns a list of one integers`() {
        val firstNode = Node("1")
        val integerNode = convertToInteger(firstNode)

        assertEquals("1 Null", getDescription(integerNode))
    }
    @Test
    fun `mapping a list of strings to a list of integers returns a list of integers`() {
        val firstNode = Node("1", Node("2", Node("3")))
        val integerNode = convertToInteger(firstNode)

        assertEquals("1 2 3 Null", getDescription(integerNode))
    }

    @Test
    fun `reversing a list of A B results in a list of B A`() {
        val firstNode = Node("A", Node("B"))
        val reversedNode = reverse(firstNode)
        assertEquals("B A Null", getDescription(reversedNode))
    }
    @Test
    fun `reversing a list of A B C results in a list of C B A`() {
        val firstNode = Node("A", Node("B",Node("C")))
        val reversedNode = reverse(firstNode)
        assertEquals("C B A Null", getDescription(reversedNode))
    }
}

