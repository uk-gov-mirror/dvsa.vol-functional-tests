package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;

public class GrantApplication implements En {

    private World world;
    private static String licenceType;

    public GrantApplication(World world) {
        this.world = world;

        When("^I pay fees$", () -> {
                world.genericUtils.payFeesAndGrantLicence();
        });
        Then("^the licence should be granted$", () -> {
            world.grantLicence.payGrantFees();
        });
    }
}