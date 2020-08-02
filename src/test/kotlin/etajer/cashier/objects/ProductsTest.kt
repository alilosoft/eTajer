package etajer.cashier.objects

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class ProductsTest {

    val products = object: Products {
        override fun get(id: Int): Optional<Product> {
            return Optional.empty()
        }
    }

    @Nested
    inner class GetProductById{
        @Test
        fun `when the id doesn't exist, should get an empty Optional`() {
            // Arrange
            // Act
            val p = products.get(0)
            // Assert
            assertFalse(p.isPresent)
        }
    }

}