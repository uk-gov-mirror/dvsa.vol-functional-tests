@Admin-Payment
Feature: Admin create fees

  Background:
    Given i have logged in to internal

  Scenario Outline: Internal Admin Create Processing Fees
    Given i am on the payment processing page
    When i add a new "<Fee>" fee
    Then the fee should be created

    Examples:
      | Fee           |
      | NETA Bus Fine |
      | NI A&D        |
      | SCOT Bus Fine |