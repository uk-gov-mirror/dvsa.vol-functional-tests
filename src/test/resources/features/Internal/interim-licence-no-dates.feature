@OLCS-17639
Feature: Change Date Validation On Interim Applications

  Background:
    Given i have a valid "public" licence
    And i have logged in to internal

  Scenario: Date not required when creating an interim application
    When  I create an interim application with no start and end dates
    Then  I should not error when i save the application

  Scenario: Date required when granting an interim application
    When I create an interim application with no start and end dates
    Then I should not error when i attempt to grant the application