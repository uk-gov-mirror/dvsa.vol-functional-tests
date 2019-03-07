@SS
@NI-SURRENDERS

  Feature: Surrender a Northern Ireland Licence

    Scenario:
      Given I have a "goods" "ni" licence
      And my application to surrender is under consideration
      When the caseworker approves the surrender
      Then the licence status should be "surrendered"
      And the surrender menu should be hidden in internal
      And the licence should not displayed in selfserve