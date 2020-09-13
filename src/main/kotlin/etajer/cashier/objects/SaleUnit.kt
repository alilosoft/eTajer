package etajer.cashier.objects

interface SaleUnit {
    val name: String
    /**
     * The implementations of SaleUnit should decide/know how to set the price.
     * It could be fixed unit price, or calculated based on the Product price
     * using the formula S
     */
    val price: Double
}