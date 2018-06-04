package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.framework.stepdefs.World;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SelfServePayFees extends BasePage implements En {
    private World world;

    public SelfServePayFees(World world) {
        When("^I pay fees on self serve$", () -> {
            world.grantLicence.createOverview(world.createLicence.getApplicationNumber());
            world.genericUtils.externalUserLogin();
            clickByLinkText("Fees");
            click("//*[@id='checkall']", SelectorType.XPATH);
            click("//*[@id=\"pay\"]", SelectorType.XPATH);
            click("//*[@id=\"form-actions[pay]\"]", SelectorType.XPATH);
            waitForTextToBePresent("Card Number*");
//            waitForTextToBePresent("Online Payments");
            enterText("//*[@id='scp_cardPage_cardNumber_input']", "4006000000000600");
            enterText("//*[@id='scp_cardPage_cardNumber_input']", "10");
            enterText("//*[@id='scp_cardPage_expiryDate_input2']", "50");
            enterText("//*[@id='scp_cardPage_csc_input']", "123");
            click("//*[@id='scp_cardPage_buttonsNoBack_continue_button']", SelectorType.XPATH);
            enterText("//*[@id='scp_additionalInformationPage_cardholderName_input']", "Mr Regression Test");
            click("//*[@id='scp_additionalInformationPage_buttons_continue_button']", SelectorType.XPATH);
            click("//*[@id='scp_storeCardConfirmationPage_buttons_back_button']", SelectorType.XPATH);
            waitForTextToBePresent("Payment successful");
            clickByLinkText("Back");
            clickByLinkText("fees");
        });
        And("^an internal user has granted my application$", () -> {
            world.grantLicence.grant(world.createLicence.getLicenceNumber());
        });
        Then("^my licence should valid$", () -> {
            System.out.println("Licence Number =========== :" + world.createLicence.getLicenceNumber());
        });
    }

    @After
    public void tearDown(){
        Hooks hooks = new Hooks();
        hooks.attach();
    }
}
