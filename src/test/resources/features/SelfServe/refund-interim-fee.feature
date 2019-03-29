@OLCS-23724
@SS

Feature: Fee paid on new application

  Scenario Outline: Interim fee has been paid and licence has been refused
    Given i have an interim "<OperatorType>" "<LicenceType>" application
    When the interim fee has been paid
    And the licence has been refused
    Then the interim fee should be refunded

    Examples:
      | OperatorType | LicenceType |
      | goods        | si          |
      | goods        | sn          |

  Scenario Outline: Interim fee has been paid and licence has been withdrawn
    Given i have an interim "<OperatorType>" "<LicenceType>" application
    When the interim fee has been paid
    And the licence has been withdrawn
    Then the interim fee should be refunded

    Examples:
      | OperatorType | LicenceType |
      | goods        | sn          |

  Scenario Outline: Interim fee has been paid and licence has been granted
    Given i have an interim "<OperatorType>" "<LicenceType>" application
    When the interim fee has been paid
    And the licence is granted
    Then the interim fee should be refunded

    Examples:
      | OperatorType | LicenceType |
      | goods        | sn          |

  Scenario Outline: Interim fee has been paid and licence has been granted
    Given i have an interim "<OperatorType>" "<LicenceType>" application
    When the interim fee has been paid
    And the interim is granted
    And the licence is granted
    Then the interim fee should not be refunded

    Examples:
      | OperatorType | LicenceType |
      | goods        | sn          |