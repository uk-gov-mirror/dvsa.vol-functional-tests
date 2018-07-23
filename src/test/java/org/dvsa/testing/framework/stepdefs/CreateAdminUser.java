package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;

public class CreateAdminUser implements En {

    private ValidatableResponse apiResponse;
    public CreateAdminUser(World world) {
        Given("^I create a new internal admin user$", () -> {
            apiResponse = world.updateLicence.createInternalAdminUser();
        });
        Then("^I should be able to login with my new credentials$", () -> {
            boolean itsTrue = apiResponse.extract().response().asString().contains("ERR_USERNAME_EXISTS");
            world.genericUtils.internalAdminUserLogin(itsTrue);
        });
    }
}
