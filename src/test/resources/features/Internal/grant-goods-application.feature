Feature: Grant an application

  Background:
    Given I have an application which is under consideration

  Scenario:
    When I pay fees
    Then the application should be granted