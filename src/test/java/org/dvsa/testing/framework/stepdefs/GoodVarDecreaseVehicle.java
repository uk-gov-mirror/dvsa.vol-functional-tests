package org.dvsa.testing.framework.stepdefs;

import com.fasterxml.jackson.databind.ser.Serializers;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;

public class GoodVarDecreaseVehicle extends BasePage implements En {
    World world = new World();

    public GoodVarDecreaseVehicle(World world) {
        When("^A selfserve user decreases the vehicle authority count$", () -> {
            world.genericUtils.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.genericUtils.changeVehicleReq("4");
            world.genericUtils.changeVehicleAuth("4");
        });
        When("^A selfserve user decreases the vehicle required count by invalid characters$", () -> {
            world.genericUtils.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.genericUtils.changeVehicleReq("-6");
        });
        When("^A selfserve user decreases the vehicle authority by invalid charecters$", () -> {
            world.genericUtils.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            world.genericUtils.changeVehicleReq("6");
            world.genericUtils.changeVehicleAuth("-6");
        });
    }
}
