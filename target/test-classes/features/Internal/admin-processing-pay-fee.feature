@INT
@Admin-Payment-Processing
Feature: Admin payment process

  Background:
    Given i have logged in to internal

  Scenario Outline: Process GB payment by cash and card
    Given i am on the payment processing page
    When when i pay for the fee by "<payment-method>"
    Then the fee should be paid and no longer visible in the fees table

    Examples:
      | payment-method |
      | card           |