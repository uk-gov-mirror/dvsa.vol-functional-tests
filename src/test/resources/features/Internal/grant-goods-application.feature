@INT
@GRANT-GOODS-APP
Feature: Grant under consideration application

  Background:
    Given I have a "goods" "standard_international" application which is under consideration

  Scenario: Grant a goods standard international licence
    When I pay fees
    Then the licence should be granted