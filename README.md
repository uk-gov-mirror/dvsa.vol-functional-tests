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


## Executing
``mvn clean verify -Denv= -Dbrowser= -DlicenceType= -DbusinessType= -Dni=(Defaults to GB) -DJENKINS_USERNAME= -DJENKINS_PASSWORD= -DdbUsername=
  -DdbPassword=``
  
The environment and browser properties need to be set otherwise the test will not execute. If only environment and browser properties are set, the harness will
create by default ``a GB goods standard national limited company licence``

``@PSV-LAST-TM-TRIGGER`` test requires access to the Jenkins api and database, so for this test the following properties 
``-DJENKINS_USERNAME= -DJENKINS_PASSWORD= -DdbUsername= -DdbPassword=`` need to be set

## Executing with tags
``mvn clean verify {system props e.g. -Denv -Dbrowser} -Dcucumber.options="--tags @tag``

## Reports
To produce the reports run the following command in your terminal
``mvn allure:report``