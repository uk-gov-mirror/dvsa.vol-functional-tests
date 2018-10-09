Feature: Countersigning declaration page for the operator (Verify switched off)

  Background:
    Given verify has been switched off
    When I have a "goods" "<Flag>" application
    And i add a transport manager

  Scenario: Verify switched off operator same as TM
    And the transport manager is the owner
    Then Signing options are not displayed on the page
    And submit to operator button is displayed

  Scenario: Verify switched off operator not TM
    And the transport manager is not the owner
    Then Signing options are not displayed on the page
    And submit to operator button is not displayed
