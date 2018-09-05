@OLCS-21390
Business Need: A TM should no longer be able to access a download link to the TM1 form from VOL, or expect to have a form sent to them via email from VOL.

Feature: Update text to remove any reference to the form

  Background:
    Given i have a valid "goods" licence
    And i have logged in to self serve

  Scenario: Add a new TM to an existing application
    When i add a new transport manager
    Then a transport manager has been created banner is displayed
