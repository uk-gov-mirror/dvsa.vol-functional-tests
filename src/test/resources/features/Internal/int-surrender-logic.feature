@OLCS-22913
Feature: Logic for Surrender menu item

  Background:
    Given i have a valid "goods" "si" licence
    And my application to surrender is under consideration

  Scenario: Surrender Licence
    When the caseworker approves the surrender
    Then the licence status should be "surrendered"
    And the surrender menu should be hidden
    And the licence details page should display

  Scenario: Attempt to Withdraw surrender
    When the caseworker attempts to withdraw the surrender
    Then a modal box is displayed
    And a prompt message is displayed

  Scenario: Confirm surrender withdraw
    When the caseworker attempts to withdraw the surrender
    And the caseworker confirms the withdraw
    And the licence status should be "withdrawn"
    Then the modal box is hidden
    And the surrender menu should be hidden

  Scenario: Cancel surrender withdraw
    When the caseworker attempts to withdraw the surrender
    And the caseworker cancels the withdraw
    And the licence status should be "valid"
    Then the modal box is hidden
    And the surrender menu should be displayed