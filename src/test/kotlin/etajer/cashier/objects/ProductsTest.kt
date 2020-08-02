package etajer.cashier.objects

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class ProductsTest {

    val products = object : Products {

        override fun byId(id: Int): Optional<Product> =
                if (id == 0) Optional.empty()
                else
                    Optional.of(
                            object : Product {
                                override val id: Int = id
                                override val name: String = "test prod"
                                override val price: Double get() = TODO("Not yet implemented")
                            }
                    )

        override fun byBarCode(barCode: String): Optional<Product> {
            return if (barCode.isBlank()) Optional.empty()
            else Optional.of(
                    object : Product {
                        override val id: Int = 0
                        override val name: String  get()  = TODO("Not yet implemented")
                        override val price: Double   get() = TODO("Not yet implemented")
                    }
            )
        }
    }

    @Nested
    inner class GetProductById {
        @Test // happy
        fun `when the id exist, get an Optional of Product`() {
            // Arrange
            // Act
            val p = products.byId(123)
            // Assert
            assertTrue(p.isPresent)
        }

        @Test
        fun `when the id doesn't exist, get an empty Optional`() {
            // Arrange
            // Act
            val p = products.byId(0)
            // Assert
            assertFalse(p.isPresent)
        }
    }

    @Nested
    inner class FindByBarcode {
        @Test
        fun `when the barcode exist, should get an Optional of Product`() {
            // Arrange
            val p: Optional<Product> = products.byBarCode("123")
            // Act
            // Assert
            assertTrue(p.isPresent)
        }

        @Test
        fun `when the barcode doesn't exist, get an empty Optional`() {
            // Arrange
            val p = products.byBarCode("")
            // Act
            // Assert
            assertFalse(p.isPresent)
        }
    }
}