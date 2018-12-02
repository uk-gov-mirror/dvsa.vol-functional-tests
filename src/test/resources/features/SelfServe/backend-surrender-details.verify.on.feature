@OLCS-22272

Feature: Retrieving deteails for a Surrendered licence

  Background:
    Given i have a valid "public" licence
    And surrenders has been switched "on"
    And verify has been switched "On"
    Then as "selfserve" user I can surrender a licence
    Then as "selfserve" user I can update surrender details


  Scenario: Retrieve details for surrendered licence
    Given I request the details for a surrender
    Then correct licence id should be returned
    And all surrender details should be returned
    And the value of the SystemParameter for disabled digital signatures should be "false"



  Scenario: Can not retrieve details for surrendered licence I do not own
    Given as a selfserve user i apply for a "goods" licence
    Then as "selfserve" user I can surrender a licence
    And another user is unable to get details of my surrendered licence


