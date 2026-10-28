@int_regression @undertakings-and-conditions
Feature: Undertakings and Conditions on Internal

  @undertakings-goods-licence
  Scenario: Undertakings page renders for goods licence
    Given I have a "goods" "standard_national" application which is under consideration
    And i create an admin and url search for my application
    When I navigate to the undertakings and conditions page on internal
    Then the undertakings and conditions page should display on internal
    And the undertakings table should display

  @undertakings-psv-licence
  Scenario: Undertakings page renders for PSV licence
    Given I have a "public" "standard_national" application which is under consideration
    And i create an admin and url search for my application
    When I navigate to the undertakings and conditions page on internal
    Then the undertakings and conditions page should display on internal
    And the undertakings table should display

  @conditions-shown-after-grant
  Scenario: Conditions shown after licence grant
    Given i have a valid "goods" "standard_national" licence
    And i create an admin and url search for my licence
    When I navigate to the undertakings and conditions page on internal
    Then the undertakings and conditions page should display on internal
    And the conditions table should display
