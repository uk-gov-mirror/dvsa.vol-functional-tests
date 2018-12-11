Feature: Set up users for accessibility testing

  Scenario Outline: PSV account with 8 different licences and 3 vehicles
    Given I have applied for "<LicenceType>" "<OperatorType>" licences
    And the licence has been submitted and granted
    When i have logged in to self serve

    Examples:
      | LicenceType            | OperatorType |
      | standard_national      | public       |
      | standard_international | public       |

  Scenario: PSV account with 8 different licences with external tm
    Given I have applied for "standard_national" "goods" licences
    And i add an external TM
    When i have logged in to self serve