package etajer.derby

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.vladsch.kotlin.jdbc.session
import com.vladsch.kotlin.jdbc.sqlQuery
import etajer.pos.Product
import etajer.pos.Products
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import javax.sql.DataSource

//TODO re-enable this test after adding appropriate mechanism (i.e: test containers for db)
// to run the tests against a real (temp) data base!
@Disabled
class ProductsIntegrationTests {
    @Test
    fun `get Product by barcode`() {
        // Arrange
        assertNotNull(DerbyDb.dataSource, "should be connected to a derby database!")
        // Act
        val prod = DbProducts().byBarCode("005")
        // Assert
        assertNotNull(prod.toNullable())
        when (prod) {
            is Some -> prod.value
            is None -> println("not found")
        }
    }
}

class DbProducts(private val dataSource: DataSource = DerbyDb.dataSource,
                 private val getProduct: GetProductFn = ::getDbProduct) : Products {

    // TODO: add logger

    /**
     * insert the new product to DB
     */
    override fun add(name: String, price: Double): Product {
        val sql = "INSERT INTO product SET name = ?, price = ?"
        TODO("Not yet implemented")
        // insert a new prod to DB
    }

    override fun byId(id: Int): Optional<Product> {
        return None
    }

    override fun byBarCode(barCode: String): Optional<Product> {
        val query = sqlQuery("SELECT id_prod FROM en_stock WHERE cod_bar = ?", barCode)
        val id = session(dataSource).first(query) { row -> row.int("id_prod") }
        println("barcode: $barCode => id: $id") // TODO: add logging
        return if (id == null) None else Some(getProduct(id))
    }
}

/**
 * Product factory function
  */
typealias GetProductFn = (Int) -> Product
fun getDbProduct(id: Int): Product = DbProduct(id)