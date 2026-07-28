@int_regression
@FullRegression

Feature: Case hearings and appeals - appeals, public inquiries, non-public inquiries, impoundings

  Background:
    Given i have a valid "goods" "standard_national" licence
    And I create a new case
    And i have logged in to internal as "admin"
    When i url search for my licence

  @Add_appeal_to_case
  Scenario: UI - Add an appeal to a case
    When I navigate to Hearings & appeals
    Then I add an appeal to the case
    Then the appeal should be created

  @Add_tc_stay_to_appeal
  Scenario Outline: UI - Add a Traffic Commissioner / Transport Regulator stay to an appeal - <outcome>, DVSA notified <dvsaNotified>, withdrawn <withdrawn>
    When I navigate to Hearings & appeals
    Then I add an appeal to the case
    Then I add a TC/Transport Regulator stay to the appeal with outcome "<outcome>", DVSA notified "<dvsaNotified>", withdrawn "<withdrawn>"
    Then the TC/Transport Regulator stay should be created

    Examples:
      | outcome | dvsaNotified | withdrawn |
      | GRANTED | Yes          | No        |
      | REFUSED | No           | No        |
      | GRANTED | Yes          | Yes       |

  @Add_ut_stay_to_appeal
  Scenario Outline: UI - Add an Upper Tribunal stay to an appeal - <outcome>, DVSA notified <dvsaNotified>, withdrawn <withdrawn>
    When I navigate to Hearings & appeals
    Then I add an appeal to the case
    Then I add an Upper Tribunal stay to the appeal with outcome "<outcome>", DVSA notified "<dvsaNotified>", withdrawn "<withdrawn>"
    Then the Upper Tribunal stay should be created

    Examples:
      | outcome | dvsaNotified | withdrawn |
      | GRANTED | Yes          | No        |
      | REFUSED | No           | No        |
      | REFUSED | Yes          | Yes       |

  @Add_public_inquiry_to_case
  Scenario: UI - Add a public inquiry to a case
    When I navigate to Public Inquiry
    Then I add a public inquiry to the case
    Then the public inquiry should be created

  @Add_hearing_to_public_inquiry
  Scenario: UI - Add a hearing to a public inquiry
    When I navigate to Public Inquiry
    Then I add a public inquiry to the case
    Then I add a hearing to the public inquiry
    Then the hearing should be added to the public inquiry

  @Add_decision_to_public_inquiry
  Scenario: UI - Add a decision to a public inquiry
    When I navigate to Public Inquiry
    Then I add a public inquiry to the case
    Then I add a hearing to the public inquiry
    Then I add a decision to the public inquiry
    Then the decision should be added to the public inquiry

  @Edit_service_level_agreement
  Scenario: UI - Edit the service level agreement of a public inquiry
    When I navigate to Public Inquiry
    Then I add a public inquiry to the case
    Then I edit the service level agreement
    Then the service level agreement should be updated

  @Add_sla_exception_to_public_inquiry
  Scenario: UI - Add an SLA exception to a public inquiry
    When I navigate to Public Inquiry
    Then I add a public inquiry to the case
    Then I add an SLA exception to the public inquiry
    Then the SLA exception should be added

  @Add_non_public_inquiry_to_case
  Scenario: UI - Add a non-public inquiry to a case
    When I navigate to Non-Public Inquiry
    Then I add a non-public inquiry to the case
    Then the non-public inquiry should be created

  @Add_impounding_to_case
  Scenario: UI - Add an impounding to a case
    When I navigate to Impoundings
    Then I add an impounding to the case
    Then the impounding should be created
