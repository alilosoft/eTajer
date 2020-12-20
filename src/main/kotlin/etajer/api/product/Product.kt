package etajer.api.product

import java.math.BigDecimal

interface Product {
    val name: String
    val purchasePrice: BigDecimal
    val salePrice: BigDecimal
    val qty: Int
    val saleUnits: SaleUnits
}

interface Products : Iterable<Product> {
    fun add(
        name: String,
        salePrice: BigDecimal,
        purchasePrice: BigDecimal = BigDecimal.ZERO
    ): Product
}