@SS-ADD-DIRECTOR
Feature: Add a director variation

  Background:
    Given i have a valid "public" licence
    And i create a director change variation

  Scenario: Add a new director in self-serve
    Given i add a new person
    And i enter financial details
    And i enter previous convictions details
    Then a new director should be added to my licence