package etajer.pos

interface SaleUnit {
    val name: String
    /**
     * The Unitary price of the this sale unit.
     *
     * Only implementations of SaleUnit should decide/know how to set/calc/fetch their price.
     *
     * It could be a fixed price ind DB, or calculated based on another Unit price in case of a package,
     * or a Promotional price...etc.
     */
    val price: Double
}