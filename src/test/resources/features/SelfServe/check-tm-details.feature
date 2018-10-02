@SS-TM-Verify
@SS
@OLCS-21298

Feature: TM/operator checks optional wording has been removed for TM details page

  Background:
    Given I have a new application
    And the transport manager is the operator

  Scenario Outline: Radio button not clicked
    Then the optional wording should not be displayed in the "<section>" section
    And the "<button>" button should not be displayed

    Examples:
      | section              | button                        |
      | Other Licences       | Add other licences            |
      | Other Employments    | Add other employment          |
      | previous Convictions | Add convictions and penalties |
      | previous Licences    | Add licences                  |


  Scenario Outline: Radio button clicked
    When I click the yes radio button for the "<section>" section
    Then the "<button>" button should be displayed

    Examples:
      | section              | button                        |
      | Other Licences       | Add other licences            |
      | Other Employments    | Add other employment          |
      | previous Convictions | Add convictions and penalties |
      | previous Licences    | Add licences                  |

  Scenario Outline: Check section contains
    When I click on the "<button>" button
    Then I should be taken to the "<page>" page
    And page title "<page-title>" should be displayed on page

    Examples:
      | page                           | button                        | page-title              |
      | add-other-licence-applications | Add other licences            | Add other licence       |
      | add-employment                 | Add other employment          | Add employment          |
      | add-previous-conviction        | Add convictions and penalties | Add previous conviction |
      | add-previous-licence           | Add licences                  | Add previous licence    |

  Scenario: validation checks when no radio button has been selected
    When the users attempts to save without entering any data
    Then a validation message should be displayed


  Scenario: validation checks on guidance message
    When I click the no radio button for the "owner/director" question
    Then the guidance text should be displayed

