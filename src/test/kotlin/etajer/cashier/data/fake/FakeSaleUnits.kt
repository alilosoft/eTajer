package etajer.cashier.data.fake

import etajer.cashier.objects.SaleUnit

object FakeSaleUnits {
    private val data = mapOf(
            "FACTO" to CoffeeFacto,
            "MLK500" to Milkospray500g,
            "MLK1000" to Milkospray1Kg,
            "IFRI1" to WaterBottle,
            "IFRI6" to WaterFardo
    )
    fun bySku(sku: String) = data[sku]
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