Feature: Data Retention Populate

  Background:
    Given I am on the vol internal app page
    When I am logged in on internal
    And data retention records has been enabled

  Scenario Outline: Populate
    Given the rule <rule> has been enabled and set to <actionType>
    When the data retention job for populate has been ran
    Then I expect records that met the criteria for <rule> of action type <actionType> to be present in the populate

    Examples:
      |rule                                               | actionType|
      |IRFO Operator Expired                              | review    |
      |IRFO Operator Expired                              | automate  |
      |IRFO GV Permit Expired                             | review    |
      |IRFO GV Permit Expired                             | automate  |
      |IRFO PSV Authorisation Expired                     | review    |
      |IRFO PSV Authorisation Expired                     | automate  |
      |IRFO GV Permit Withdrawn Pending Or Refused Expired| review    |
      |IRFO GV Permit Withdrawn Pending Or Refused Expired| automate  |
      |Licence Not Yet Submitted                          | review    |
      |Licence Not Yet Submitted                          | automate  |
      |Applications Refused                               | review    |
      |Applications Refused                               | automate  |

  Scenario Outline: Review records match values in the corresponding database
    Given the rule <rule> has been enabled and set to <actionType>
    When the data retention job for populate has been ran
    And I review the populated records for <rule>
    Then I expect to see the records shown in review records in the data_retention table

    Examples:
      |rule                                               | actionType|
      |IRFO Operator Expired                              | review    |
      |IRFO Operator Expired                              | automate  |
      |IRFO GV Permit Expired                             | review    |
      |IRFO GV Permit Expired                             | automate  |
      |IRFO PSV Authorisation Expired                     | review    |
      |IRFO PSV Authorisation Expired                     | automate  |
      |IRFO GV Permit Withdrawn Pending Or Refused Expired| review    |
      |IRFO GV Permit Withdrawn Pending Or Refused Expired| automate  |
      |Licence Not Yet Submitted                          | review    |
      |Licence Not Yet Submitted                          | automate  |
      |Applications Refused                               | review    |
      |Applications Refused                               | automate  |

  Scenario Outline: Delete
    Given the rule <rule> has been enabled and set to automate
    When the data retention job for populate has been ran
    And the data retention job for delete has been ran
    Then the records that where populated should be deleted from the database

    Examples:
      |rule                                               |
      |IRFO Operator Expired                              |
      |IRFO GV Permit Expired                             |
      |IRFO PSV Authorisation Expired                     |
      |IRFO GV Permit Withdrawn Pending Or Refused Expired|
      |IRFO PSV Auth Withdrawn,Pending,Refused Etc Expired|