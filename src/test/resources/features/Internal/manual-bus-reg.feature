@INT
@MANUAL-BUS-REG
Feature: Complete Manual Bus Registration Steps

  Background:
    Given i have a valid "psv" licence

  Scenario: Paying Fees to Complete Bus Registration Manually
    When when i pay for the fee by "card"
    Then the fee should be paid and no longer visible in the fees table




