@SS
@GRANT-GOODS-APP
Feature: Self Serve Apply for goods licence

  Background:
    Given I have a "goods" application which is under consideration

  Scenario: Pay application and grant fees
    When I pay fees on self serve
    And an internal user has granted my application
    Then my licence should valid