package org.dvsa.testing.framework.hook;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.dvsa.testing.framework.util.Util;
import org.dvsa.testing.lib.browser.Browser;

public class Hooks {

    @After()
    public void after(Scenario scenario) {
        Util.embedScreenShot(scenario);
        Browser.deleteCookies();
        Browser.refresh();
    }

}
