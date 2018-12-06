@WIP
@INT
@OLCS-22443


Feature: Surrenders toggle switched off

  Background:
    Given surrenders has been switched "off"
    When i have a valid "public" licence

  Scenario: As an internal user I am unable to use any surrender feature
    Then as "internal" user I cannot surrender a licence
    Then as "internal" user I cannot update a surrender
    Then as "internal" user I cannot delete a surrender


