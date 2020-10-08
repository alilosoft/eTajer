package etajer.pos.objects

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SaleUnitUseCases {

    @Test
    fun `a SaleUnit impl should encapsulate its data`() {
        // Arrange
        val fakeSaleUnit = object : SaleUnit {
            // following data are encapsulated this fake impl, it could be fetched from a DB by the SKU for example.
            private val unitName = "Fardo"
            private val unitQty = 6; // if an impl need to know the Qty packed by a unit, it could fetch it also!
            private val unitPrice = 180.00
            private val productName = "Water Ifri"

            // following is the public API of a SaleUnit, an impl should only expose those properties for clients
            override val name: String = "$productName (${if (unitName.isNotBlank()) unitName else "x$unitQty"})"
            override val price: Double = unitPrice
        }
        // Act
        val saleUnitName = fakeSaleUnit.name
        // Assert
        Assertions.assertEquals("Water Ifri (Fardo)", saleUnitName)
    }
}