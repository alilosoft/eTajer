package etajer.cashier.objects

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ProductAssumptions {

    @Test
    fun `a Product should have a readonly id property`() {
        // Arrange
        val p = Product(100)
        // Act
        // Product(0).id = 100 //compilation error
        // Assert
        assertNotNull(p.id)
    }

    @Test
    fun `a Product should have a readonly name property`() {
        // Arrange
        val p = Product(0)
        // Act
        // p.name = "immutable" // compile error
        // Assert
        assertNotNull(p.name)
    }

}