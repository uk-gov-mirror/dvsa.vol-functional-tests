@INT
@GRANT-PSV-APP
Feature: Grant an application

  Background:
    Given I have a "public" "standard_national" application which is under consideration

  Scenario:
    When I pay fees
    Then the licence should be granted