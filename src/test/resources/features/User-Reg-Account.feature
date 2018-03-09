Feature: User account registration

  Scenario: User notified of temp password email

    Given  I am on the VOL External registration
    When  I successfully register an account
    Then I should be notified to check my email for temp password
    And I should see help text for signing in problems