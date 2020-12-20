package etajer.fake

import etajer.api.Price
import etajer.api.product.SaleUnit
import etajer.fake.cart.SaleUnitBySku
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

fun createFakeSaleUnit(
    sku: String,
    productName: String,
    unitDesc: String = "",
    packedQty: Int = 1,
    fixedPrice: Double
) =
    object : SaleUnit {
        override val sku: String = sku
        override val desc: String = "$productName (${if (unitDesc.isNotBlank()) unitDesc else "x$packedQty"})"
        override val price: BigDecimal = Price.Fixed(fixedPrice).value
        override val packedQty: Int = packedQty
    }

object FakeSku {
    const val FACTO = "FACTO"
    const val MLK500 = "MLK50O"
    const val MLK1000 = "MLK1000"
    const val IFRI_BOTTLE = "IFRI1B"
    const val IFRI_FARDO = "IFRI6B"
}

//TODO: this object should implements an interface SaleUnits
object FakeSaleUnits : SaleUnitBySku {

    val ifriBottle = createFakeSaleUnit(
        sku = FakeSku.IFRI_BOTTLE,
        productName = "Watter Ifri",
        fixedPrice = 35.00
    )
    val ifriFardo = createFakeSaleUnit(
        sku = FakeSku.IFRI_FARDO,
        productName = "Water Ifri",
        fixedPrice = 170.00,
        unitDesc = "Fardo",
        packedQty = 6
    )
    val coffeeFacto = createFakeSaleUnit(
        sku = FakeSku.FACTO,
        productName = "Coffee Facto",
        fixedPrice = 185.00
    )
    val milkospray500g = createFakeSaleUnit(
        sku = FakeSku.MLK500,
        productName = "Milkospray 500g",
        fixedPrice = 370.00
    )
    val milkospray1Kg = createFakeSaleUnit(
        sku = FakeSku.MLK1000,
        productName = "Milkospray 1Kg",
        fixedPrice = 650.00
    )

    private val data = mapOf(
        FakeSku.FACTO to coffeeFacto,
        FakeSku.MLK500 to milkospray500g,
        FakeSku.MLK1000 to milkospray1Kg,
        FakeSku.IFRI_BOTTLE to ifriBottle,
        FakeSku.IFRI_FARDO to ifriFardo
    )

    override fun find(sku: String): SaleUnit? = data[sku]

    // as mentioned here: http://disq.us/p/1wxagvf
    // Exceptions are not suitable for user/domain error reporting
    // because: 1) they are very slow 2) the user will not need a stacktrace
    // 3) Exceptions are only for developer (logging, debugging)
    // Instead of throwing Exceptions for control flow use monadic data types
    // like: Option, Either, Result or even a Nullable
    fun findOrThrow(sku: String): SaleUnit = data[sku]
        ?: throw IllegalArgumentException("SKU: $sku not found!")
}

class FakeSaleUnitsTest {
    @Test
    fun `create fake SaleUnit with specific unitName`() {
        // Arrange
        val ifriFardo = createFakeSaleUnit(
            sku = FakeSku.IFRI_FARDO,
            productName = "Water Ifri",
            unitDesc = "Fardo",
            packedQty = 6,
            fixedPrice = 180.00
        )
        // Act
        // Assert
        assertEquals("Water Ifri (Fardo)", ifriFardo.desc)
    }

    @Test
    fun `create fake SaleUnit without a unitName`() {
        // Arrange
        val ifri6B = createFakeSaleUnit(
            sku = FakeSku.IFRI_FARDO,
            productName = "Water Ifri",
            packedQty = 6,
            fixedPrice = 180.00
        )
        // Act
        // Assert
        assertEquals("Water Ifri (x6)", ifri6B.desc)
    }
}