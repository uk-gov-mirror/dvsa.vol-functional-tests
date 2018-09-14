Feature: Countersigning declaration page for the operator (Verify switched off)

  Scenario Outline: Verify switched off check text
    Given I have a "goods" "<Flag>" application
    When I navigate to the declaration page
    Then Signing options are not displayed on the page

 Examples:
    |Flag|
    |GB|
#    |NI|