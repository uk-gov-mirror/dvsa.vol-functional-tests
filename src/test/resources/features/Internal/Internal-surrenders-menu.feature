@INT
@OLCS-22990

  Feature: View Surrender Menu and details on Internal
  Background:
    Given i have a valid "goods" "sn" licence
    And i choose to surrender my licence
    When a caseworker views the surrender


    Scenario: Surrender Menu should be displayed on Internal
      Then the internal surrender menu should be displayed
      And any open cases should be displayed
      And any open bus registrations should be displayed
      And the ECMS tick box should be displayed



