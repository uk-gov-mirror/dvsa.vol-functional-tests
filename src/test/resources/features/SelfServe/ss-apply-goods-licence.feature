@SS
@GRANT-GOODS-APP
Feature: Self Serve Apply for goods licence

  Background:
    Given i have a "goods" application

  Scenario: Pay application and grant fees on selfserve
    When i choose to print and sign
    Then the application should be submitted