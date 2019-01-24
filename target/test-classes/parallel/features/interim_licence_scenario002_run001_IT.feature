# language: en

@OLCS-13203
@INT
@INTERIM
Feature: Change Validation On Interim Vehicle Authority

Scenario: Interim Vehicle Authority Equal to Application Vehicle Authority

Given i have a valid "goods" licence
And i have logged in to internal
And i search for my licence
When I have an interim vehicle authority equal to my application vehicle authority
Then I should be able to save the application without any errors

# Source feature: src/test/resources/features/Internal/interim-licence.feature
# Generated by Cucable
