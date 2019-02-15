@SS
@SS-GOODS-VAR-OC-INCREASE-VEHICLE
@OLCS-21133

Feature: Goods Variation increase vehicle count for an OC

  Background:
    Given i have a valid "goods" "sn" licence

  Scenario: Create a variation and increase vehicle count
    When A selfserve user increases the vehicle authority count
    Then a status of update required should be shown next to financial evidence

  Scenario: Increasing the vehicle count to an invalid character for required vehicles
    When A selfserve user increases the vehicle required count by invalid characters
    Then An error message should appear

  Scenario: Increasing the vehicle count to an invalid character for authorised vehicles
    When A selfserve user increases the vehicle authority by invalid charecters
    Then An error should appear