Feature: esbr upload

  Scenario Outline: Short notice ESBR in selfserve
    Given I have a psv application with traffic area "<TrafficArea>" which has been granted
    When I upload a short notice esbr file
    Then A short notice flag should be displayed in selfserve
    And  A short notice tab should be displayed in internal

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
    And  A short notice tab should be displayed in internal
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