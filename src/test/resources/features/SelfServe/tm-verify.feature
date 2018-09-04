@Business Need: As part of the OTC strategic objects for 2018
@SS

Feature: TM signs through verify

  Background:
    Given the self-service user has successfully signed the TM application through Verify

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


    Scenario: TM has completed journey and is checking Your details section
      Given the operator is ona check your answers page
      Then the correct headings, data and links should be displayed for Your details section

  Scenario: TM has completed journey and is checking Responsibilities section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Responsibilities section

  Scenario: TM has completed journey and is checking Hours per week section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Hours per week section

  Scenario: TM has completed journey and is checking Other licences   section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Other licences section

  Scenario: TM has completed journey and is checking Additional information section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Additional information section

  Scenario: TM has completed journey and is checking Other employment section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Other employment section

  Scenario: TM has completed journey and is checking Convictions & Penalties section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Convictions & Penalties section

  Scenario: TM has completed journey and is checking Revoked, curtailed or suspended licences section
    Given the operator is on check your answers page
    Then the correct headings, data and links should be displayed for Revoked, curtailed or suspended licences section



