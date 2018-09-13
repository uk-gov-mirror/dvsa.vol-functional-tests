@SS-TM-Verify
@SS

Feature: TM/operator checks optional wording has been removed for TM details page

  Background:
    Given I have a new application
    And the transport manager is the operator

#  Scenario Outline: Radio button not clicked
#    Then the optional wording should not be displayed in the "<section>" section
#    And the "<button>" button should not be displayed
#
#    Examples:
#      | section              | button                        |
#      | Other Licences       | Add other licences            |
#      | Other Employments    | Add other employment          |
#      | previous Convictions | Add convictions and penalties |
#      | previous Licences    | Add licences                  |
#
#
#  Scenario Outline: Radio button clicked
#    When I click the yes radio button for the "<section>" section
#    Then the "<button>" button should be displayed
#
#    Examples:
#      | section              | button                        |
#      | Other Licences       | Add other licences            |
#      | Other Employments    | Add other employment          |
#      | previous Convictions | Add convictions and penalties |
#      | previous Licences    | Add licences                  |

  Scenario Outline:
    When I click on the "<button>" button
    Then I should be taken to the "<page>" page


    Examples:
      | page                           | button                        |
      | add-other-licence-applications | Add other licences            |
      | add-employment                 | Add other employment          |
      | add-previous-conviction        | Add convictions and penalties |
      | add-previous-licence           | Add licences                  |


