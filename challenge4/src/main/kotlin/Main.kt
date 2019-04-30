fun main(args: Array<String>) {
    println("Hello world")
}

fun sortVouchers(vouchers: String): String {
    val listOfVouchers = vouchers.split(";")

    if (listOfVouchers.size < 2) return vouchers

    val voucherDate0 = listOfVouchers[0].substring(0,6).toInt() * 10
    val voucherDate1 = listOfVouchers[1].substring(0,6).toInt() * 10

    val voucherstatusValue0 = if (listOfVouchers[0].contains("Activated")) 0 else 1
    val voucherstatusValue1 = if (listOfVouchers[1].contains("Activated")) 0 else 1

    val voucherStatusGroup0 = if (listOfVouchers[0].contains("Activated") || listOfVouchers[0].contains("Available") ) 0 else 10000000
    val voucherStatusGroup1 = if (listOfVouchers[1].contains("Activated") || listOfVouchers[1].contains("Available") ) 0 else 10000000

    val voucher0Rank = voucherStatusGroup0 + voucherDate0

    return when {
        voucherStatusGroup0 < voucherStatusGroup1  -> vouchers
        voucherStatusGroup0 > voucherStatusGroup1  -> listOfVouchers[1] + ";" + listOfVouchers[0]
        voucherDate0 < voucherDate1  -> vouchers
        voucherDate0 > voucherDate1  -> listOfVouchers[1] + ";" + listOfVouchers[0]
        voucherstatusValue0 < voucherstatusValue1 -> vouchers
        voucherstatusValue0 > voucherstatusValue1 -> listOfVouchers[1] + ";" + listOfVouchers[0]
        else -> vouchers
    }
}