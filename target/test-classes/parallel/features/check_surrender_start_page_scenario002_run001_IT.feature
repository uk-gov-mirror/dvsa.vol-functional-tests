# language: en

Feature: Checking the start page for surrenders

Scenario: PSV and Goods licence

Given i have a valid "goods" licence
When i choose to surrender my licence
Then the correct page heading for "goods" should be displayed
And the correct instructions for "goods" should be displayed
And the correct licence number should be displayed

# Source feature: src/test/resources/features/SelfServe/check-surrender-start-page.feature
# Generated by Cucable
