@INT
@MANUAL-BUS-REG
Feature: Complete Manual Bus Registration Steps

  Background:
    Given I have a psv application with traffic area "B" and enforcement area "EA-B" which has been granted
    And I upload an esbr file with "42" days notice
    Then A short notice flag should not be displayed in selfserve
    And A short notice tab should not be displayed in internal

  Scenario: Paying Fees to Complete Bus Registration Manually
    Given i add a new bus registration
    And it has been paid and granted


#    When when i pay for the fee by "card"
#    Then the fee should be paid and no longer visible in the fees table




