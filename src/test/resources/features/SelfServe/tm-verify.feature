@Business Need: As part of the OTC strategic objects for 2018
@SS-TM-Verify
@SS

Feature: TM signs through verify

  Background:
    #Needs updating to use new application flow
    Given i have a valid "public" licence
    And the TM has successfully signed through verify

  Scenario: TM who is not Operator / Applicant signs through Verify
    When the user has been redirected to the awaiting confirmation page
    Then the 'Awaiting operator review' post signature page is displayed showing the correct information
    And the confirmation panel is displaying the correct assets

  Scenario Outline: TM has signed the application via GOV.UK Verify who is not the operator/applicant
    Given the operator has chosen to counter sign the application by print
    When the user is on the print sign page
    Then print details like will open in a new tab
    And the following "<attributes>" text will be displayed on the page

    Examples:
      | attributes                   |
      | Transport managers signature |
      | Signed by                    |
      | Date of birth                |
      | Signature date               |

  @OLCS-20343
  Scenario: Operator applicant has confirmed TM details and submitted and now wishes to co-sign using Verify - TM has signed online already using Verify
    When the user confirms details on the TM 'Review and submit' page
    Then the correct information is displayed on operator-application declaration page
    And the users chooses to sign with verify
    Then the declaration text and verify button are displayed

  @OLCS-20343
  Scenario: Operator applicant has confirmed TM details and submitted and now wishes to co-sign using Print - TM has signed online already using Verify
    When the user confirms details on the TM 'Review and submit' page
    Then the correct information is displayed on operator-application declaration page
    And the users chooses to sign with verify
    Then the declaration text and verify button are not displayed
