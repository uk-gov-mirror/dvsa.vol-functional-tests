Feature: Removing the Last Director

    Scenario: remove last director from licence
      Given i have a valid "goods" licence
      And i remove a the last director
      Then a task is created in internal
