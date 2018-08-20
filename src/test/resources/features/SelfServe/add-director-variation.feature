@SS
@SS-ADD-DIRECTOR
Feature: Add a director variation

  Background:
    Given i have a valid "public" licence

  Scenario: Director without any convictions
    Given i add a new person
    And i enter "No" to financial details question
    When i enter "No" previous convictions details question
    Then a new director should be added to my licence
    And a non urgent task is created in internal

  Scenario: Director with convictions and bankruptcy
    Given i add a new person
    And i enter "Yes" to financial details question
    When i enter "Yes" previous convictions details question
    Then a new director should be added to my licence
    And an urgent task is created in internal

  Scenario: Director with convictions and no bankruptcy
    Given i add a new person
    And i enter "No" to financial details question
    When i enter "Yes" previous convictions details question
    Then a new director should be added to my licence
    And an urgent task is created in internal

  Scenario: Director with convictions check for snapshot in internal
    When a new director has been added
    Then a snapshot should be created in internal