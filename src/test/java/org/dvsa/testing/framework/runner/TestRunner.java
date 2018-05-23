package org.dvsa.testing.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"org.dvsa.testing.framework.runner.Hooks"},

        features = {"src/test/resources/features/internal"},
        glue = {"org.dvsa.testing.framework.stepdefs"}
)

public class TestRunner {

}

