package etajer.cashier.jdbc

import com.vladsch.kotlin.jdbc.session
import com.vladsch.kotlin.jdbc.sqlQuery
import etajer.cashier.objects.Product
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductIntegrationTests {
    @Test
    fun `a Product should fetch its name from DB`() {
        // Arrange
        // TODO: make sure a Product with id = 123 exist!
        val id = 123
        val productName = "SHAMPOIN SUNSILK GM MARRON"
        // Act
        val p = DbProduct(id)
        // Assert
        assertEquals(productName , p.name)
    }

    @Test
    fun `a Product should fetch its price`() {
        // Arrange
        val p = DbProduct(123)
        // Act
        val price = p.price
        // Assert
    }
}

class DbProduct(override val id: Int) : Product {
    private val dataSource = DerbyDb.dataSource

    override val name: String
        get() {
            val query = sqlQuery("SELECT des AS name FROM produit WHERE id = ?", id)
            val name = session(dataSource).first(query) { row -> row.string("name") }
            return name ?: throw IllegalStateException("Product name not found with id = $id")
        }
    override val price: Double
        get() = TODO("Not yet implemented")
}