@OLCS-19792
@Business Need: As part of the OTC strategic objects for 2018
@SS

Feature: TM signs through verify

  Scenario: TM who is not Operator / Applicant signs through Verify

    Given the self-service user has successfully signed the TM application through Verify
    Then the 'Awaiting operator review' post signature page is displayed showing the correct information
    And the confirmation panel is displaying the correct assets