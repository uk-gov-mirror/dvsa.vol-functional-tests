package org.dvsa.testing.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"io.qameta.allure.cucumber2jvm.AllureCucumber2Jvm"},
        features = {"src/test/resources/features/SelfServe"},
        glue = {"org.dvsa.testing.framework.stepdefs"},
        format = {"json:cucumber.json"}
)

public class TestRunner {
}


