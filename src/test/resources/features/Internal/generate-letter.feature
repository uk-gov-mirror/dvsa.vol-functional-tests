Feature: Generate letter pop up should contain letter details

  Background:
    Given I have logged in to internal
    And I have an internal application

  Scenario:
    When I generate a letter
    Then The pop up should contain letter details