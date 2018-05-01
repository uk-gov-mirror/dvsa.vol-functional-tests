Feature: Grant an application

  Background:
    Given I have a psv application which is under consideration

  Scenario:
    When I pay my application fees
    Then my application should be granted