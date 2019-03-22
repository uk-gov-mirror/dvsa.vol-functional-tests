@SS
@GRANT-APP
Feature: Application Print and Sign

  Scenario Outline: Pay application and grant fees on selfserve
    Given i have a "<operatorType>" "<licenceType>" application
    When i choose to print and sign
    Then the application should be submitted

    Examples:
      | operatorType | licenceType       |
      | goods        | standard_national |
      | public       | standard_national |