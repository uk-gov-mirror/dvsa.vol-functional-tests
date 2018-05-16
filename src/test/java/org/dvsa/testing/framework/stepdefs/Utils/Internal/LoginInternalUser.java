package org.dvsa.testing.framework.stepdefs.Utils.Internal;

import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.dvsa.testing.lib.pages.BasePage;

public class LoginInternalUser extends BasePage implements En {

    public static String USER_EMAIL = "usr291";
    public static String USER_PASSWORD = "Password1";
    private CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();

    public LoginInternalUser() throws Exception{

        Given("^I have logged in to internal$", () -> {
            if (goodsApp.getApplicationNumber() == null) {
                goodsApp.createAndSubmitGoodsApp();
            }

            EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));
            String myURL = URL.build(ApplicationType.INTERNAL, env).toString();

            Browser.go(myURL);

            if(isTextPresent("Username",60))
               Login.signIn(USER_EMAIL, USER_PASSWORD);
            else{
                //TODO Replace with logger
                System.out.println("Already logged In");
            }
        });

        And("^I have an internal application$", () -> {
            selectValueFromDropDown("//select[@id='search-select']",SelectorType.XPATH,"Applications");
            do {
                SearchNavBar.search(goodsApp.getApplicationNumber());
            } while (!isLinkPresent(goodsApp.getApplicationNumber(), 60));
            clickByLinkText(goodsApp.getApplicationNumber());
            if (isLinkPresent("Interim", 60))
                clickByLinkText("Interim ");
        });
    }
}