package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;


public class GoodVarIncreaseVehicle extends BasePage implements En  {
    World world = new World();

    public GoodVarIncreaseVehicle(World world) {

        When("^A selfserve user increases the vehicle authority count$", () -> {
         world.genericUtils.externalUserLogin();
         clickByLinkText(world.createLicence.getLicenceNumber());
         world.genericUtils.changeVehicleAuth("6");
        });

        Then("^a status of update required should be shown next to financial evidence$", () -> {
         isExpectedTextInElementWithin(".status.orange, .orange.overview__status", "REQUIRES ATTENTION", 10);
        });
    }
}
