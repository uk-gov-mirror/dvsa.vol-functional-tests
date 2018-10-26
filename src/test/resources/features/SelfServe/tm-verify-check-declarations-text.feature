@SS-Verify-On
@OLCS-20343
Feature: Countersigning declaration page for the operator (Verify switched on)

  Background:
    Given verify has been switched "On"
    When i have a "goods" "NI" partial application
    And i add a transport manager
    And i navigate to the declarations page

  Scenario: Check declaration text for NI
    Then the correct information is displayed on the declaration page

  Scenario: Check that verify button is not displayed when Print and sign is selected as an option
    And the users chooses to sign print and sign
    Then the declaration text and verify button are not displayed