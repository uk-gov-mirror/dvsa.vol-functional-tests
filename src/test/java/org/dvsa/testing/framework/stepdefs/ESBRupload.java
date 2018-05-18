package org.dvsa.testing.framework.stepdefs;

import activesupport.MissingRequiredArgument;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.*;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;

import static junit.framework.TestCase.assertTrue;
import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.*;
import static org.junit.Assert.assertFalse;

public class ESBRupload extends BasePage implements En {

    private CreateInterimPsvLicenceAPI psvApp = new CreateInterimPsvLicenceAPI();
    private GrantLicenceAPI grantApp = new GrantLicenceAPI();
    private GenericUtils genericUtils = new GenericUtils();

    public ESBRupload() throws MissingRequiredArgument {

        Given("^I have a psv application with traffic area \"([^\"]*)\" and enforcement area \"([^\"]*)\" which has been granted$", (String arg0, String arg1) -> {
            generateAndGrantPsvApplicationPerTrafficArea(psvApp, grantApp, arg0, arg1, genericUtils);
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
            internalUserLogin();
            selectValueFromDropDown("//select[@id='search-select']",SelectorType.XPATH,"Bus registrations");
            do {
                SearchNavBar.search(psvApp.getLicenceNumber());
            } while (!isLinkPresent(psvApp.getLicenceNumber(), 60));
            clickByLinkText(psvApp.getLicenceNumber());
            assertTrue(isTextPresent("Short notice", 60));
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
            internalSiteAddBusNewReg(getCurrentDayOfMonth(),getCurrentMonth(),getCurrentYear());
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Service details", 2));//condition
            assertTrue(isTextPresent("Short notice", 30));
        });
        And("^A short notice tab should not be displayed in internal$", () -> {
            internalUserLogin();
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
            internalSiteAddBusNewReg(getCurrentDayOfMonth(),getFutureMonth(4),getCurrentYear());
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Service details", 2));//condition
            assertFalse(isTextPresent("Short notice", 30));
        });
        When("^I upload an esbr file with \"([^\"]*)\" days notice$", (String arg0) -> {
            // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
            uploadAndSubmitESBR(genericUtils,psvApp,"futureDay", Integer.parseInt(arg0));
        });
    }
}