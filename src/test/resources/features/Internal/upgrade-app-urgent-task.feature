@OLCS-23684
@INT
@UPGRADE-APP
  Feature: Upgrade application and check for urgent task

    Scenario:
      Given i have a valid "goods" "sn" licence
      And i choose to upgrade my licence
      Then an urgent task is created in internal
