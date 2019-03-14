@backend-surrender-off
@SS

Feature: Surrenders toggle switched off

  Background:
    Given surrenders has been switched "off"
    When i have a valid "public" "si" licence

  Scenario: I am unable to use any surrender feature
    Then as "selfserve" user I cannot surrender a licence
    Then as "selfserve" user I cannot update a surrender