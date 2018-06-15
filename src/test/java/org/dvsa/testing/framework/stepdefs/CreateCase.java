package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;

public class CreateCase implements En {

    public CreateCase(World world) {
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
