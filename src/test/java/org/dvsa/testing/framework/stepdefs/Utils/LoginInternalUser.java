package org.dvsa.testing.framework.stepdefs.Utils;

import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.dvsa.testing.lib.pages.BasePage;

import static org.dvsa.testing.framework.stepdefs.Utils.CreateInterimGoodsLicenceAPI.*;

public class LoginInternalUser extends BasePage implements En {

    public static String USER_EMAIL = "usr291";
    public static String USER_PASSWORD = "Password1";

    public LoginInternalUser() {

        Given("^I have logged in to internal$", () -> {
            if (getApplicationNumber() == null) {
                CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();
                goodsApp.createGoodsApp();
            }

            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = URI.build(ApplicationType.INTERNAL, env);

            Browser.go(URL);

            if(isTextPresent("Username",60))
               Login.signIn(USER_EMAIL, USER_PASSWORD);
            else{
                //TODO Replace with logger
                System.out.println("Already logged In");
            }
        });
                //TODO: Make search element using table instead of text
        And("^I have an internal application$", () -> {
            do {
                SearchNavBar.search(getApplicationNumber());
                SearchNavBar.applications();
            } while (!isLinkPresent(getApplicationNumber(), 60));
            clickByLinkText(getApplicationNumber());
            if (isLinkPresent("Interim", 60))
                clickByLinkText("Interim ");
        });
    }
}
