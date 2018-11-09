@SS-Verify-On
@OLCS-19792
Feature: TM signs through verify

  Background:
    Given verify has been switched "On"
    And I am the operator and not the transport manager
    And I have a "goods" "GB" partial application

  Scenario: TM who is not Operator / Applicant signs through Verify
    When i add an existing person as a transport manager who is not the operator
    And i choose to sign with verify with "pavlov"
    Then the 'Awaiting operator review' post signature page is displayed

  Scenario: TM who is Operator signs through Verify
    When i add an operator as a transport manager
    And i choose to sign with verify with "pavlov"
    Then the 'Review and declarations' post signature page is displayed

  Scenario: Operator co-signs through Verify
    When i add an external person as a transport manager
    And i choose to sign with verify with "pavlov"
    And the operator countersigns digitally
    Then the 'Review and declarations' post signature page is displayed