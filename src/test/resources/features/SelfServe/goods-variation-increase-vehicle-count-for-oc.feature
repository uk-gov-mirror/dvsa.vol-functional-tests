@SS
@SS-GOODS-VAR-OC-INCREASE-VEHICLE
@OLCS-21133

  Feature: Goods Variation increase vehicle count for an OC

    Background:
      Given i have a valid "goods" licence

     Scenario: Create a variation and increase vehicle count
       When A selfserve user increases the vehicle authority count
       Then a status of update required should be shown next to financial evidence
