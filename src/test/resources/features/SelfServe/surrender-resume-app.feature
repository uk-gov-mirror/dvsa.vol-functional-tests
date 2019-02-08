@OLCS-22275
@SS

Feature: User should be able to continue where they left off

  Background:
    Given i have a valid "public" licence
    And i have started a surrender

  Scenario: Update correspondence address
    Given i update my address details on my licence
    Then continue with application link is displayed
    And user is taken to information change page on clicking continue application
    And the new correspondence details are displayed on correspondence page

  Scenario: Remove disc from licence
    Given i remove a disc to my licence
    Then continue with application link is displayed
    And user is taken to information change page on clicking continue application

  Scenario: Return back to correspondence page
    And i am on the surrenders review contact details page
    When i leave the surrenders journey
    Then continue with application link is displayed
    And user is taken to review contact page on clicking continue application

  Scenario: Return back to current discs page
    And i am on the surrenders current discs page
    When i leave the surrenders journey
    Then continue with application link is displayed
    And user is taken to the surrenders current discs on clicking continue application

  Scenario: Return back to operator licence page
    And i am on the operator licence page
    When i leave the surrenders journey
    Then continue with application link is displayed
    And user is taken to the operator licence page on clicking continue application