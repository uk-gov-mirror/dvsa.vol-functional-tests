@INT
@GRANT-PSV-APP
Feature: Grant an application

  Background:
    Given I have a "public" application which is under consideration

  Scenario:
    When I pay fees
    Then the application should be granted