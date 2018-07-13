@OLCS-19510-GB
Feature: Provide downloadable PDFs to signed-in Self Serve users(GB)
  Background:
#    Given I have logged into Selfserve GB

  Scenario Outline: Check presence of TM1 Form
    Given I have a valid "public" "<Flag>" licence
    And I am on add Transport Manager Page
    Then I open TM1 Form
    Examples:
      |   Flag   |
      |   GB     |
      |   NI     |
  Scenario: Check presence of Maintenance form
    Given I am on add Safety Inspectors Page
    Then I open Maintenance Form
