@SS-TM-Verify
@SS

Feature: TM signs through verify

  Background:
    Given verify has been switched "On"
    And I am the operator and not the transport manager
    And I have a "goods" "GB" partial application

  @OLCS-19792
  Scenario: TM who is not Operator / Applicant signs through Verify
    When i add an existing person as a transport manger who is not the operator
    And i choose to sign with verify with "pavlov"
#    Then the 'Awaiting operator review' post signature page is displayed
#    When the user has been redirected to the awaiting confirmation page
#    Then the 'Awaiting operator review' post signature page is displayed showing the correct information
#    And the confirmation panel is displaying the correct assets

#  @OLCS-20350
#  Scenario Outline: TM has signed the application via GOV.UK Verify who is not the operator/applicant
#    Given the operator has chosen to counter sign the application by print
#    When the user is on the print sign page
#    Then print details like will open in a new tab
#    And the following "<attributes>" text will be displayed on the page
#
#    Examples:
#      | attributes                   |
#      | Transport managers signature |
#      | Signed by                    |
#      | Date of birth                |
#      | Signature date               |