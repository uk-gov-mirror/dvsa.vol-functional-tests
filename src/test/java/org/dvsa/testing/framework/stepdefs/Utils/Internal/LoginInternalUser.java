package org.dvsa.testing.framework.stepdefs.Utils.Internal;

import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.dvsa.testing.lib.pages.BasePage;

import static org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI.*;

public class LoginInternalUser extends BasePage implements En {

    public static String USER_EMAIL = "usr291";
    public static String USER_PASSWORD = "Password1";
    private CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();

    public LoginInternalUser() {

        Given("^I have logged in to internal$", () -> {
            if (goodsApp.getApplicationNumber() == null) {
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

        And("^I have an internal application$", () -> {
            do {
                SearchNavBar.search(goodsApp.getApplicationNumber());
                SearchNavBar.applications();
            } while (!isLinkPresent(goodsApp.getApplicationNumber(), 60));
            clickByLinkText(goodsApp.getApplicationNumber());
            if (isLinkPresent("Interim", 60))
                clickByLinkText("Interim ");
        });
    }
}