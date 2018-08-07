package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;


public class GoodVarIncreaseVehicle extends BasePage implements En  {
    World world = new World();

    public GoodVarIncreaseVehicle() {
        Given("^I have a valid good licence$", () -> {
            world.genericUtils = new GenericUtils(world);
            world.genericUtils.createApplication();
            world.genericUtils.payFeesAndGrantLicence();
            world.genericUtils.grantLicence().payGrantFees();
            System.out.println("Licence: " + world.createLicence.getLicenceNumber());
            world.genericUtils.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
        });

        When("^A selfserve user increases the vehicle authority count$", () -> {
        clickByLinkText("Operating centres and authorisation");
        clickByLinkText("change your licence");
        waitAndClick("button[name='form-actions[submit]'", SelectorType.CSS);
        waitAndClick("//*[@id=\"OperatingCentres\"]/fieldset[1]/div/div[2]/table/tbody/tr/td[1]/input", SelectorType.XPATH);
        enterField(nameAttribute("input", "data[noOfVehiclesRequired]"),"6");
        click(nameAttribute("button", "form-actions[submit]"));
        click(nameAttribute("button", "form-actions[submit]"));
        enterField(nameAttribute("input", "data[totAuthVehicles]"),"6");
        click(nameAttribute("button", "form-actions[save]"));
        });

        Then("^a status of update required should be shown next to financial evidence$", () -> {
       isExpectedTextInElementWithin(".status.orange, .orange.overview__status", "REQUIRES ATTENTION", 10);
        });
    }
}
