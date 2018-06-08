@INT
Feature: Northern Ireland Goods Vehicles Data Export
  Scenario: All goods vehicles from Northern Ireland are exported to CSV
    Given Northern Ireland goods vehicles data has been exported
    When I download the generated CSV file for Northern Ireland
    And check the contents of the CSV file
    Then I expect to only see Northern Ireland goods vehicle data
