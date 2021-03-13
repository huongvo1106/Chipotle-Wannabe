Current version: v2.0
Recommendation engine for java gui

(changelog:)
v1.0
Finished customer recommendations in v1.0

rec.recToCustomer("B",1,conn) will return a list of beverages to recommend to customer prioritizing price, 0 for stock. If possible will return list with highest of both.

To use:

recommendation rec = new recommendation();
rec.recToCustomer("<letter type (B, D, S, E)>, <0,1>, conn);

NOT IMPLEMENTED IN GUI YET

v2.0
NOT IMPLEMENTED IN GUI YET!!!

ADDED dateStruct.java
Initialize with (<day>, <month>, <year>) ex. dateStruct startDate = new dateStruct(3,12,2021);
Note: months and days can be passed as single digits or double digits (03 or 3 are valid), years must be 4

Customer recommendatons have stayed the same.
Finished Manager recommendations. Can now call function rec.Summary(startDate, endDate, conn);

This will return a summary of of items sold for a period of time as well as recommendations for stock or price changes.

startDate and endDate must be passed in, they must be prompted for as there is no default value. Note that summary returns a string. An example is given below:

Summary for 2020-12-12-2020-12-12

Sales:

 Entrees:
 Burrito:70
 Burrito Bowl:81
 Salad:88
 Taco:117
 Quesadilla:104
 Torta:90
 Flautas:54

 Sides:
 Chips:104
 Nachos:215
 Guacamole:182
 Rice:103

 Dessert:
 Flan:178
 Cookie:274
 Tres Leches Cake:152

 Beverages:
 Fountain Drink:61
 Lemonade:174
 Tea:130
 Orange Juice:158
 Water:81

 Recommendations:


 Entrees:
 The following had the lowest sales, consider stocking less or lowering the price:
 Flautas
 The following had the highest sales, consider stocking more or increasing the price:
 Taco

 Sides:
 The following had the lowest sales, consider stocking less or lowering the price:
 Rice
 The following had the highest sales, consider stocking more or increasing the price:
 Nachos

 Dessert:
 The following had the lowest sales, consider stocking less or lowering the price:
 Tres Leches Cake
 The following had the highest sales, consider stocking more or increasing the price:
 Cookie

 Beverages:
 The following had the lowest sales, consider stocking less or lowering the price:
 Fountain Drink
 The following had the highest sales, consider stocking more or increasing the price:
 Lemonade

NOTHING HAS BEEN DONE IN THE GUI YET.
