# language: en

@SS
@External-Search-Filter
Feature: Using filters on External Search

Scenario: Check lorry and bus operator Goods or PSV filter

Given i have a valid "goods" licence
And i have searched for a licence
Then the Goods or PSV filter should be displayed

# Source feature: src/test/resources/features/SelfServe/external-search-filter.feature
# Generated by Cucable
