@OLCS-18578
Feature: Change validation on interim directions so can have 0 vehicles

  Background:
    Given I have logged in to selfserve
    And I have an internal license

  Scenario:
    Given I have an interim application
    When  I have zero vehicle authority on a goods variation application
    Then  I save the application with no error