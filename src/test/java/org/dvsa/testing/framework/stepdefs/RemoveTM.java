package org.dvsa.testing.framework.stepdefs;

import activesupport.aws.s3.S3;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
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
    private static String newAlertValue = "'You are about to remove the last transport manager for this licence.\n" +
            "Do you want to send a letter about this to the operator to all known addresses?\n" +
            "\n" + "If yes, this will be automatically issued tomorrow.'";
    private static String alertHeaderValue = "Are you sure you want to remove this Transport Manager?";
    private static String applicationVariationTMAlertContent = "This action is permanent and cannot be undone.";
    private World world;

    public RemoveTM(World world) throws Exception {
        this.world = world;
        Given("^i have an application with a transport manager$", () -> {
            world.genericUtils = new GenericUtils(world);
            world.genericUtils.createApplication("public");
        });
        When("^the transport manager has been removed by an internal user$", () -> {
            internalUserLogin();
            world.genericUtils.searchAndViewApplication();
            world.genericUtils.removeInternalTransportManager();
        });
        Then("^a pop up message should be displayed$", () -> {
            waitForTextToBePresent(alertHeaderValue);
            String alertContent = getElementValueByText("//*[@class='js-content']/p", SelectorType.XPATH);
            assertEquals(alertContent, oldAlertValue);
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
                assertFalse(isTextPresent(newAlertValue, 60));
                assertTrue(isTextPresent(oldAlertValue, 60));
            }
            if(tmCount > 1){
                assertFalse(isTextPresent(newAlertValue, 60));
                assertTrue(isTextPresent(applicationVariationTMAlertContent, 60));
            }
        });
        Given("^a self-serve user removes the last TM$", () -> {
            externalUserLogin();
            String password = S3.getTempPassword(world.createLicence.getEmailAddress());

            if (isTextPresent("Username", 60))
                Login.signIn(world.createLicence.getLoginId(), password);
            else if (isTextPresent("Current password", 60)) {
                enterField(nameAttribute("input", "oldPassword"), password);
                enterField(nameAttribute("input", "newPassword"), "Password1");
                enterField(nameAttribute("input", "confirmPassword"), "Password1");
                click(nameAttribute("input", "submit"));
            }
            clickByLinkText( world.createLicence.getLicenceNumber());
            clickByLinkText("Transport Managers");
            click("//*[@value='Remove']", SelectorType.XPATH);
        });
        Given("^the licence has been granted$", () -> {
            world.genericUtils.payGoodsFeesAndGrantLicence();
            world.genericUtils.grantLicence().payGrantFees(world.genericUtils.createApp().getOrganisationId(), world.genericUtils.createApp().getApplicationNumber());
        });
        When("^i create a variation$", () -> {
            world.genericUtils.createVariation(world.genericUtils.createApp().getLicenceId());
            world.genericUtils.updateLicenceType(world.genericUtils.createApp().getLicenceId());
        });
    }
}