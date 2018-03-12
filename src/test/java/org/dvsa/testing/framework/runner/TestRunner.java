package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( plugin = {"pretty",
                            "html:target/cucumber",
                            "json:target/cucumber/json/cucumber.json",
                            "junit:target/cucumber/ci/ci.xml"},
                  features =  "classpath:features",
                  glue = "stepdefs",
                  tags = {"not @wip or not @WIP"},
                  strict = true,
                  monochrome = true
)
public class TestRunner {
}