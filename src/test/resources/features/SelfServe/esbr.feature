@OLCS-19509
Feature: Self-serve ESBR upload short notice for English and Scottish transport areas

  Scenario Outline: Short notice ESBR in selfserve
    Given I have a psv application with traffic area "<TrafficArea>" which has been granted
    When I upload a short notice esbr file
    Then A short notice flag should be displayed in selfserve
    And  A short notice tab should be displayed in internal
    And Any registrations created in internal should display a short notice tab
    Examples:
      | TrafficArea |
      | B           |
      | C           |
      | D           |
      | M           |

  Scenario Outline: ESBR in selfserve
    Given I have a psv application with traffic area "<TrafficArea>" which has been granted
    When I upload an esbr file
    Then A short notice flag should not be displayed in selfserve
    And  A short notice tab should not be displayed in internal
    And Any registrations created in internal should not display a short notice tab
    Examples:
      | TrafficArea |
      | B           |
      | C           |
      | D           |
      | M           |
      | G           |
      | F           |
      | H           |
      | K           |