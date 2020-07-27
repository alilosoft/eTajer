package etajer.cashier.objects

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ProductAssumptions {

    fun testProduct() =  object : Product {
        override val id: Int = 0
        override val name: String = "test product"
    }

    @Test
    fun `a Product should have a readonly id property`() {
        // Arrange
        val p = testProduct()
        // Act
        // Product(0).id = 100 //compilation error
        // Assert
        assertNotNull(p.id)
    }

    @Test
    fun `a Product should have a readonly name property`() {
        // Arrange
        val p = testProduct()
        // Act
        // p.name = "immutable" // compile error
        // Assert
        assertNotNull(p.name)
    }

}