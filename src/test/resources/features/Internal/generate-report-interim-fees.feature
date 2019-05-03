@OCLS-23771


Feature: Generate report for interim fees

  Background:
    Given i have an interim "goods" "si" application
    When the interim fee has been paid
    And the licence has been refused

  Scenario: Interim fee has been paid and licence has been refused
    Then the report should be generated
    And the refund should be displayed