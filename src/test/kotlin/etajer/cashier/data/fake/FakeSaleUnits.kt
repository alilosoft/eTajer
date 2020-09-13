package etajer.cashier.data.fake

import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import etajer.cashier.objects.Product
import etajer.cashier.objects.SaleUnit

object FakeSaleUnits {
    private val data = mapOf(
            "FACTO" to CoffeeFacto,
            "MLK500" to Milkospray500g,
            "MLK1000" to Milkospray1Kg,
            "IFRI1" to IfriBottle,
            "IFRI6" to IfriFardo
    )

    /**
     * TODO: this function should implements an interface function defined in SaleUnits for example
     */
    fun bySku(sku: String): Optional<SaleUnit> = data[sku].toOptional()
}

val CoffeeFacto = object : SaleUnit {
    override val name: String = "Coffee Facto"
    override val price: Double = 180.00
}

val Milkospray500g = object : SaleUnit {
    override val name: String = "Milkospray 500g"
    override val price: Double = 370.00
}

val Milkospray1Kg = object : SaleUnit {
    override val name: String = "Milkospray 1Kg"
    override val price: Double = 650.00
}

val IfriBottle = object : SaleUnit {
    override val name: String = "Watter Ifri"
    override val price: Double = 35.00
}

val IfriFardo = object : SaleUnit {
    val product = object : Product {
        override val id: Int = 123
        override val name: String = "Water Ifri"
        override val price: Double = 35.0
    }
    val unitName = "Fardo"

    override val name: String = "${product.name} ($unitName)"
    override val price: Double = 180.00
}