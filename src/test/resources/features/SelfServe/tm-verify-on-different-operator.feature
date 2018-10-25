@SS-TM-Verify-On

Feature: TM signs through verify

  Background:
    Given verify has been switched "On"
    And I am the operator and not the transport manager
    And I have a "goods" "GB" partial application

  @OLCS-19792
  Scenario: TM who is not Operator / Applicant signs through Verify
    When i add an existing person as a transport manger who is not the operator
    And i choose to sign with verify with "pavlov"
    Then the 'Awaiting operator review' post signature page is displayed