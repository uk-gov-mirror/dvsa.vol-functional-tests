@OLCS-22275
@SS

Feature: User should be able to continue where they left off

  Background:
    Given i have a valid "public" licence
    And i have started a surrender

#  Scenario: Update correspondence address
#    Given i update my address details on my licence
#    Then continue with application link is displayed
#    And user is taken to information change page on clicking continue application
#    And the new correspondence details are displayed on correspondence page

  Scenario: Add disc licence to licence
    Given i add a disc to my licence
    Then continue with application link is displayed
    And user is taken to information change page on clicking continue application

#  Scenario: Remove disc on licence
#    Given i add a disc to my licence
#    Then continue with application link is displayed
#    And user is taken to information change page on clicking continue application