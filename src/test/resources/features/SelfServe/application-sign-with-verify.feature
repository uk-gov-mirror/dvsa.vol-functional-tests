@OLCS-7284
@SS-Verify-On

Feature: Operator signs with verify

  Scenario Outline: Operator chooses to sign with verify latin and non latin characters
    Given verify has been switched "on"
    And i have an application in progress
    When i choose to sign with verify with "<user>"
    Then the application should be signed with verify

    Examples:
      | user   |
      | pavlov |