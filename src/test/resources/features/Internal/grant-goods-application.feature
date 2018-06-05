@INT
@GRANT-GOODS-APP
Feature: Grant an application

  Background:
    Given I have a "goods" "standard_international" application which is under consideration

  Scenario:
    When I pay fees
    Then the licence should be granted