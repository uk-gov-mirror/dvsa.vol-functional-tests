package org.dvsa.testing.framework.stepdefs;

import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.internal.SystemParameters;
import org.dvsa.testing.lib.pages.enums.dataretention.SystemParameter;
import org.dvsa.testing.lib.uri.Environment;
import org.dvsa.testing.lib.uri.URI;
import org.dvsa.testing.lib.uri.utils.ApplicationType;

public class Common implements En{

    public Common() {

        And("^data retention records has been enabled$", () -> {
            SystemParameters.enable(SystemParameter.DATA_RETENTION_RECORDS);
        });

        Given("^I am logged in on internal$", () -> {
            String username = Properties.get("user", true);
            String password = Properties.get("password", true);

            Login.signIn(username, password);
        });

        Given("^I am on the vol internal app page$", () -> {
            Browser.open(URI.build(ApplicationType.INTERNAL, Environment.enumType(Properties.get("env", true))));
            Browser.deleteCookies();
            Browser.refresh();
        });

    }

}
