@OLCS 19509
Feature: ESBR Short Notice
#
#Background:
#  Given I have an application for traffic area "<trafficArea>"


  Scenario:
#    Given I have an application for traffic area "<trafficArea>"
#    And  I submit a bus registration with short notice
#    Then The short notice text is displayed
##
##    Examples:
#    |trafficArea|
#    |B|
#    |C|
#    |D|
#    |M|


#
#  Scenario :
#    Given I have an application for traffic area "<trafficArea>"
    And I submit a bus registration without short notice
    Then The short notice text isn't displayed
##
##
#    Examples:
#      |trafficArea|
#      |B|
#      |C|
#      |D|
#      |M|
#      |G|
#      |F|
#      |H|
#      |K|
#
#
#
#
#
#
#
