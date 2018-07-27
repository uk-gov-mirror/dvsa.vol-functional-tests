package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;

public class AddDirectorVariation implements En {
    private ValidatableResponse response;

    public AddDirectorVariation(World world) {
        When("^i add a new person$", () -> {
           world.updateLicence.createVariation();
        });
        And("^i enter financial details$", () -> {
          world.createLicence.addFinancialHistory();
        });
        And("^i enter previous convictions details$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^a new director should be added to my licence$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
