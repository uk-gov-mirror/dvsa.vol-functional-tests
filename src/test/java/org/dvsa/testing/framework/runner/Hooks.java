package org.dvsa.testing.framework.runner;

import cucumber.api.java.After;
import org.dvsa.testing.lib.browser.Browser;

public class Hooks {

    @After
    public void tearDown() {
        Browser.quit();
    }
}
