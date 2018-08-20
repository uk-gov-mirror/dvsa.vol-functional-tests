package org.dvsa.testing.framework.stepdefs;

import activesupport.MissingRequiredArgument;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.pages.BasePage;

import static junit.framework.TestCase.assertTrue;
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
            world.journeySteps.viewESBRInExternal();
            assertTrue(isTextPresent("successful", 60));
            assertTrue(isTextPresent("New", 60));
            assertTrue(isTextPresent("short notice", 60));
        });
        And("^A short notice tab should be displayed in internal$", () -> {
            world.genericUtils.createAdminUser();
            world.journeySteps.internalAdminUserLogin();
            world.journeySteps.internalSearchForBusReg();
            assertTrue(isTextPresent("Short notice", 60));
        });
        Then("^A short notice flag should not be displayed in selfserve$", () -> {
            world.genericUtils.executeJenkinsBatchJob("que_typ_ebsr_pack");
            world.journeySteps.viewESBRInExternal();
            assertTrue(isTextPresent("successful", 60));
            assertTrue(isTextPresent("New", 60));
            assertFalse(isTextPresent("short notice", 60));
        });
        And("^Any registrations created in internal should display a short notice tab$", () -> {
            world.journeySteps.internalSiteAddBusNewReg(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
            assertTrue(isTextPresent("Short notice", 30));
        });
        And("^A short notice tab should not be displayed in internal$", () -> {
            world.genericUtils.createAdminUser();
            world.journeySteps.internalAdminUserLogin();
            world.journeySteps.internalSearchForBusReg();
            assertFalse(isTextPresent("Short notice", 60));
        });
        And("^Any registrations created in internal should not display a short notice tab$", () -> {
            world.journeySteps.internalSiteAddBusNewReg(getCurrentDayOfMonth(), getFutureMonth(5), getCurrentYear());
            assertFalse(isTextPresent("Short notice", 30));
        });
        When("^I upload an esbr file with \"([^\"]*)\" days notice$", (String arg0) -> {
            // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
            world.journeySteps.uploadAndSubmitESBR("futureDay", Integer.parseInt(arg0));
        });

        After(new String[]{"@SS"}, 0, 1, (Scenario scenario) -> {
            String[] args = new String[0];
            Hooks hooks = new Hooks();
            hooks.main(args);
        });
        Given("^i add a new bus registration$", () -> {
            world.journeySteps.internalSiteAddBusNewReg(getCurrentDayOfMonth(), getFutureMonth(5), getCurrentYear());
        });
    }
}