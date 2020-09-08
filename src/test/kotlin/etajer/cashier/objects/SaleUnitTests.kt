package etajer.cashier.objects

class SaleUnitTests {
}

val CoffeeFacto = object : SaleUnit {
    override val name: String = "Coffee Facto"
    override val qty: Int = 1
    override val price: Double = 180.00
}

val Milkospray500g = object : SaleUnit {
    override val name: String = "Milkospray 500g"
    override val qty: Int = 1
    override val price: Double = 370.00
}

val Milkospray1Kg = object : SaleUnit {
    override val name: String = "Milkospray 1Kg"
    override val qty: Int = 1
    override val price: Double = 650.00
}

val WaterBottle = object : SaleUnit {
    override val name: String = "Watter Ifri"
    override val qty: Int = 1
    override val price: Double = 35.00
}

val WaterFardo = object : SaleUnit {
    val productName = "Watter Ifri"
    val unitName = "Fardo"

    override val name: String = "$productName ($unitName)"
    override val qty: Int = 6
    override val price: Double = 180.00
}