@SS
@GRANT-PSV-APP
Feature: Self Serve Apply for psv licence

  Background:
    Given i have a "public" application

  Scenario: Pay application and grant fees on selfserve
    When i choose to print and sign
    Then the application should be submitted