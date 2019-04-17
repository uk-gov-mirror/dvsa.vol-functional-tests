@INT
@OLCS-22990
@regression
Feature: View Surrender Menu and details on Internal

  Background:
    Given i have a valid "public" "sn" licence with an open case and bus reg
    And i choose to surrender my licence with verify
    When a caseworker views the surrender details

  Scenario: Surrender details should be displayed on Internal
    And any open cases should be displayed
    And any open bus registrations should be displayed
    And tick boxes should be displayed
    Then the Surrender button should not be clickable

  Scenario: Surrender after closing cases & bus Reg
    And the open case and bus reg is closed
    And the tick boxes are checked and saved
    When the Surrender button is clicked
    Then the licence should be surrendered