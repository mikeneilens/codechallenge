### Challenge 15

First off I created a class for each of these:

* DiscountForAnEAN

* DeliveryToAShop

* DeliveryToADepot

There is a constructor function for each of these classes that would take an array of values to create an object.
A general purpose function called **String.toListOfObjects()** takes a string containing a CSV and any object with interface **CreatesObjectFromAListOfStrings**. It creates a list of the appropriate object. 

To calculate the percentage discount for each supplier I worked out that the relationship between the different types are:

EAN <- Depot/Item <- Supplier.

I createde class **EANRebateCalculator** which contains a list of **deliveriesToShopRebateCalculator**s. Each DeliveryToShopRebateCalcuator contains a list of **SupplierRebate**s. 

When constructing each DepotRebateCalculator, the percentage rebate allocated to each Depot/Item is calculated. This percentage is then used when constructing each SupplierRebate for each Depot/Item. Percentage rebate for each supplier is calculated as

 <Percent supplied by the Depot/Item to the shop> X (Percent supplied by the suplier to the Depot/Item). 

If a Depot/Item is supplied to a shop but a supplier doesn't supply a Depot/Item then the calculation will be incorrect but in theory we shouldn't be giving shops things we haven't received!
 
A getter returns a list of SupplierRebates. The supplier is repeated if it supplies more than one depot/item for the same EAN so there is also a getter than aggregates the SupplierRebates so that there is only one value for each supplier with %rebate summed up.  

A function is used to calculate rebates for each EAN. The result of this is summed by product using Kotlin .groupingBy() and .aggregate().

### Bonus challenge
The solution assumes an input of a list of EANs that are all included in the same promotion.
Each EANSold element in the list has a quantity.

The final solution follows two steps:

* Convert each EANSold so that the EAN is repeated with a quantity of 1 instead of listed once with a quantiy >= 1. 

* Sort the list of EANs by price (descending) and calculate the discount offered on every 3rd item on the list.
