fun main(args: Array<String>) {
    println("Hello world")
}

fun sortVouchers(vouchers: String): String {
    val listOfVouchers = vouchers.split(";")

    if (listOfVouchers.size < 2) return vouchers

    val voucherDate0 = listOfVouchers[0].substring(0,6).toInt()
    val voucherDate1 = listOfVouchers[1].substring(0,6).toInt()

    return  if (voucherDate0 < voucherDate1) listOfVouchers[0] + ";" + listOfVouchers[1]
            else listOfVouchers[1] + ";" + listOfVouchers[0]
}