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

    @Test
    fun `Test two current vouchers in the incorrect status sequence return the same two vouchers in the correct status sequence`() {
        TestCase.assertEquals("190112:Activated:bbbb;190112:Available:aaaa",sortVouchers("190112:Available:aaaa;190112:Activated:bbbb"))
    }

    @Test
    fun `Test two redeemed or expired vouchers in the incorrect status sequence return the same two vouchers in the correct status sequence`() {
        TestCase.assertEquals("190112:Expired:bbbb;190112:Redeemed:aaaa",sortVouchers("190112:Redeemed:aaaa;190112:Expired:bbbb"))
    }

    @Test
    fun `Test two vouchers in the incorrect status group sequence return the same two vouchers in the correct status group sequence`() {
        TestCase.assertEquals("190112:Available:bbbb;190112:Expired:aaaa",sortVouchers("190112:Expired:aaaa;190112:Available:bbbb"))
    }

    @Test
    fun `Test three vouchers in the incorrect date sequence return the same three vouchers in the correct date sequence`() {
        TestCase.assertEquals("190112:Activated:cccc;190113:Activated:bbbb;190114:Activated:aaaa",sortVouchers("190114:Activated:aaaa;190113:Activated:bbbb;190112:Activated:cccc"))
    }

    @Test
    fun `Test empty string`() {
        TestCase.assertEquals("",sortVouchers(""))
    }
}