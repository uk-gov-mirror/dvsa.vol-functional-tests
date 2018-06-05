package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;

public class GrantApplication implements En {

    private World world;
    private static String licenceType;

    public GrantApplication(World world) {
        this.world = world;

        When("^I pay fees$", () -> {
            if (String.valueOf(licenceType).equals("public")) {
                world.genericUtils.payPsvFeesAndGrantLicence();
            } else {
                world.genericUtils.payGoodsFeesAndGrantLicence();
            }
        });
        Then("^the licence should be granted$", () -> {
            world.genericUtils.grantLicence().payGrantFees();
        });
    }
}