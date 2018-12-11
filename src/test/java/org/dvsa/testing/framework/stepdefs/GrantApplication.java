package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrantApplication implements En {

    private World world;
    private ValidatableResponse apiResponse;

    public GrantApplication(World world) {
        this.world = world;

        When("^I grant licence$", () -> {
            world.grantLicence.grantLicence();
        });
        Then("^the licence should be granted$", () -> {
            apiResponse = world.grantLicence.payGrantFees();
            assertTrue(apiResponse.extract().response().asString().contains("documents\\/Licensing\\/Other_Documents"));
            System.out.println(world.createLicence.getLicenceNumber());
        });
    }
}