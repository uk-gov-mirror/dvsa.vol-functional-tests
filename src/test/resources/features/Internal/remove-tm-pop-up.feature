@OLCS-19478
Feature: Remove last Transport Manager (TM) pop up

  Background:
    Given i have an application with a transport manager

#  Scenario: Pop up should be displayed when last TM is removed
#    Given the licence has been granted
#    When the last transport manager has been removed by an internal user
#    Then a pop up message should be displayed
#
#  Scenario: Pop up should display new warning message when a licence contains multiple TM's
#    Given i add a transport manager to an existing licence
#    And the licence has been granted
#    When a self-serve user removes the last TM
#    Then the remove last TM popup should not be displaying new TM remove text
#
#  Scenario: Pop up should display new warning message when a self-serve user removes a TM
#    Given the licence has been granted
#    When a self-serve user removes the last TM
#    Then the remove last TM popup should not be displaying new TM remove text
#
#  Scenario: Pop up should display new warning message when a TM is removed from an application
#    When the last transport manager has been removed by an internal user
#    Then the remove last TM popup should not be displaying new TM remove text

  Scenario: Pop up should not displayed when a licence contains multiple TM's
    Given the licence has been granted
    When i create a variation
    And the last transport manager has been removed by an internal user
#    Then the remove last TM popup should not be displaying new TM remove text

