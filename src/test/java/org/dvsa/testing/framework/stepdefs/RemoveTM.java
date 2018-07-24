package org.dvsa.testing.framework.stepdefs;

import activesupport.aws.s3.S3;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static junit.framework.TestCase.assertTrue;
import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RemoveTM extends BasePage implements En {

    private static String oldAlertValue = "You are removing your last Transport Manager. If you haven't yet made an application to appoint a replacement, " +
            "you must contact us on 0300 123 9000 or at notifications@vehicle-operator-licensing.service.gov.uk";
    private static String newAlertValue = "You are about to remove the last transport manager for this licence. Do you want to send a letter about this to the operator to all known addresses?\n" +
            "If yes, this will be automatically issued tomorrow.";
    public static String alertHeaderValue = "Are you sure you want to remove this Transport Manager?";
    private static String applicationVariationTMAlertContent = "This action is permanent and cannot be undone.";
    private World world;

    public RemoveTM(World world) throws Exception {
        this.world = world;

        Given("^i have an application with a transport manager$", () -> {
            if (world.createLicence.getOperatorType() == null) {
                world.createLicence.setOperatorType("public");
            }
            world.genericUtils.createApplication();
        });
        When("^the transport manager has been removed by an internal user$", () -> {
            world.genericUtils.createAdminUser();
            world.genericUtils.searchAndViewApplication();
            world.genericUtils.removeInternalTransportManager();
        });
        Then("^a pop up message should be displayed$", () -> {
            waitForTextToBePresent(alertHeaderValue);
            String alertContent = getElementValueByText("//*[@id=\"pg:lva-licence/transport_managers:index\"]/div[2]/div/div[2]/div/p", SelectorType.XPATH);
            assertEquals(alertContent, newAlertValue);
        });
        Given("^i add a transport manager to an existing licence$", () -> {
            world.createLicence.setUsername("newTmApi");
            world.createLicence.addTransportManager();
            world.createLicence.submitTransport();
        });
        Then("^the remove TM popup should not be displaying new TM remove text$", () -> {
            waitForTextToBePresent(alertHeaderValue);
            if (Browser.getURL().contains("variation") || Browser.getURL().contains("application")) {
                assertFalse(isTextPresent(newAlertValue, 60));
                assertTrue(isTextPresent(applicationVariationTMAlertContent, 60));
            }
            if (Browser.getURL().contains("ssap1")) {
                String alertContent = getElementValueByText("//div[@class='modal__content']/p", SelectorType.XPATH);
                assertEquals(alertContent, oldAlertValue);
            }
            if (tmCount > 1) {
                assertFalse(isTextPresent(newAlertValue, 60));
                assertTrue(isTextPresent(applicationVariationTMAlertContent, 60));
            }
        });
        Given("^a self-serve user removes the last TM$", () -> {
            world.genericUtils.externalUserLogin();
            String password = S3.getTempPassword(world.createLicence.getEmailAddress());

            if (isTextPresent("Username", 60))
                Login.signIn(world.createLicence.getLoginId(), password);
            else if (isTextPresent("Current password", 60)) {
                enterField(nameAttribute("input", "oldPassword"), password);
                enterField(nameAttribute("input", "newPassword"), "Password1");
                enterField(nameAttribute("input", "confirmPassword"), "Password1");
                click(nameAttribute("input", "submit"));
            }
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Transport Managers");
            click("//*[@value='Remove']", SelectorType.XPATH);
        });
        Given("^the licence has been granted$", () -> {
            world.genericUtils.payFeesAndGrantLicence();
            world.genericUtils.grantLicence().payGrantFees();

        });
        When("^i create a variation$", () -> {
            world.updateLicence.createVariation();
            world.updateLicence.updateLicenceType(world.genericUtils.createApp().getLicenceId());
        });
        And("^user attempts to remove the last TM without selecting an option$", () -> {
            waitForTextToBePresent(alertHeaderValue);
            click("//*[@id='form-actions[submit]']", SelectorType.XPATH);
        });
        Then("^an error message should be displayed$", () -> {
            waitForTextToBePresent(alertHeaderValue);
            do {
                // do nothing
            } while (!isTextPresent("You must select an option", 60));
            isLinkPresent("You must select an option", 60);
        });

        After(new String[]{"@INT"}, (Scenario scenario) -> {
            String[] args = new String[0];
            Hooks hooks = new Hooks();
            hooks.main(args);
        });
    }
}