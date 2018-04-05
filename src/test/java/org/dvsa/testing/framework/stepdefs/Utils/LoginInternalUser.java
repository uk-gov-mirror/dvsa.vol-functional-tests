package org.dvsa.testing.framework.stepdefs.Utils;

import activesupport.string.Str;
import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.internal.*;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.dvsa.testing.lib.pages.BasePage;
import org.openqa.selenium.By;

import static org.dvsa.testing.framework.stepdefs.Utils.APICreateInterimGoodsLicence.*;

public class LoginInternalUser extends BasePage implements En {

    public static String USER_EMAIL = "usr291";
    public static String USER_PASSWORD = "Password1";

    public LoginInternalUser() {

        Given("^I have logged in to internal$", () -> {
            APICreateInterimGoodsLicence goodsApp = new APICreateInterimGoodsLicence();
            goodsApp.createGoodsApp();

            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = URI.build(ApplicationType.INTERNAL, env);

            Browser.go(URL);
            Login.signIn(USER_EMAIL, USER_PASSWORD);
        });
//TODO: Make search element using table instead of text
        And("^I have an internal application$", () -> {
        do {
            SearchNavBar.search(applicationNumber);
            SearchNavBar.applications();
        } while (isTextPresent("There were no results for your search.", 60));
        clickByLinkText(applicationNumber);
       if (isLinkPresent("Interim", 60))
        clickByLinkText("Interim ");
    });

}
}
