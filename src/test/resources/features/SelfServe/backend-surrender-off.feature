@Surrender-Off
@SS

Feature: Surrenders toggle switched off

  Background:
    Given surrenders has been switched "off"
    When i have a valid "public" licence

  Scenario: I am unable to use any surrender feature
    Then i cannot surrender my licence
    Then i cannot update my surrender



