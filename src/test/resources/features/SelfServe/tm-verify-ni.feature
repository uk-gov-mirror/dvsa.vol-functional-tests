@Business Need: As part of the OTC strategic objects for 2018
@SS-TM-Verify-NI
@SS

Feature: TM signs through verify

  Background:
    Given i have a valid "public" "NI" licence
    And the TM has successfully signed through verify

  @OLCS-20343
  Scenario: Operator applicant has confirmed TM details and submitted and now wishes to co-sign using Verify - TM has signed online already using Verify
    When the user confirms details on the TM 'Review and submit' page
    Then the correct information is displayed on operator-application declaration page
    And the users chooses to sign with verify
    Then the declaration text and verify button are displayed