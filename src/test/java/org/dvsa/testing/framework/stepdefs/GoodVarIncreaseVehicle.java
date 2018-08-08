package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;


public class GoodVarIncreaseVehicle extends BasePage implements En  {
    World world = new World();

    public GoodVarIncreaseVehicle(World world) {

        When("^A selfserve user increases the vehicle authority count$", () -> {
         world.journeySteps.externalUserLogin();
         clickByLinkText(world.createLicence.getLicenceNumber());
         world.journeySteps.changeVehicleReq("6");
         world.journeySteps.changeVehicleAuth("6");
        });

        Then("^a status of update required should be shown next to financial evidence$", () -> {
         isExpectedTextInElementWithin(".status.orange, .orange.overview__status", "REQUIRES ATTENTION", 10);
        });

        When("^A selfserve user increases the vehicle required count by invalid characters$", () -> {
            world.journeySteps.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.journeySteps.changeVehicleReq("+6");

        });
        Then("^An error message should appear$", () -> {
            isTextPresent("//*[@id=\"OperatingCentre\"]/fieldset[2]/div[1]/div/p",  20);
        });

        When("^A selfserve user increases the vehicle authority by invalid charecters$", () -> {
            world.journeySteps.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.journeySteps.changeVehicleReq("6");
            world.journeySteps.changeVehicleAuth("+6");
        });
        Then("^An error should appear$", () -> {
            isTextPresent("//*[@id=\"OperatingCentres\"]/fieldset[3]/div[1]/div/p",  20);
        });
    }
}