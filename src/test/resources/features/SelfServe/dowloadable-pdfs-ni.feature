@OLCS-19510-NI
Feature: Provide downloadable PDFs to signed-in Self Serve users NI
#Background:
#  Given I have logged into Selfserve NI

  Scenario: Check presence of TM1 Form - NI
    Given I am on add Transport Manager Page -  NI
    Then I open TM1 Form - NI

  Scenario: Check presence of Maintenance form - NI
    Given I am on add Safety Inspectors Page - NI
    Then I open Maintenance Form - NI