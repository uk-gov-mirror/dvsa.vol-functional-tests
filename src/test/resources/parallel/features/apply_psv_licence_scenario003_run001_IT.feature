# language: en

@INT
@APPLY-PSV-LICENCE
Feature: Apply for a psv licence

Scenario: Apply for a standard international licence

Given I have applied for a "public" "standard_international" licence
When I grant licence
Then the licence should be granted

# Source feature: src/test/resources/features/Internal/apply-psv-licence.feature
# Generated by Cucable
