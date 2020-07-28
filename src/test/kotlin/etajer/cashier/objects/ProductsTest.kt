package etajer.cashier.objects

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ProductsTest {

    val products = object: Products {
        override fun find(id: Int): Optional<Product> {
            return Optional.empty()
        }
    }

    @Test
    fun `try to find a Product by it's id, when not exist`() {
        // Arrange
        // Act
        val p = products.find(0)
        // Assert
        assertFalse(p.isPresent)
    }
}