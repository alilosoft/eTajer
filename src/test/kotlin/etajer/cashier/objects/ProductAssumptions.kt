package etajer.cashier.objects

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

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

    @Test
    fun `Product id property should be readonly`() {
        //Product(0).id = 100 //compilation error
    }

}

class Product(val id: Int) {

}
