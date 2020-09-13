package etajer.cashier.data.fake

import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import etajer.cashier.objects.Product
import etajer.cashier.objects.SaleUnit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

object FakeSKUs {
    const val FACTO = "FACTO"
    const val MLK500 = "MLK50O"
    const val MLK1000 = "MLK1000"
    const val IFRI1B = "IFRI1B"
    const val IFRI6B = "IFRI6B"
}

object FakeSaleUnits {
    private val data = mapOf(
            FakeSKUs.FACTO to CoffeeFacto,
            FakeSKUs.MLK500 to Milkospray500g,
            FakeSKUs.MLK1000 to Milkospray1Kg,
            FakeSKUs.IFRI1B to IfriBottle,
            FakeSKUs.IFRI6B to IfriFardo
    )

    /**
     * TODO: this object should implements an interface defined in SaleUnits for example
     */
    fun bySku(sku: String): Optional<SaleUnit> = data[sku].toOptional()
}

val IfriBottle = object : SaleUnit {
    val sku = FakeSKUs.IFRI1B
    override val name: String = "Watter Ifri"
    override val price: Double = 35.00
}

fun createFakeSaleUnit(productName: String,
                       unitName: String = "",
                       qty: Int = 1,
                       unitPrice: Double) = object : SaleUnit {
    // these data are encapsulated by hte SaleUnit impl, it could be fetched from a DB by the SKU for example.
    private val nameDb = unitName // or "Fardo" for example, (by user)
    private val qtyDb = qty; // if an impl need to know the Qty packed by a unit, it could fetch it also!
    private val priceDb = unitPrice
    private val productName = productName

    override val name: String = "$productName (${if (nameDb.isNotBlank()) nameDb else "x$qtyDb"})"
    override val price: Double = priceDb
}

val IfriFardo = object : SaleUnit {
    // these data are encapsulated by hte SaleUnit impl, it could be fetched from a DB by the SKU for example.
    private val nameDb = "Fardo" // or "Fardo" for example, (by user)
    private val qtyDb = 6; // if an impl need to know the Qty packed by a unit, it could fetch it also!
    private val priceDb = 180.00
    private val productDb = object : Product {
        override val id: Int = 123
        override val name: String = "Water Ifri"
        override val price: Double = 35.0
    }

    override val name: String = "${productDb.name} (${if (nameDb.isNotBlank()) nameDb else "x$qtyDb"})"
    override val price: Double = 180.00
}

val CoffeeFacto = object : SaleUnit {
    val sku = FakeSKUs.FACTO
    override val name: String = "Coffee Facto"
    override val price: Double = 180.00
}

val Milkospray500g = object : SaleUnit {
    val sku = FakeSKUs.MLK500
    override val name: String = "Milkospray 500g"
    override val price: Double = 370.00
}

val Milkospray1Kg = object : SaleUnit {
    val sku = FakeSKUs.MLK1000
    override val name: String = "Milkospray 1Kg"
    override val price: Double = 650.00
}

class FakeSaleUnitsTest {
    @Test
    fun `build correct unit name`() {
        // Arrange
        val ifriFardo = createFakeSaleUnit(
                productName = "Water Ifri",
                unitName = "Fardo",
                qty = 6,
                unitPrice = 180.00)
        // Act
        // Assert
        Assertions.assertEquals("Water Ifri (Fardo)", IfriFardo.name)
    }
}