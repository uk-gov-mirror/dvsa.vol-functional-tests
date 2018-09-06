package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class ApplicationVerifyJourney implements En {
    public ApplicationVerifyJourney() {
        Given("^i have an application in progress$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^i choose to sign with verify$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the application should be signed with verify$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
