package org.dvsa.testing.framework.runner;

import cucumber.api.java.After;
import org.dvsa.testing.lib.browser.Browser;
import org.junit.AfterClass;

public class Hooks {

    @AfterClass
    @After
    public void afterScenario() {
        Browser.quit();
    }
}
