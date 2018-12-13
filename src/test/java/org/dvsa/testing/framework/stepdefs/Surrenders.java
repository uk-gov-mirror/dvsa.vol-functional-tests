package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.UpdateLicenceAPI;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.hamcrest.Matchers;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dvsa.testing.framework.Journeys.APIJourneySteps.adminApiHeader;

public class Surrenders implements En {
    ValidatableResponse apiResponse;
    private String selfServeUserPid;
    private Integer surrenderId;

    public Surrenders(World world) {
        Given("^surrenders has been switched \"([^\"]*)\"$", (String toggle) -> {
            String status = "";
            if (toggle.toLowerCase().equals("off")) {
                status = "inactive";
            } else if (toggle.toLowerCase().equals("on")) {
                status = "always-active";
            }
            world.updateLicence.updateFeatureToggle("15","Backend Surrender","back_surrender",status);
        });
        Then("^as \"([^\"]*)\" user I can surrender a licence$", (String userType) -> {
            String pid = "";
            if(userType.equals("selfserve")){
                pid = world.createLicence.getPid();
            } else if(userType.equals("internal")) {
                pid = adminApiHeader();
            }
            apiResponse = world.updateLicence.surrenderLicence(world.createLicence.getLicenceId(),pid);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Surrender successfully created"));
            apiResponse.extract().jsonPath().getString("id.surrender");
            assertThat(apiResponse.body("id.surrender", Matchers.isA(Number.class)));
            this.surrenderId = apiResponse.extract().jsonPath().getInt("id.surrender");
            apiResponse.statusCode(HttpStatus.SC_CREATED);
        });
        And("^as \"([^\"]*)\" user I can update surrender details$", (String userType) -> {
            String pid = "";
            if(userType.equals("selfserve")){
                pid = world.createLicence.getPid();
            } else if(userType.equals("internal")) {
                pid = adminApiHeader();
            }
            apiResponse = world.updateLicence.updateSurrender(world.createLicence.getLicenceId(),pid, this.surrenderId);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Surrender successfully updated"));
            apiResponse.body("id.surrender", Matchers.equalTo(this.surrenderId));
            apiResponse.statusCode(HttpStatus.SC_OK);
        });
        And("^as \"([^\"]*)\" user I cannot surrender a licence again$", (String userType) -> {
            String pid = "";
            if(userType.equals("selfserve")){
                pid = world.createLicence.getPid();
            } else if(userType.equals("internal")) {
                pid = adminApiHeader();
            }
            apiResponse = world.updateLicence.surrenderLicence(world.createLicence.getLicenceId(),pid);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("A surrender record already exists for this licence"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);
        });
        Given("^as a selfserve user i apply for a \"([^\"]*)\" licence$", (String arg0) -> {
            this.selfServeUserPid = world.createLicence.getPid();
            world.genericUtils = new GenericUtils(world);
            world.createLicence.setOperatorType(arg0);
            world.APIJourneySteps.registerAndGetUserDetails();
            world.APIJourneySteps.createApplication();
            world.APIJourneySteps.submitApplication();
            if(String.valueOf(arg0).equals("public")){
                world.APIJourneySteps.grandLicenceAndPayFees();
                System.out.println("Licence: " + world.createLicence.getLicenceNumber());
            }
            else {
                world.APIJourneySteps.grandLicenceAndPayFees();
                world.APIJourneySteps.grantLicence().payGrantFees();
                System.out.println("Licence: " + world.createLicence.getLicenceNumber());
            }
        });  
        And("^another user is unable to surrender my licence$", () -> {
            apiResponse = world.updateLicence.surrenderLicence(world.createLicence.getLicenceId(),this.selfServeUserPid);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("You do not have access to this resource"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);

        });
        And("^another user is unable to update my surrender details$", () -> {
            apiResponse = world.updateLicence.updateSurrender(world.createLicence.getLicenceId(),this.selfServeUserPid, this.surrenderId);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("You do not have access to this resource"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);
        });
        And("^as internal user i can delete a surrender$", () -> {
            apiResponse = world.updateLicence.deleteSurrender(world.createLicence.getLicenceId(),adminApiHeader(), this.surrenderId);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.toLowerCase().contains("id ".concat(this.surrenderId.toString()).concat(" deleted")));
            apiResponse.body("id.id".concat(this.surrenderId.toString()) , Matchers.equalTo(this.surrenderId.toString()));
            apiResponse.statusCode(HttpStatus.SC_OK);
        });
        And("^as selfserve user I cannot delete my surrender$", () -> {
            apiResponse = world.updateLicence.deleteSurrender(world.createLicence.getLicenceId(),world.createLicence.getPid(), this.surrenderId);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("You do not have access to this resource"));
            apiResponse.statusCode(HttpStatus.SC_FORBIDDEN);
        });
        Then("^as \"([^\"]*)\" user I cannot surrender a licence$", (String userType) -> {
            apiResponse = world.updateLicence.surrenderLicence(world.createLicence.getLicenceId(),adminApiHeader());
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Handler Dvsa\\Olcs\\Api\\Domain\\CommandHandler\\Surrender\\Create is currently disabled via feature toggle"));
            apiResponse.statusCode(HttpStatus.SC_BAD_REQUEST);
        });
        Then("^as \"([^\"]*)\" user I cannot update a surrender$", (String userType) -> {
            apiResponse = world.updateLicence.updateSurrender(world.createLicence.getLicenceId(),adminApiHeader(),1);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Handler Dvsa\\Olcs\\Api\\Domain\\CommandHandler\\Surrender\\Update is currently disabled via feature toggle"));
            apiResponse.statusCode(HttpStatus.SC_BAD_REQUEST);
        });
        Then("^as \"([^\"]*)\" user I cannot delete a surrender$", (String userType) -> {
            apiResponse = world.updateLicence.deleteSurrender(world.createLicence.getLicenceId(),adminApiHeader(),1);
            String createdMessage = apiResponse.extract().jsonPath().getString("messages[0]");
            assertTrue(createdMessage.contains("Handler Dvsa\\Olcs\\Api\\Domain\\CommandHandler\\Surrender\\Delete is currently disabled via feature toggle"));
            apiResponse.statusCode(HttpStatus.SC_BAD_REQUEST);
        });
    }
}