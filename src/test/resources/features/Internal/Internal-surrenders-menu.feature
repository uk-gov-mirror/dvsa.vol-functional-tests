@INT
@OLCS-22990

  Feature: View Surrender Menu and details on Internal
  Background:
    Given i have surrendered a valid "goods" "SN" licence
    And i have found my licence on internal


    Scenario: Surrender should be displayed on Internal
      Then the internal surrender menu should be displayed
      And any open cases should be displayed
#      And any open bus registrations should be displayed
      And the ECMS tick box should be displayed



