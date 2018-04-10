@OLCS-18578

Feature: Change validation on interim directions to allow 0 vehicles

  Background:
    Given I have logged in to internal
    And I have an internal application with a variation

  Scenario: Date not required when creating an interim application
    When  I create an interim application with zero vehicle authority
    And  trailer authority greater than one
    Then  I should not error when i save the application