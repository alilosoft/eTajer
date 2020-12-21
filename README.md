# eTajer
> A software for stores & retail business management.  
## Business Rules
### Updated: 2020-09-06
* A Product should have a name, purchase price, selling price and inventory Qty.
* ~~A Product inventory should be managed in at least one Unitary Unit~~ 
* ~~A Product inventory could be managed in multiple Packaging Units of != quantities and != prices~~
* ~~Each Product Unit could have multiple BarCodes/SKU~~

### Updated: 2020-12-21 
* A `Product` should have only one __unitary__ `SaleUnit` for inventory and sales management.
* A Product could have multiple __packaging__ `SaleUnit`s of != packed quantities. 
* Each __packaging__ `SaleUnit` could have a fixed price, or a calculated price based on the packed Qty.
* Each `SaleUnit` should have a unique BarCode or SKU.
  