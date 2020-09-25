package etajer.pos.data.fake

import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import etajer.pos.objects.SaleUnit
import etajer.pos.objects.cart.SaleUnitBySku
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun createFakeSaleUnit(productName: String,
                       unitName: String = "",
                       unitQty: Int = 1,
                       unitPrice: Double) = object : SaleUnit {

    override val name: String = "$productName (${if (unitName.isNotBlank()) unitName else "x$unitQty"})"
    override val price: Double = unitPrice
}

object FakeSKUs {
    const val FACTO = "FACTO"
    const val MLK500 = "MLK50O"
    const val MLK1000 = "MLK1000"
    const val IFRI1B = "IFRI1B"
    const val IFRI6B = "IFRI6B"
}

//TODO: this object should implements an interface SaleUnits
object FakeSaleUnits : SaleUnitBySku {
    val ifriBottle = createFakeSaleUnit(productName = "Watter Ifri", unitPrice = 35.00)
    val ifriFardo = createFakeSaleUnit(productName = "Water Ifri", unitPrice = 170.00, unitName = "Fardo", unitQty = 6)
    val coffeeFacto = createFakeSaleUnit(productName = "Coffee Facto", unitPrice = 185.00)
    val milkospray500g = createFakeSaleUnit(productName = "Milkospray 500g", unitPrice = 370.00)
    val milkospray1Kg = createFakeSaleUnit(productName = "Milkospray 1Kg", unitPrice = 650.00)

    private val data = mapOf(
            FakeSKUs.FACTO to coffeeFacto,
            FakeSKUs.MLK500 to milkospray500g,
            FakeSKUs.MLK1000 to milkospray1Kg,
            FakeSKUs.IFRI1B to ifriBottle,
            FakeSKUs.IFRI6B to ifriFardo
    )

    override fun find(sku: String): Optional<SaleUnit> = data[sku].toOptional()
}

class FakeSaleUnitsTest {
    @Test
    fun `create fake SaleUnit with specific unitName`() {
        // Arrange
        val ifriFardo = createFakeSaleUnit(
                productName = "Water Ifri",
                unitName = "Fardo",
                unitQty = 6,
                unitPrice = 180.00)
        // Act
        // Assert
        assertEquals("Water Ifri (Fardo)", ifriFardo.name)
    }

    @Test
    fun `create fake SaleUnit without a unitName`() {
        // Arrange
        val ifri6B = createFakeSaleUnit(
                productName = "Water Ifri",
                unitQty = 6,
                unitPrice = 180.00)
        // Act
        // Assert
        assertEquals("Water Ifri (x6)", ifri6B.name)
    }
}