package org.dvsa.testing.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.dvsa.testing.lib.browser.Browser;
import org.junit.After;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"},
        features = {"src/test/resources/features/email-inform-message.feature"},
        glue = {"org.dvsa.testing.framework.stepdefs"}
)
public class TestRunner {
    @After
    public void tearDown() {
        Browser.quit();
    }
}

