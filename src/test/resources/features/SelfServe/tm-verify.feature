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


    Scenario Outline: TM has signed the application via GOV.UK Verify who is not the operator/applicant
      Given the operator is on check your answers page
      Then the following "<sections>" text will be displayed on the page
      And the correct data should be pulled through for "<sections>"


      Examples:
        | sections                                  |
        | Your details                              |
        | Responsibilities                          |
        | Other licences                            |
        | Additional information                    |
        | Other employment                          |
        | Convictions & Penalities                  |
        | Revoked, curtailed or suspended licences  |


