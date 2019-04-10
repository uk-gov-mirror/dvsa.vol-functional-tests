@INT
@POSTCODE-WARNING
@OLCS-17544
Feature: Operating centre added in different postcode

  Background:
    Given i have a valid "public" "sn" licence

    Scenario: Caseworker adds new OC in a different postcode
      When a caseworker adds a new operating centre out of the traffic area
      Then the postcode warning message should be displayed on internal



