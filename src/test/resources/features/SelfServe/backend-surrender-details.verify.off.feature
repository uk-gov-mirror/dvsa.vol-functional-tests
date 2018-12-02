@OLCS-22272

Feature: Retrieving details for a Surrendered licence Verify off

  Background:
    Given i have a valid "public" licence
    And surrenders has been switched "on"
    Then as "selfserve" user I can surrender a licence
    Given verify has been switched "Off"


  Scenario: Retrieve details for surrendered licence
    Given I request the details for a surrender
    Then correct licence id should be returned
    And all surrender details should be returned
    And the value of the SystemParameter for disabled digital signatures should be "true"



