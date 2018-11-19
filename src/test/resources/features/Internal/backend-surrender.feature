@OLCS-22443

  Feature: Surrendering a licence

    Background:
      Given i have a valid "public" licence

      Scenario: internal user can create, update and delete a licence surrender
        Then as an internal user i can surrender a licence
        And as an internal user i can update a surrender
        And as an internal user i can delete a surrender
