@SS
@ss-pay-app
Feature: Self Serve Apply for goods licence

  Scenario Outline: Create and pay application fees
    Given i have a "<operatorType>" "<licenceType>" application
    And i choose to print and sign
    When i pay for my application
    Then the application should be submitted

    Examples:
      | operatorType | licenceType            |
      | goods        | standard_international |
      | public       | standard_national      |

