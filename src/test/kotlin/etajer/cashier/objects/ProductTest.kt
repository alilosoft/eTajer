package etajer.cashier.objects

import org.junit.Test
import kotlin.test.assertNotNull

class ProductTest {
    @Test
    fun `create a Product with it's id`() {
        val id = 100
        val p = Product(id)
        assertNotNull(p,"we should create a Product with an id")
    }

}

class Product(id: Int) {

}
