package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;

public class RefundInterim implements En {
    public RefundInterim(World world) {
        Given("^i have an interim \"([^\"]*)\" \"([^\"]*)\" application$", (String operatorType, String licenceType) -> {
            if (licenceType.equals("si")) {
                world.createLicence.setLicenceType("standard_international");
            } else if (licenceType.equals("sn")) {
                world.createLicence.setLicenceType("standard_national");
            } else {
                world.createLicence.setLicenceType("standard_national");
            }
            world.createLicence.setIsInterim("Y");
            world.createLicence.setOperatorType(operatorType);
            world.APIJourneySteps.registerAndGetUserDetails();
            world.APIJourneySteps.createApplication();
            world.APIJourneySteps.submitApplication();
        });
        When("^the interim fee has been paid$", () -> {
            world.grantLicence.createOverview(world.createLicence.getApplicationNumber());
            world.grantLicence.getOutstandingFees(world.createLicence.getApplicationNumber());
            world.grantLicence.payOutstandingFees(world.createLicence.getOrganisationId(), world.createLicence.getApplicationNumber());
        });
        And("^the licence has been refused$", () -> {
            world.grantLicence.refuse(world.createLicence.getApplicationNumber());
        });
        Then("^the interim fee should be refunded$", () -> {
        });
        And("^the licence has been withdrawn$", () -> {
            world.grantLicence.withdraw(world.createLicence.getApplicationNumber());
        });
        Then("^the interim fee should not be refunded$", () -> {
        });
        And("^the licence is granted$", () -> {
            world.APIJourneySteps.grantLicenceAndPayFees();
        });
        And("^the interim is granted$", () -> {
            world.updateLicence.grantInterimApplication();
        });
    }
}