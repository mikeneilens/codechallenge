import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.*

class FilterTest: StringSpec ({
    val oS_1_2_3 = OperatingSystemVersion(1,2,3)
    val oS_1_2_4 = OperatingSystemVersion(1,2,4)

    "when a users operatingsystem is null OsVersionEquals always returns false" {
        OsVersionEquals(oS_1_2_3).shouldInclude(null) shouldBe false
    }
    "when a users operatingsystem does not match the OsVersionEquals operating system always returns false" {
        OsVersionEquals(oS_1_2_3).shouldInclude(oS_1_2_4) shouldBe false
    }
    "when a users operatingsystem does match the OsVersionEquals operating system always returns false" {
        OsVersionEquals(oS_1_2_3).shouldInclude(oS_1_2_3) shouldBe true
        OsVersionEquals(oS_1_2_4).shouldInclude(oS_1_2_4) shouldBe true
    }

    "when a users operatingsystem is null OsVersionGreaterThan always returns false " {
        OsVersionGreaterThan(oS_1_2_3).shouldInclude(null) shouldBe false
    }
    "when a users operatingsystem matches the operating system of OsVersionGreaterThan always returns false" {
        OsVersionGreaterThan(oS_1_2_3).shouldInclude(oS_1_2_3) shouldBe false
    }
    "when a users operatingsystem is less than the operating system of OsVersionGreaterThan always returns true" {
        OsVersionGreaterThan(oS_1_2_4).shouldInclude(oS_1_2_3) shouldBe false
    }
})