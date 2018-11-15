package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.hamcrest.Matchers;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class Surrenders implements En {
    ValidatableResponse apiResponse;
    private String selfServeUserPid;
    private Integer surrenderId;

    public Surrenders(World world) {
        Then("^i can surrender my licence$", () -> {
        apiResponse = world.APIJourneySteps.surrenderLicence(world.createLicence.getLicenceId(),world.createLicence.getPid());
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Surrender successfully created"));
            apiResponse.extract().jsonPath().getString("id.surrender");
            assertThat(apiResponse.body("id.surrender", Matchers.isA(Number.class)));
            this.surrenderId = apiResponse.extract().jsonPath().getInt("id.surrender");
            apiResponse.statusCode(HttpStatus.SC_CREATED);
        });
        And("^i can update surrender details$", () -> {
            apiResponse = world.APIJourneySteps.updateSurrender(world.createLicence.getLicenceId(),world.createLicence.getPid(), this.surrenderId);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Surrender successfully updated"));
            apiResponse.body("id.surrender", Matchers.equalTo(this.surrenderId));
            apiResponse.statusCode(HttpStatus.SC_OK);
        });
        And("^i cannot surrender my licence again$", () -> {
            apiResponse = world.APIJourneySteps.surrenderLicence(world.createLicence.getLicenceId(),world.createLicence.getPid());
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("A surrender record already exists for this licence"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);
        });
        Given("^as a selfserve user i apply for a \"([^\"]*)\" licence$", (String arg0) -> {
            this.selfServeUserPid = world.createLicence.getPid();
            world.genericUtils = new GenericUtils(world);
            world.createLicence.setOperatorType(arg0);
            world.APIJourneySteps.createAndSubmitApplication();
            if(String.valueOf(arg0).equals("public")){
                world.APIJourneySteps.payFeesAndGrantLicence();
                world.APIJourneySteps.grantLicence().payGrantFees();
                System.out.println("Licence: " + world.createLicence.getLicenceNumber());
            }
            else {
                world.APIJourneySteps.payFeesAndGrantLicence();
                world.APIJourneySteps.grantLicence().payGrantFees();
                System.out.println("Licence: " + world.createLicence.getLicenceNumber());
            }
        });
        And("^another user is unable to surrender my licence$", () -> {
            apiResponse = world.APIJourneySteps.surrenderLicence(world.createLicence.getLicenceId(),this.selfServeUserPid);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("You do not have access to this resource"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);

        });
        And("^another user is unable to update my surrender details$", () -> {
            apiResponse = world.APIJourneySteps.updateSurrender(world.createLicence.getLicenceId(),this.selfServeUserPid, this.surrenderId);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("You do not have access to this resource"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);
        });
    }
}
