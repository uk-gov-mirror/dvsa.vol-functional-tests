@INT
Feature: Admin payment process

  Background:
    Given i have logged in to internal

  Scenario: Process GB payments by cash
    Given i am on the payment processing page
    When i add a new fee
#    Then the fee should be created
#    And when i pay for the fee
#    Then the fee should be paid and no longer visible in the fees table


#  Scenario:Process GB payments by cheque
#    Given i am on the payment processing page
#    When i add a new fee
#    Then the fee should be created
#    And when i pay for the fee
#    Then the fee should be paid and no longer visible in the fees table
#
#  Scenario: Process GB payments by card
#    Given i am on the payment processing page
#    When i add a new fee
#    Then the fee should be created
#    And when i pay for the fee
#    Then the fee should be paid and no longer visible in the fees table
