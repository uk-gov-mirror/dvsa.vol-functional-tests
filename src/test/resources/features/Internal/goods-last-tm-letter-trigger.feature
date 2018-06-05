@INT-GOODS-LAST-TM-TRIGGER
@-OLCS-19479
@WIP
Feature: Set and check criteria for triggering automatic letter

  Background:
    Given i have a valid "goods" licence

  Scenario: Generate letter for valid licence
    Given the transport manager has been removed by an internal user
    And the user confirms they want to send letter
    Then a flag should be set in the DB

  Scenario: Generate letter for suspended licence
    Given the licence status is "suspend"
    When the transport manager has been removed by an internal user
    And the user confirms they want to send letter
    Then a flag should be set in the DB

  Scenario:
    Given the licence status is "curtail"
    When the transport manager has been removed by an internal user
    And the user confirms they want to send letter
    Then a flag should be set in the DB