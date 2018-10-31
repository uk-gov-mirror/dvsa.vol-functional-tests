@Business Need: As part of the OTC strategic objects for 2018
@SS-TM-Verify
@SS

Feature: Check answers page

  @OLCS-21373
  Scenario: TM has completed journey and is checking Your details section
    Given the operator is on check your answers page
    Then the correct data should be displayed
    And operator makes a change to the place of birth, address and email

