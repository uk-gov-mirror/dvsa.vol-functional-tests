@INT
@GRANT-GOODS-APP
Feature: Grant an application

  Background:
    Given I have a "goods" application which is under consideration

  Scenario:
    When I pay fees
    Then the application should be granted