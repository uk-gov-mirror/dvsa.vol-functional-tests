# language: en

@SS-Verify-On
@OLCS-20343
Feature: Countersigning declaration page for the operator (Verify switched on)

Scenario: Check that verify button is not displayed when Print and sign is selected as an option

Given verify has been switched "On"
When i have a "goods" "NI" partial application
And i add an operator as a transport manager
And the user chooses to print and sign
Then the declaration text and verify button are not displayed

# Source feature: src/test/resources/features/SelfServe/tm-verify-check-declarations-text.feature
# Generated by Cucable
