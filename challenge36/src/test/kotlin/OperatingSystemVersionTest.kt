import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.OperatingSystemVersion

class OperatingSystemVersionTest: StringSpec ({
    "operating system version 1.2.3 is equal to operating system version 1.2.3" {
        OperatingSystemVersion(1,2,3).equals(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 1.2.3 is not equal to operating system version 1.2.4" {
        OperatingSystemVersion(1,2,3).equals(OperatingSystemVersion(1,2,4)) shouldBe false
    }
    "operating system version 1.2.3 is not equal to operating system version 1.3.3" {
        OperatingSystemVersion(1,2,3).equals(OperatingSystemVersion(1,3,3)) shouldBe false
    }

    "operating system version 1.2.3 is not greater than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,3).greaterThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 0.2.3 is not greater than operating system version 1.2.3" {
        OperatingSystemVersion(0,2,3).greaterThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.2.4 is greater than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,4).greaterThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 1.3.3 is greater than operating system version 1.2.3" {
        OperatingSystemVersion(1,3,3).greaterThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 1.2.3 is not less than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,3).lessThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.2.4 is not less than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,4).lessThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.2.2 is less than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,2).lessThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }

    "operating system version 1.2.3 is major equals operating system version 1.3.3" {
        OperatingSystemVersion(1,2,3).majorEquals(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 2.2.3 is not major equals operating system version 1.2.3" {
        OperatingSystemVersion(2,2,3).majorEquals(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.2.3 is not major greater than operating system version 1.3.3" {
        OperatingSystemVersion(1,2,3).majorGreaterThan(OperatingSystemVersion(1,3,3)) shouldBe false
    }
    "operating system version 0.2.3 is not major greater than operating system version 1.2.3" {
        OperatingSystemVersion(0,2,3).majorGreaterThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 2.2.3 is not major greater than operating system version 1.2.3" {
        OperatingSystemVersion(2,2,3).majorGreaterThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 1.2.3 is not major less than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,3).majorlessThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.1.3 is not major less than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,2).majorlessThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 0.2.3 is major less than operating system version 1.2.3" {
        OperatingSystemVersion(0,1,2).majorlessThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }

    "operating system version 1.2.3 is minor equals operating system version 1.2.4" {
        OperatingSystemVersion(1,2,3).minorEquals(OperatingSystemVersion(1,2,4)) shouldBe true
    }
    "operating system version 1.3.3 is not minor equals operating system version 1.2.4" {
        OperatingSystemVersion(1,3,3).minorEquals(OperatingSystemVersion(1,2,4)) shouldBe false
    }
    "operating system version 2.2.3 is minor greater than operating system version 1.2.3" {
        OperatingSystemVersion(2,2,3).minorGreaterThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 1.3.3 is minor greater than operating system version 1.2.3" {
        OperatingSystemVersion(1,3,3).minorGreaterThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }
    "operating system version 1.2.4 is not minor greater than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,4).minorGreaterThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.2.3 is not minor less than operating system version 1.2.3" {
        OperatingSystemVersion(1,2,3).minorlessThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.3.3 is not minor less than operating system version 1.2.3" {
        OperatingSystemVersion(1,3,3).minorlessThan(OperatingSystemVersion(1,2,3)) shouldBe false
    }
    "operating system version 1.1.3 is minor less than operating system version 1.2.3" {
        OperatingSystemVersion(1,1,3).minorlessThan(OperatingSystemVersion(1,2,3)) shouldBe true
    }


})