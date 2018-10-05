Feature: Countersigning declaration page for the operator (Verify switched off)

  Background:
    Given I have a new application
    And the transport manager is the operator

  Scenario Outline: Verify switched off check text
    Given I have a "goods" "<Flag>" application
    And the transport manager is the operator
    When I navigate to the declaration page
    Then Signing options are not displayed on the page

    Examples:
      | Flag |
      | GB   |
      | NI   |