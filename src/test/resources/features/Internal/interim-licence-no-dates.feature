@OLCS-17639
Feature: Change Date Validation On Interim Applications

  Background:
    Given I have logged in to internal
    And I have an internal application

  Scenario: Date not required when creating an interim application
    When  I create an interim application with no start and end dates
    Then  I should not error when i save the application

  Scenario: Date required when granting an interim application
    When I create an interim application with no start and end dates
    Then I should not error when i attempt to grant the application
