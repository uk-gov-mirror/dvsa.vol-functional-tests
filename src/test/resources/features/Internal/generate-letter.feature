@INT
Feature: Generate letter pop up should contain letter details

  Background:
    Given i have a valid "goods" licence
    And i have logged in to internal

  Scenario: Check generate letter pop up
    When I generate a letter
    Then The pop up should contain letter details