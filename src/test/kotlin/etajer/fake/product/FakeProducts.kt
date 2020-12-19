package etajer.fake.product

import etajer.api.product.Product
import etajer.api.product.Products
import etajer.api.product.SaleUnits
import java.math.BigDecimal
import kotlin.random.Random

fun fakeProduct() = object: Product {
    override val name: String
        get() = TODO("Not yet implemented")
    override val purchasePrice: BigDecimal
        get() = TODO("Not yet implemented")
    override val salePrice: BigDecimal
        get() = TODO("Not yet implemented")
    override val qty: Int
        get() = TODO("Not yet implemented")
    override val saleUnits: SaleUnits
        get() = TODO("Not yet implemented")

}

object FakeProducts : Products {
    private var nextId = 1;
    private val data = mutableMapOf<Int, Product>()

    override fun add(name: String, price: Double): Product {
        val prod = fakeProduct()
        data[nextId++] = prod
        return prod
    }

    override fun byBarCode(barCode: String): Product? =
        if (barCode.isBlank()) null
        else fakeProduct()
}