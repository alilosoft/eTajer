package etajer.cashier.objects

interface SaleUnit {
    val name: String
    /**
     * todo: according to issue #10, maybe this property should be removed!
     * The number of units packed by this sale unit if it's a package,
     * if it's a Unitary unit this should be 1
     */
    val qty: Int
    /**
     * The implementations of SaleUnit should decide/know how to set the price.
     * It could be fixed unit price, or calculated based on the Product price
     * using the formula S
     */
    val price: Double
}