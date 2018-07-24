@SS
@PSV-LAST-TM-TRIGGER
@-OLCS-19479

Feature: Set and check criteria for triggering automatic letter

  Background:
    Given i have a valid "public" licence

  Scenario: Generate letter for valid licence when ss removes last TM
    When a self-serve user removes the last TM
    Then a flag should be set in the DB