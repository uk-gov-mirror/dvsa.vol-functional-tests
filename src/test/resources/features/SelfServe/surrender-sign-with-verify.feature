@SS
@Surrender-Verify
Feature: Sign with surrender

  Background: 
    Given i have a valid "public" licence
    And i choose to surrender my licence
    And i am on the review contact details page
    
  Scenario: Sign with verify - check that surrender has been created
    When i sign with verify
    Then the post verify success page is displayed
    And the surrender status is "Surrender pending"

  Scenario: Print and sign - check that surrender has been created
    When the user chooses to print and sign
    Then the print and sign page is displayed
    And the surrender status is "Surrender pending"