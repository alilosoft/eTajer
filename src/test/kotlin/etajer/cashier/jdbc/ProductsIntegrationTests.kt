package etajer.cashier.jdbc

import com.vladsch.kotlin.jdbc.session
import com.vladsch.kotlin.jdbc.sqlQuery
import etajer.cashier.objects.Product
import etajer.cashier.objects.Products
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import javax.sql.DataSource

class ProductsIntegrationTests {
    @Test
    fun `get Product by barcode`() {
        // Arrange
        assertNotNull(DerbyDb.dataSource, "should be connected to a derby database!")
        // Act
        val prod = DbProducts().byBarCode("005")
        // Assert
        assertTrue(prod.isPresent)
        prod.ifPresent { println("prod.id: " + it.id) }
    }
}

class DbProducts(private val dataSource: DataSource = DerbyDb.dataSource,
                 private val getProduct: GetProductFn = ::getDbProduct) : Products {

    // TODO: add logger

    override fun byId(id: Int): Optional<Product> {
        return Optional.empty()
    }

    override fun byBarCode(barCode: String): Optional<Product> {
        val query = sqlQuery("SELECT id_prod FROM en_stock WHERE cod_bar = ?", barCode)
        val id = session(dataSource).first(query) { row -> row.int("id_prod") }
        println("barcode: $barCode => id: $id") // TODO: add logging
        return if (id == null) Optional.empty() else Optional.of(getProduct(id))
    }
}

/**
 * Product factory function
  */
typealias GetProductFn = (Int) -> Product
fun getDbProduct(id: Int): Product = DbProduct(id)