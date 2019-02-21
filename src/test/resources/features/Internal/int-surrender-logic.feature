@OLCS-22913
Feature: Logic for Surrender menu item

  Background:
    Given i have a valid "goods" "si" licence

  Scenario: Surrender Licence
    And my application to surrender is under consideration
    When the caseworker approves the surrender
    Then the licence status should be "surrendered"
    And the surrender menu should be hidden
    And the licence details page should display

  Scenario: Attempt to Withdraw surrender
    And my application to surrender is under consideration
    When the caseworker attempts to withdraw the surrender
    Then a modal box is displayed
    And a prompt message is displayed

  Scenario: Confirm surrender withdraw
    And my application to surrender is under consideration
    When the caseworker attempts to withdraw the surrender
    And the caseworker confirms the withdraw
    And the licence status should be "valid"
    Then the modal box is hidden
    And the surrender menu should be hidden

  Scenario: Cancel valid licence surrender withdraw
    When the caseworker attempts to withdraw the surrender
    And the caseworker cancels the withdraw
    And the licence status should be "valid"
    Then the modal box is hidden
    And the surrender menu should be displayed

  Scenario Outline: Cancel suspended, curtailed licence surrender withdraw
    And the licence status is "<licence_status>"
    And my application to surrender is under consideration
    When the caseworker attempts to withdraw the surrender
    And the caseworker cancels the withdraw
    And the licence status should be "<licence_status>"
    Then the modal box is hidden
    And the surrender menu should be displayed

    Examples:
      | licence_status |
      | suspend        |
      | curtail        |