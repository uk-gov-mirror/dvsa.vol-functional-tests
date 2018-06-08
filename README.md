# Functional Tests
This project holds functional tests for the VOL website. These end to end tests are facilitated through the use of WebDriver, cucumber and internal vol libraries.

## Prerequisite 
The following technologies should be installed on your system.
* Java JDK 8
* Maven 3

## Technologies
* Java
* WebDriver
* Cucumber
* Maven

## Reports
To produce the reports run the following command in your terminal
``mvn allure:report``
## Executing
``mvn clean test -Denv= -Dbrowser= -DlicenceType= -DbusinessType= -Dni= -DJENKINS_USERNAME= -DJENKINS_PASSWORD= -DdbUsername=
  -DdbPassword=
``
