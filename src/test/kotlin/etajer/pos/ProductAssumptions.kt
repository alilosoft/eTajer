package etajer.pos

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ProductAssumptions {

    private fun testProduct() =  object : Product {
        override val id: Int = 0
        override val name: String = "test product"
        override val price: Double = 10.0
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

    @Test
    fun `a Product should have a readonly price property`() {
        // Arrange
        val p = testProduct()
        // Act
        // p.price = 100.0 // compilation error
        // Assert
        assertNotNull(p.price)
    }

}