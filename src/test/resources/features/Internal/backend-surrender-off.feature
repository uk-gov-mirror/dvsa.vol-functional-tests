@INT
@OLCS-22443


Feature: Surrenders toggle switched off

  Background:
    Given surrenders has been switched "off"
    When i have a valid "public" licence

  Scenario: As an internal user I am unable to use any surrender feature
    Then i cannot surrender a licence
    Then i cannot update a surrender
    Then i cannot delete a surrender


