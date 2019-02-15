@OLCS-22443

Feature: Surrendering a licence

  Background:
    Given i have a valid "public"  "si" licence
    And surrenders has been switched "on"

  Scenario: Surrender valid licence
    Then as "selfserve" user I can surrender a licence
    And as "selfserve" user I can update surrender details
    And as "selfserve" user I cannot surrender a licence again
    And as selfserve user I cannot delete my surrender

  Scenario Outline: Surrender Curtailed and Suspended licence
      Given the licence status is "<licenceStatus>"
      Then as "selfserve" user I can surrender a licence

    Examples:
      | licenceStatus |
      | curtail       |
      | suspend       |

Scenario: Another user attempts to surrender my licence
  Given as a selfserve user i apply for a "goods" licence
  Then as "selfserve" user I can surrender a licence
  And another user is unable to surrender my licence
  And another user is unable to update my surrender details

