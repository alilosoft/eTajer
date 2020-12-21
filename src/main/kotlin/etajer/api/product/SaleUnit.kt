package etajer.api.product

import arrow.core.Either
import java.math.BigDecimal

interface SaleUnit {
    val sku: String
    val desc: String

    /**
     * The Unitary price of the this sale unit.
     * Only implementations of SaleUnit should decide/know how to set/calc/fetch their price.
     * It could be a fixed price ind DB, or calculated based on another Unit price in case of a package,
     * or a Promotional price...etc.
     */
    val price: BigDecimal

    /**
     * Number of unitary units packed by this SaleUnit.
     * Should be > 1 if it represent a package otherwise  = 1
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