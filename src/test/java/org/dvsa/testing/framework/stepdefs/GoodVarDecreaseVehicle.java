package org.dvsa.testing.framework.stepdefs;

import com.fasterxml.jackson.databind.ser.Serializers;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

public class GoodVarDecreaseVehicle extends BasePage implements En {
    World world = new World();

    public GoodVarDecreaseVehicle(World world) {
        When("^A selfserve user decreases the vehicle authority count$", () -> {
            world.journeySteps.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.journeySteps.changeVehicleReq(String.valueOf(world.createLicence.getNoOfVehiclesRequired() - 1));
            world.journeySteps.changeVehicleAuth(String.valueOf(world.createLicence.getNoOfVehiclesRequired() - 1));
        });
        When("^A selfserve user decreases the vehicle required count by invalid characters$", () -> {
            world.journeySteps.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.journeySteps.changeVehicleReq("-6");
        });
        When("^A selfserve user decreases the vehicle authority by invalid charecters$", () -> {
            world.journeySteps.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.journeySteps.changeVehicleReq("6");
            world.journeySteps.changeVehicleAuth("-6");
        });
        Then("^a status of update required should be shown next to Review and declarations$", () -> {
            untilExpectedTextInElement("//*[@id=\"overview-item__undertakings\"]",  SelectorType.XPATH,"REQUIRES ATTENTION", 10);
        });
    }
}
