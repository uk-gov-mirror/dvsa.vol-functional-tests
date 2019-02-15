@INT
@OLCS-22990

  Feature: View Surrender Menu and details on Internal
  Background:
    Given i have a valid "goods" licence
    And i have completed a surrender application with verify
    And i have logged in to internal
    And i search for my licence

    Scenario: Surrender should be displayed on Internal
      Then the internal surrender menu should be displayed
      When i click on the surrenders tab
#      Then any open cases should be displayed
#      And any open bus registrations should be displayed
      And the ECMS tick box should be displayed



