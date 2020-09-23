package etajer.pos.objects

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProductsTest {

    @Nested
    inner class AddNewProduct {
        @Test
        fun `add new product`() {
            // Arrange
            // Act
            val prod1 = FakeProducts.add("My Product 1", 100.0)
            val prod2 = FakeProducts.add("My Product 2", 150.0)
            // Assert
            assertTrue(prod1.id != prod2.id)
            println(prod1)
            println(prod2)
        }
    }

    @Nested
    inner class GetProductById {
        @Test // happy path
        fun `when the id exist, get an Optional of Product`() {
            // Arrange
            // Act
            val p = FakeProducts.byId(123)
            // Assert
            assertNotNull(p.toNullable())
        }

        @Test
        fun `when the id doesn't exist, get an empty Optional`() {
            // Arrange
            // Act
            val p = FakeProducts.byId(0)
            // Assert
            assertEquals(None, p)
        }
    }

    @Nested
    inner class FindByBarcode {
        @Test
        fun `when the barcode exist, should get an Optional of Product`() {
            // Arrange
            val p: Optional<Product> = FakeProducts.byBarCode("123")
            // Act
            // Assert
            assertNotNull(p.toNullable())
        }

        @Test
        fun `when the barcode doesn't exist, get an empty Optional`() {
            // Arrange
            val p = FakeProducts.byBarCode("")
            // Act
            // Assert
            assertEquals(None, p)
        }
    }
}

object FakeProducts : Products {
    private var nextId = 1;
    private val data = mutableMapOf<Int, Product>()

    override fun add(name: String, price: Double): Product {
        // create a fake product TODO: move this logic to a helper function!
        val newProd = object : Product {
            override val id by lazy { nextId++ }
            override val name = name
            override val price = price
            override fun toString() = "FakeProduct(id:$id, name:$name, price:$price)"
        }
        data[newProd.id] = newProd
        return newProd
    }

    override fun byId(id: Int): Optional<Product> =
            if (id == 0) None
            else Some(
                    object : Product {
                        override val id: Int = id
                        override val name: String = "test prod"
                        override val price: Double get() = TODO("Not yet implemented")
                    }
            )

    override fun byBarCode(barCode: String): Optional<Product> {
        return if (barCode.isBlank()) None
        else Some(
                object : Product {
                    override val id: Int = 0
                    override val name: String get() = TODO("Not yet implemented")
                    override val price: Double get() = TODO("Not yet implemented")
                }
        )
    }
}
