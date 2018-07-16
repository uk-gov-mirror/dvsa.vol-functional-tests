package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.*;

public class SelfServePayFees extends BasePage implements En {
    private World world;
    private String bankCardNumber = "4006000000000600";
    private String cardExpiryMonth = "10";
    private String cardExpiryYear = "50";

    public SelfServePayFees(World world) {
        When("^I pay fees on self serve$", () -> {
            world.grantLicence.createOverview(world.createLicence.getApplicationNumber());
            world.genericUtils.externalUserLogin();
            clickByLinkText("Fees");
            click("//*[@id='checkall']", SelectorType.XPATH);
            click("//*[@id='pay']", SelectorType.XPATH);
            click("//*[@id='form-actions[pay]']", SelectorType.XPATH);
            world.genericUtils.payFee(null,"card",bankCardNumber,cardExpiryMonth,cardExpiryYear);
        });
        And("^an internal user has granted my application$", () -> {
            world.grantLicence.grant(world.createLicence.getApplicationNumber());
            world.grantLicence.payGrantFees();
        });
        Then("^my licence should valid$", () -> {
            clickByLinkText("Home");
            assertTrue(isTextPresent("Valid",80));
        });
    }
}