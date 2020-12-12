package etajer.api.product

interface Product {
    /**
     * The id of a Product should be managed by persistence infrastructure and assigned when a new Product is created.
     */
    val id : Int
    val name: String
    /**
     * This price would be the default if any, otherwise Product could have multiple sale prices,
     * that are defined by different criteria, for example: promotional price, discount price, by qty...etc */
    val price: Double // TODO: define a Price type instead of naked Double
}