@OLCS-22443

  Feature: Surrendering a licence

    Background:
      Given i have a valid "public" "si" licence

      Scenario: internal user can create, update and delete a licence surrender
        Then as "internal" user I can surrender a licence
        And as "internal" user I can update surrender details
        And as "internal" user I cannot surrender a licence again
        And as internal user i can delete a surrender
