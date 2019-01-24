@INT
@GRANT-PSV-APP
Feature: Grant under consideration application

  Background:
    Given I have a "public" "standard_national" application which is under consideration

  Scenario: Grant a public standard national licence
    When I pay fees
    Then the licence should be granted