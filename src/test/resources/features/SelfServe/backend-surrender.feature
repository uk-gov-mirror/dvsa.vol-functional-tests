@OLCS-22443

Feature: Surrendering a licence

  Background:
    Given i have a valid "public" licence
    And surrenders has been switched "on"

  Scenario: Surrender valid licence
    Then i can surrender my licence
    And i can update surrender details
    And i cannot surrender my licence again
    And i cannot delete my surrender

  Scenario Outline: Surrender Curtailed and Suspended licence
      Given the licence status is "<licenceStatus>"
      Then i can surrender my licence

    Examples:
      | licenceStatus |
      | curtail       |
      | suspend       |

Scenario: Another user attempts to surrender my licence
  Given as a selfserve user i apply for a "goods" licence
  Then i can surrender my licence
  And another user is unable to surrender my licence
  And another user is unable to update my surrender details

