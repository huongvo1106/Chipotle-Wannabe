Current version: v1.0
Recommendation engine for java gui

v1.0
Finished customer recommendations in v1.0

rec.recToCustomer("B",1,conn) will return a list of beverages oto recommend prioritizing price, 0 for stock. If possible will return list with highest of both.

To use:

recommendation rec = new recommendation();
rec.recToCustomer("<letter type (B, D, S, E)>, <0,1>, conn);

NOT IMPLEMENTED IN GUI YET
