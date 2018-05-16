@OLCS-19509
@SS
Feature: Self-serve ESBR upload short notice for English and Scottish transport areas

  Scenario Outline: Short notice ESBR in self-serve
    Given I have a psv application with traffic area "<TrafficArea>" and enforcement area "<EnforcementArea>" which has been granted
    When I upload an esbr file with "<Days>" days notice
    Then A short notice flag should be displayed in selfserve
    And  A short notice tab should be displayed in internal
    And Any registrations created in internal should display a short notice tab
    Examples:
      | TrafficArea | EnforcementArea | Days |
      | B           |                 | 41   |
      | M           |                 | 41   |
      | G           | EA-E            | 55   |
      | F           |                 | 41   |
      | H           |                 | 41   |
      | K           |                 | 41   |
  Scenario Outline: ESBR in self-serve
    Given I have a psv application with traffic area "<TrafficArea>" and enforcement area "<EnforcementArea>" which has been granted
    When I upload an esbr file with "<Days>" days notice
    Then A short notice flag should not be displayed in selfserve
    And  A short notice tab should not be displayed in internal
    And Any registrations created in internal should not display a short notice tab
    Examples:
      | TrafficArea | EnforcementArea | Days |
      | B           |                 | 42   |
      | M           |                 | 42   |
      | G           | EA-E            | 56   |
      | F           |                 | 42   |
      | H           |                 | 42   |
      | K           |                 | 42   |