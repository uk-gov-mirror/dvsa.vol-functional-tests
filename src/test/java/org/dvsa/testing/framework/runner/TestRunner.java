package org.dvsa.testing.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.junit.Cucumber;
import org.dvsa.testing.lib.browser.Browser;
import org.junit.After;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

@RunWith(Cucumber.class)
@CucumberOptions( plugin = {"pretty",
                            "html:target/cucumber",
                            "json:target/cucumber/json/cucumber.json",
                            "junit:target/cucumber/ci/ci.xml"},
                  features = "src/test/resources/features",
                  glue = "org.dvsa.testing.framework.stepdefs",
                  tags = {"not @wip or not @WIP"},
                  strict = true,
                  monochrome = true
)
public class TestRunner {
}