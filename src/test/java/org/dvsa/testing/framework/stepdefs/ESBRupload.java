package org.dvsa.testing.framework.stepdefs;

import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.*;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;

import static junit.framework.TestCase.assertTrue;
import static org.dvsa.testing.framework.stepdefs.Utils.External.Scenarios.generateAndGrantPsvApplicationPerTrafficArea;
import static org.dvsa.testing.framework.stepdefs.Utils.External.Scenarios.internalSiteAddBusNewReg;
import static org.dvsa.testing.framework.stepdefs.Utils.External.Scenarios.uploadAndSubmitESBR;
import static org.junit.Assert.assertFalse;

public class ESBRupload extends BasePage implements En {

    private static String USER_EMAIL = "usr291";
    private static String USER_PASSWORD = "Password1";

    private CreateInterimPsvLicenceAPI psvApp = new CreateInterimPsvLicenceAPI();
    private GrantApplicationAPI grantApp = new GrantApplicationAPI();
    private GenericUtils genericUtils = new GenericUtils();

    public ESBRupload() {

        Given("^I have a psv application with traffic area \"([^\"]*)\" which has been granted$", (String arg0) -> {
            generateAndGrantPsvApplicationPerTrafficArea(psvApp, grantApp, arg0);
        });

        When("^I upload a short notice esbr file$", () -> {
            // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
            uploadAndSubmitESBR(genericUtils,psvApp,"future",1);
        });

        Then("^A short notice flag should be displayed in selfserve$", () -> {
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            } while (isTextPresent("processing", 60));

            assertTrue(isTextPresent("successful", 60));
            assertTrue(isTextPresent("New", 60));
            assertTrue(isTextPresent("short notice", 60));
        });
        And("^A short notice tab should be displayed in internal$", () -> {
            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = org.dvsa.testing.lib.URI.build(ApplicationType.INTERNAL, env);

            if (Browser.isInitialised()) {
                //Quit Browser and open a new window
                Browser.quit();
            }
            Browser.go(URL);

            Login.signIn(USER_EMAIL, USER_PASSWORD);
            selectValueFromDropDown("//select[@id='search-select']",SelectorType.XPATH,"Bus registrations");
            do {
                SearchNavBar.search(psvApp.getLicenceNumber());
            } while (!isLinkPresent(psvApp.getLicenceNumber(), 60));
            clickByLinkText(psvApp.getLicenceNumber());
            assertTrue(isTextPresent("Short notice", 60));
        });
        When("^I upload an esbr file$", () -> {
            // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
            uploadAndSubmitESBR(genericUtils,psvApp,"future",4);
        });
        Then("^A short notice flag should not be displayed in selfserve$", () -> {
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            } while (isTextPresent("processing", 60));

            assertTrue(isTextPresent("successful", 60));
            assertTrue(isTextPresent("New", 60));
            assertFalse(isTextPresent("short notice", 60));
        });
        And("^Any registrations created in internal should display a short notice tab$", () -> {
            clickByLinkText(psvApp.getLicenceNumber());
            click(nameAttribute("button", "action"));
            internalSiteAddBusNewReg();
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Service details", 2));//condition
            assertTrue(isTextPresent("Short notice", 30));
        });
        And("^A short notice tab should not be displayed in internal$", () -> {
            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = org.dvsa.testing.lib.URI.build(ApplicationType.INTERNAL, env);

            if (Browser.isInitialised()) {
                //Quit Browser and open a new window
                Browser.quit();
            }
            Browser.go(URL);

            Login.signIn(USER_EMAIL, USER_PASSWORD);
            selectValueFromDropDown("//select[@id='search-select']",SelectorType.XPATH,"Bus registrations");
            do {
                SearchNavBar.search(psvApp.getLicenceNumber());
            } while (!isLinkPresent(psvApp.getLicenceNumber(), 60));
            clickByLinkText(psvApp.getLicenceNumber());
            assertFalse(isTextPresent("Short notice", 60));
        });
        And("^Any registrations created in internal should not display a short notice tab$", () -> {
            clickByLinkText(psvApp.getLicenceNumber());
            click(nameAttribute("button", "action"));
            internalSiteAddBusNewReg();
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Service details", 2));//condition
            assertFalse(isTextPresent("Short notice", 30));
        });
    }
}