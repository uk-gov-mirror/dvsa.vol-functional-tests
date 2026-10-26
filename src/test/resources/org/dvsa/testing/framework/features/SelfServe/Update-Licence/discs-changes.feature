@discs-changes @ss_regression
Feature: Licence discs management

  Background:
    Given i have a valid "public" "standard_international" licence

  @verify-discs-present
  Scenario: Verify PSV licence has discs on creation
    When i navigate to the discs page
    Then the discs page should display
    And the discs page should display at least 1 discs

  @remove-disc
  Scenario: Remove a disc from PSV licence
    When i navigate to the discs page
    And the discs page should display at least 2 discs
    When i remove the first disc
    Then the discs page should display

  @replace-disc
  Scenario: Replace a disc on PSV licence
    When i navigate to the discs page
    And the discs page should display at least 1 discs
    When i replace the first disc
    Then the discs page should display
