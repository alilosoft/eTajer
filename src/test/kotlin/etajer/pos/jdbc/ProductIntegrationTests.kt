package etajer.pos.jdbc

import com.vladsch.kotlin.jdbc.session
import com.vladsch.kotlin.jdbc.sqlQuery
import etajer.pos.objects.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
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

class BarCodedProduct(barCode: String): Product {
    override val id: Int
        // select id where barcode = $sku
        get() = TODO("Not yet implemented")
    override val name: String
        // get the product_name + unit_name or variant_name
        get() = TODO("Not yet implemented")
    override val price: Double
        // get/calc the price for the specific SKU if any else get the default price
        // or return a Price object that knows how to calc/fetch the price given a barcode
        get() = TODO("Not yet implemented")

}