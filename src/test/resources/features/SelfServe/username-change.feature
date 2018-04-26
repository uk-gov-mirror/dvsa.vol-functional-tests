
Feature: user's name change

  Scenario: User changes their name
      Given I change user name
      When I make a change on my application
      Then the change should be logged against the new username







