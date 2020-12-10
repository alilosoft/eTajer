package etajer.pos

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.random.Random

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
        fun `may returns a Product`() {
            // Arrange
            // Act
            val p = FakeProducts.byId(123)
            // Assert
            assertNotNull(p)
        }

        @Test
        fun `may returns null`() {
            // Arrange
            // Act
            val p = FakeProducts.byId(0)
            // Assert
            assertNull(p)
        }
    }

    @Nested
    inner class FindByBarcode {
        @Test
        fun `may return a Product`() {
            // Arrange
            // Act
            val p = FakeProducts.byBarCode("123")
            // Assert
            assertNotNull(p)
        }

        @Test
        fun `may return null`() {
            // Arrange
            // Act
            val p = FakeProducts.byBarCode("")
            // Assert
            assertNull(p)
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

    override fun byId(id: Int): Product?  =
            if (id <= 0) null
            else object : Product {
                override val id: Int = id
                override val name: String = "test prod"
                override val price: Double get() = Random.nextDouble(100.0)
            }

    override fun byBarCode(barCode: String): Product? =
        if (barCode.isBlank()) null
        else object : Product {
            override val id: Int = Random.nextInt(100)
            override val name: String get() = "test"
            override val price: Double get() = Random.nextDouble(100.0)
        }
}
