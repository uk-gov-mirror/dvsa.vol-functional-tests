@INT
@Admin-Payment-Processing
@regression
Feature: Admin paying fees

  Background:
    Given i have logged in to internal

  Scenario Outline: Process GB payment by cash and card
    Given i am on the payment processing page
    And i add a new "<fee-type>" fee
    When when i pay for the fee by "<payment-method>"
    Then the fee should be paid and no longer visible in the fees table

    Examples:
      | payment-method | fee-type      |
      | card           | NETA Bus Fine |
      | cash           | NI A&D        |
      | cheque         | SCOT Bus Fine |
      | postal         | SCOT Bus Fine |