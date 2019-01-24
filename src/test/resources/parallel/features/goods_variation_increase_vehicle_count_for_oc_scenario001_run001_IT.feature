# language: en

@SS
@SS-GOODS-VAR-OC-INCREASE-VEHICLE
@OLCS-21133
Feature: Goods Variation increase vehicle count for an OC

Scenario: Create a variation and increase vehicle count

Given i have a valid "goods" licence
When A selfserve user increases the vehicle authority count
Then a status of update required should be shown next to financial evidence

# Source feature: src/test/resources/features/SelfServe/goods-variation-increase-vehicle-count-for-oc.feature
# Generated by Cucable
