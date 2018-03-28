package org.dvsa.testing.framework.stepdefs.Utils;

import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;

public class LoginInternalUser implements En {

    public static String USER_EMAIL = "usr291";
    public static String USER_PASSWORD = "Password1";

    public LoginInternalUser() {

        Given("^I have logged in to internal$", () -> {
            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = URI.build(ApplicationType.INTERNAL, env);

            Browser.go(URL);

            Login.signIn(USER_EMAIL, USER_PASSWORD);
        });

        And("^I have an internal application$", () -> {
            SearchNavBar.search("h");
            SearchNavBar.applications();
            SearchNavBar.TEMP_LICENCE();
        });

    }
}
