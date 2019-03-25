@SS
@SURRENDER-START-PAGE
@OLCS-21944
Feature: Checking the start page for surrenders

  Scenario Outline: PSV and Goods licence
    Given i have a valid "<LicenceType>" "sn" licence
    When i choose to surrender my licence
    Then the correct page heading for "<LicenceType>" should be displayed
    And the correct instructions for "<LicenceType>" should be displayed
    And the correct licence number should be displayed

    Examples:
      | LicenceType |
      | public      |
      | goods       |
