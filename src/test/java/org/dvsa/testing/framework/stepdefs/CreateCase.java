package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.UpdateLicenceAPI;

public class CreateCase implements En {

    private World world;

    public CreateCase(World world) {
        this.world = world;
        world.updateLicence = new UpdateLicenceAPI(world);

        When("^I create a new case$", () -> {
            world.updateLicence.createCase();
            world.updateLicence.addComplaint();
            world.updateLicence.addConviction();
        });
        Then("^I should be able to view the case details$", () -> {
//
        });
    }
}
