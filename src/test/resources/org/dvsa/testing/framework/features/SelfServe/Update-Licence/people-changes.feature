@people-changes
Feature: People changes (partners, LLP members)

  Background:
    Given i have a valid "goods" "standard_national" LLP licence

  @add-partner @ss_regression
  Scenario: Add a non-director partner to an LLP licence
    When i navigate to the people page
    And i add a partner named "John Smith" with date of birth "15 3 1975"
    Then the partner named "John Smith" should be present in the table

  @remove-partner
  Scenario: Remove a partner from an LLP licence
    When i navigate to the people page
    And i add a partner named "Jane Doe" with date of birth "22 8 1980"
    And the partner named "Jane Doe" should be present in the table
    When i remove the partner named "Jane Doe"
    Then the partner named "Jane Doe" should not be present in the table

  @add-multiple-partners
  Scenario: Add multiple partners to an LLP licence
    When i navigate to the people page
    And i add a partner named "John Smith" with date of birth "15 3 1975"
    And the partner named "John Smith" should be present in the table
    When i add a partner named "Sarah Johnson" with date of birth "10 5 1985"
    Then the partner named "Sarah Johnson" should be present in the table
