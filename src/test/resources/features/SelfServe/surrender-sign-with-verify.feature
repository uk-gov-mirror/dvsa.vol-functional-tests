@WIP
Feature: Sign with surrender

  Background: 
    Given i have a valid "public" licence
    And i surrender my licence
    
  Scenario: Sign with verify - check that surrender has been created
    When i sign with verify
    Then the post verify success page is displayed
    And the surrender status is "Surrender pending"

  Scenario: Print and sign - check that surrender has been created
    When the user chooses to print and sign
    Then the print and sign page is displayed
    And the surrender status is "Surrender pending"