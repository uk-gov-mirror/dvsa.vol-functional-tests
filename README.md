# Functional Tests
This project holds functional tests for the VOL website. These end to end tests are facilitated through the use of WebDriver, cucumber and internal vol libraries.

## Prerequisite 
The following technologies should be installed on your system.
* Java JDK 17
* Maven 3.

## Technologies
* Java
* WebDriver
* Cucumber
* Maven


## Executing

``mvn clean test -Denv= -Dbrowser= -Dcucumber.filter.tags="@xx" ``

### Local execution through the jumpbox proxy

Start `aws-helper/jumpbox-proxy` with valid MFA credentials before launching tests.
The Cucumber startup hook automatically configures the proxy at `127.0.0.1:3128`
for `dev`, `qa`, `int` and `reg`, including direct Cucumber runs from IntelliJ.
No additional proxy VM options are needed. For example, use
`-Denv=qa -Dbrowser=chrome -Dheadless=false` and set the environment variable
`AWS_PROFILE=aws-helper` for AWS access.

Set `-Djumpbox.proxy=off` to disable automatic proxy configuration. Explicit
`http.proxyHost`, `https.proxyHost`, `httpProxy` or `httpsProxy` properties also
prevent automatic configuration.

## Reports
To produce the reports run the following command in your terminal
``mvn allure:report``

## Dependency Vulnerability Scanner
To scan use the following command ``mvn verify -DskipTests``

### Certificate issues

If maven report issues with the nvd.nist.gov certificate you can follow the steps below to add it

*  Follow the steps here https://github.com/jeremylong/InstallCert using the domain `nvd.nist.gov`. This will download and add the cert to your system keystore.
*  Add the certificate to the java keystore using one of the following commands: 
 
`JDK installed: keytool -importcert -file <path of extracted cert> -alias nvd-nist-gov -keystore $(/usr/libexec/java_home)/jre/lib/security/cacerts`

`JDK not installed: keytool -importcert -file <path of extracted cert> -alias nvd-nist-gov -keystore $(/usr/libexec/java_home)/lib/security/cacerts`

## Refactoring todos

- Upgrade to Java 17
- Refactor Java 11 Methods to Java 17

##EUPA Refactor
- Add documentation on the changes and how to modify things
