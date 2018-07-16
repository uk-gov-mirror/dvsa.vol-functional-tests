@INT
@INT-NEW-ADMIN-USER
Feature: Create a new admin user

  Scenario: Creating a internal system admin user
    Given I create a new internal admin user
    When I navigate to the internal site
    Then I should be able to login into my new account