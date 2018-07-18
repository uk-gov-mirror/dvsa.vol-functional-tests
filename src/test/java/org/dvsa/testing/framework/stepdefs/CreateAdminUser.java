package org.dvsa.testing.framework.stepdefs;

import cucumber.api.Scenario;
import cucumber.api.java8.En;

public class CreateAdminUser implements En {
Scenario scenario;
    public CreateAdminUser(World world) {
        Given("^I create a new internal admin user$", () -> {
            world.updateLicence.createInternalAdminUser();
        });
        Then("^I should be able to login with my new credentials$", () -> {
            world.genericUtils.newAdminUserLogin();
        });
    }
}
