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
    Then the licence status should be "valid"
    And the surrender menu should be hidden
    And the user should be able to re apply for a surrender in internal

  Scenario: Cancel valid licence surrender withdraw
    And my application to surrender is under consideration
    When the caseworker attempts to withdraw the surrender
    And the caseworker cancels the withdraw
    Then the modal box is hidden
    And the surrender menu should be displayed

  Scenario Outline: Cancel suspended, curtailed licence surrender withdraw
    And the licence status is "<licence_status>"
    And my application to surrender is under consideration
    When the caseworker attempts to withdraw the surrender
    And the caseworker cancels the withdraw
    Then the user should remain on the surrender details page
    And the modal box is hidden
    And the surrender menu should be displayed

    Examples:
      | licence_status |
      | suspend        |
      | curtail        |

  Scenario Outline: Surrender suspended, curtailed licence
    And the licence status is "<licence_status>"
    And my application to surrender is under consideration
    When the caseworker approves the surrender
    Then the licence status should be "<licence>"
    And the surrender menu should be hidden
    And the licence should not displayed in selfserve

    Examples:
      | licence_status |licence|
      | suspend        |Surrendered       |
      | curtail        |Surrendered       |