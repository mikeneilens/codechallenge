### Challenge 15

First of created a class for each of these:

* DiscountForAnEAN

* DeliveryToAShop

* DeliveryToADepot

I created a constructor function for each of these classes that would take an array of values to create an object
I then created a general purpose function called **String.toListOfObjects()** that takes a string containing a CSV and a function to create a function from an array to create a list of the appropriate object. 

To calculate the percentage discount for each supplier I worked out the relationship between the different types are:

EAN <- Depot/Item <- Supplier.

I createde class **EANRebateCalculator** which contains a list of **DepotRebateCalculator**s. Each DepotRebateCalcuator contains a list of **SupplierRebate**s. 

When constructing each DepotRebateCalculator, the percentage rebate allocated to each Depot/Item is calculated. This percentage is then used when constructing each SupplierRebate for each Depot/Item. Percentage rebate for each supplier is calculated as

 <Percent supplied by the Depot/Item to the shop> X (Percent supplied by the suplier to the Depot/Item). 

A getter returns a list of SupplierRebates. The supplier is repeated if it supplies more than one depot or item so there is also a getter than aggregates the SupplierRebates so that there is only one value for each supplier with %rebate summed up.  

A function is used to calculate rebates for each EAN. The result of this is summed by product using Kotlin .groupingBy() and .aggregate().
