package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.World;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SelfServePayFees extends BasePage implements En {
    private World world;

    public SelfServePayFees(World world) {
        When("^I pay fees on self serve$", () -> {
            world.grantLicence.createOverview(world.createLicence.getApplicationNumber());
            world.genericUtils.externalUserLogin();
            clickByLinkText("Fees");
            click("//*[@id='checkall']", SelectorType.XPATH);
            click("//*[@id='pay']", SelectorType.XPATH);
            click("//*[@id='form-actions[pay]']", SelectorType.XPATH);
            waitForTextToBePresent("Card Number*");
            enterText("//*[@id='scp_cardPage_cardNumber_input']", "4006000000000600", SelectorType.XPATH);
            enterText("//*[@id='scp_cardPage_expiryDate_input']", "10", SelectorType.XPATH);
            enterText("//*[@id='scp_cardPage_expiryDate_input2']", "50", SelectorType.XPATH);
            enterText("//*[@id='scp_cardPage_csc_input']", "123", SelectorType.XPATH);
            click("//*[@id='scp_cardPage_buttonsNoBack_continue_button']", SelectorType.XPATH);
            enterText("//*[@id='scp_additionalInformationPage_cardholderName_input']", "Mr Regression Test", SelectorType.XPATH);
            click("//*[@id='scp_additionalInformationPage_buttons_continue_button']", SelectorType.XPATH);
            waitForTextToBePresent("Online Payments");
            click("//*[@id='scp_confirmationPage_buttons_payment_button']", SelectorType.XPATH);
            waitForTextToBePresent("Online Payments");
            click("//*[@id='scp_storeCardConfirmationPage_buttons_back_button']", SelectorType.XPATH);
            waitForTextToBePresent("Payment successful");
            clickByLinkText("Back");
            waitForTextToBePresent("There are currently no outstanding fees to pay");
        });
        And("^an internal user has granted my application$", () -> {
            world.grantLicence.grant(world.createLicence.getLicenceNumber());
        });
        Then("^my licence should valid$", () -> {
            assertNotNull(world.createLicence.getLicenceNumber());
            System.out.println("Licence Number =========== :" + world.createLicence.getLicenceNumber());
        });
    }
}
