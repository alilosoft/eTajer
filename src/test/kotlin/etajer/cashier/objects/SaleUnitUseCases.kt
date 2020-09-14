package etajer.cashier.objects

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SaleUnitUseCases {

    @Test
    fun `a SaleUnit impl could get its data from a DB`() {
        // Arrange
        val saleUnitUseCaseDb = object : SaleUnit {
            // following data are encapsulated by this SaleUnit impl, it could be fetched from a DB by the SKU for example.
            private val unitName = "Fardo" // or "Fardo" for example, (by user)
            private val unitQty = 6; // if an impl need to know the Qty packed by a unit, it could fetch it also!
            private val unitPrice = 180.00
            private val productName = "Water Ifri"

            override val name: String = "$productName (${if (unitName.isNotBlank()) unitName else "x$unitQty"})"
            override val price: Double = unitPrice
        }
        // Act
        // Assert
        Assertions.assertEquals("Water Ifri (Fardo)", saleUnitUseCaseDb.name)
    }
}