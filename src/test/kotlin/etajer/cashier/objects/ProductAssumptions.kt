package etajer.cashier.objects

import org.junit.Test
import kotlin.test.assertNotNull

class ProductAssumptions {
    @Test
    fun `create a Product with it's id`() {
        val id = 100
        val p = Product(id)
        assertNotNull(p, "we should create a Product with an id")
    }

    @Test
    fun `a Product should have an id property`() {
        assertNotNull(Product(100).id)
    }

}

class Product(var id: Int) {

}
