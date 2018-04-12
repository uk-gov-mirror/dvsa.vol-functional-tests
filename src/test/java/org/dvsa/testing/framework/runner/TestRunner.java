package org.dvsa.testing.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.dvsa.testing.lib.browser.Browser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"},

        features = {"src/test/resources/features/internal"},
        glue = {"org.dvsa.testing.framework.stepdefs"}
)
public class TestRunner {

    @AfterClass
    public void tearDown() {
        Browser.quit();
    }
}
