package etajer.api.product

import arrow.core.Either
import java.math.BigDecimal

interface SaleUnit {
    val sku: String
    val desc: String

    /**
     * The final sale price of the item represented by this unit.
     * The logic of fetching/calculating this price should be
     * encapsulated by the impl. It could be a simple value in a DB
     * or calculated with some logic.
     */
    val price: BigDecimal

    /**
     * Number of unitary units packed by this SaleUnit.
     * if it's = 1 then this unit represent a unitary unit.
     * if it's > 1 then this unit represent a package.
     */
    val packedQty: Int
}

interface SaleUnits : Iterable<SaleUnit> {
    fun add(
        sku: String,
        desc: String = "",
        packedQty: Int = 1,
        unitPrice: BigDecimal = BigDecimal.ZERO
    ): Either<String, SaleUnit>
}

/**
 * Clients that needs to find a SaleUnit using its SKU
 * will need an Impl of this interface
 */
fun interface SaleUnitBySku {
    fun find(sku: String): Either<String, SaleUnit>
}