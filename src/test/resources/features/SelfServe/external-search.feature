@OLCS-20956
@SS
@SS-EXTERNAL-SEARCH
@Business Need: External users should be to search by Address, Business name, Licence Number and Person's name

Feature: External user search by Address, Business name, Licence Number and Person's name

  Background:
    Given i have a valid "goods" licence

  Scenario: [Positive]Search for lorry and bus operators by Address
    Given I am on the external search page
    When I search for a lorry and bus operator by "address"
    Then search results page addresses should only display address belonging to our post code

  Scenario: [Positive]Search for lorry and bus operators by Business name
    Given I am on the external search page
    When I search for a lorry and bus operator by "business"
    Then search results page should display operator names containing our business name

  Scenario: [Positive]Search for lorry and bus operators by Licence number
    Given I am on the external search page
    When I search for a lorry and bus operator by "licence"
    Then search results page should only display our licence number

  Scenario: [Positive]Search for lorry and bus operators by Person's name
    Given I am on the external search page
    When I search for a lorry and bus operator by "person"
    Then search results page should display names containing our operator name

  Scenario: [Negative]Search for lorry and bus operators by Address
    Given I am on the external search page
    When I search for a lorry and bus operator by "address"
    Then search results page should not display addresses which were not searched for

  Scenario: [Negative]Search for lorry and bus operators by Business name
    Given I am on the external search page
    When I search for a lorry and bus operator by "business"
    Then search results page should only display operator names containing our business name

  Scenario: [Negative]Search for lorry and bus operators by Licence number
    Given I am on the external search page
    When I search for a lorry and bus operator by "licence"
    Then search results page should not display any other licence number

  Scenario: [Negative]Search for lorry and bus operators by Person's name
    Given I am on the external search page
    When I search for a lorry and bus operator by "person"
    Then search results page should only display names containing our operator name