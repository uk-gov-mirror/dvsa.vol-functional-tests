@OLCS-21213
@SS-VERIFY

Feature: Operator signs with verify

  Scenario Outline: Operator chooses to sign with verify latin and non latin characters
    Given i have an application in progress
    When i choose to sign with verify with "<user>"
    Then the application should be signed with verify

    Examples:
    |user|
    |pavlov|
    |georgios|