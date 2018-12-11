@user
Feature: Set up users for accessibility testing

  Scenario Outline: PSV account with 16 different licences and 3 vehicles
    Given I have applied for "<LicenceType>" "<OperatorType>" licences
    When i have logged in to self serve
    Then the licence should be created and granted

    Examples:
      | LicenceType            | OperatorType |
      | standard_national      | goods        |
      | standard_international | public       |

#  Scenario Outline: PSV account with 16 different licences with external tm
#    Given I have applied for "<LicenceType>" "<OperatorType>" application
#    When i have logged in to self serve
  # need to add then for asserting that licences have created
#  Examples:
#  | LicenceType            | OperatorType |
#  | standard_national      | goods        |
#  | standard_international | public       |