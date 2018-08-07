package org.dvsa.testing.framework.stepdefs;

import activesupport.MissingRequiredArgument;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;

import static junit.framework.TestCase.assertTrue;
import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.*;
import static org.junit.Assert.assertFalse;

public class ESBRupload extends BasePage implements En {

    private World world;

    public ESBRupload(World world) throws MissingRequiredArgument {
        this.world = world;
        world.genericUtils = new GenericUtils(world);

        Given("^I have a psv application with traffic area \"([^\"]*)\" and enforcement area \"([^\"]*)\" which has been granted$", (String arg0, String arg1) -> {
            world.genericUtils.generateAndGrantPsvApplicationPerTrafficArea(arg0, arg1);
        });

        Then("^A short notice flag should be displayed in selfserve$", () -> {
            world.genericUtils.executeJenkinsBatchJob("que_typ_ebsr_pack");
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            } while (isTextPresent("processing", 60));

            assertTrue(isTextPresent("successful", 60));
            assertTrue(isTextPresent("New", 60));
            assertTrue(isTextPresent("short notice", 60));
        });
        And("^A short notice tab should be displayed in internal$", () -> {
            world.genericUtils.createAdminUser();
            world.genericUtils.internalAdminUserLogin();
            selectValueFromDropDown("//select[@id='search-select']", SelectorType.XPATH, "Bus registrations");
            do {
                SearchNavBar.search(world.createLicence.getLicenceNumber());
            } while (!isLinkPresent(world.createLicence.getLicenceNumber(), 60));
            clickByLinkText(world.createLicence.getLicenceNumber());
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
            clickByLinkText(world.createLicence.getLicenceNumber());
            click(nameAttribute("button", "action"));
            internalSiteAddBusNewReg(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Service details", 2));//condition
            assertTrue(isTextPresent("Short notice", 30));
        });
        And("^A short notice tab should not be displayed in internal$", () -> {
            world.genericUtils.createAdminUser();
            selectValueFromDropDown("//select[@id='search-select']", SelectorType.XPATH, "Bus registrations");
            do {
                SearchNavBar.search(world.createLicence.getLicenceNumber());
            } while (!isLinkPresent(world.createLicence.getLicenceNumber(), 60));
            clickByLinkText(world.createLicence.getLicenceNumber());
            assertFalse(isTextPresent("Short notice", 60));
        });
        And("^Any registrations created in internal should not display a short notice tab$", () -> {
            clickByLinkText(world.createLicence.getLicenceNumber());
            click(nameAttribute("button", "action"));
            internalSiteAddBusNewReg(getCurrentDayOfMonth(), getFutureMonth(5), getCurrentYear());
            do {
                // Refresh page
                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Service details", 2));//condition
            assertFalse(isTextPresent("Short notice", 30));
        });
        When("^I upload an esbr file with \"([^\"]*)\" days notice$", (String arg0) -> {
            // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
            world.genericUtils.uploadAndSubmitESBR("futureDay", Integer.parseInt(arg0));
        });

        After(new String[]{"@SS"}, 0, 1, (Scenario scenario) -> {
            String[] args = new String[0];
            Hooks hooks = new Hooks();
            hooks.main(args);
        });
    }
}