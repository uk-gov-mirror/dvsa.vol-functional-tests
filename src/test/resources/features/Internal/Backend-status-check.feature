@OLCS-22443

Feature: Setting and Checking licence status

  Background:
    Given i have a valid "public" licence

  Scenario: Surrender valid licence
    Then i can surrender my licence
    And i cannot surrender my licence again

  Scenario Outline: Surrender Curtailed and Suspended licence
      Given the licence status is "<licenceStatus>"
      Then i can surrender my licence

    Examples:
      | licenceStatus |
      | curtail       |
      | suspend       |

Scenario:
  Given as a selfserve user i apply for a "goods" licence
  Then another user is unable to surrender my licence

