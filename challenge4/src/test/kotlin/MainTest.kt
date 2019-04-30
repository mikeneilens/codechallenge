import junit.framework.TestCase
import org.junit.Test

class MainTest {
    @Test
    fun `Single voucher returns the same single voucher`() {
        TestCase.assertEquals("190112:Activated:aaaa",sortVouchers("190112:Activated:aaaa"))
    }

    @Test
    fun `Test two vouchers in the correct sequence return the same two vouchers in the correct sequence`() {
        TestCase.assertEquals("190112:Activated:aaaa;190113:Activated:bbbb",sortVouchers("190112:Activated:aaaa;190113:Activated:bbbb"))
    }

    @Test
    fun `Test two vouchers in the incorrect date sequence return the same two vouchers in the correct date sequence`() {
        TestCase.assertEquals("190112:Activated:bbbb;190113:Activated:aaaa",sortVouchers("190113:Activated:aaaa;190112:Activated:bbbb"))
    }

}