@OLCS-22913
@SS
@Surrender

Feature: Multiple licence holder

  Background:
    Given I have applied for "standard_national" "goods" licences

  Scenario: Surrender multiple licences
    And my application to surrender is under consideration
    When the caseworker approves the surrender
    Then the licence status should be "surrendered"
    And the surrender menu should be hidden in internal
    And the licence should not displayed in selfserve