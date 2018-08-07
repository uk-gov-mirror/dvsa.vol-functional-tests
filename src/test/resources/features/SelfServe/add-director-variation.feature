@SS
@SS-ADD-DIRECTOR
Feature: Add a director variation
  Background:
    Given i have a valid "public" licence

  Scenario: Director without any convictions
    Given i add a new person
    And i enter financial details
    When i enter previous convictions details
    Then a new director should be added to my licence
    And a non urgent task is created in internal