package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java.eo.Se;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.UpdateLicenceAPI;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CreateCase extends BasePage implements En {

    private World world;
    private ValidatableResponse response;

    public CreateCase(World world) {
        this.world = world;
        world.updateLicence = new UpdateLicenceAPI(world);

        When("^I create a new case$", () -> {
            world.updateLicence.createCase();
        });
        Then("^I should be able to view the case details$", () -> {
           response = world.updateLicence.getCaseDetails("cases",world.updateLicence.getCaseId());
           assertThat(response.body("description", Matchers.equalTo("Sent through the API"),
                   "caseType.id",  Matchers.equalTo("case_t_lic")));
        });
        When("^I add a complaint details$", () -> {
            world.updateLicence.addComplaint();
        });
        Then("^Complaint should be created$", () -> {
            response = world.updateLicence.getCaseDetails("complaint",world.updateLicence.getComplaintId());
            assertThat(response.body("driverFamilyName", Matchers.equalTo("Dotell"),
                    "complaintType.id",  Matchers.equalTo("ct_cov")));
        });
        When("^I add conviction details$", () -> {
            world.updateLicence.addConviction();
        });
        Then("^Conviction should be created$", () -> {
            response = world.updateLicence.getCaseDetails("conviction",world.updateLicence.getConvictionId());
            assertThat(response.body("birthDate", Matchers.equalTo("1999-06-10"),
                    "convictionCategory.id",  Matchers.equalTo("conv_c_cat_1065")));
        });
        When("^I add condition undertaking details$", () -> {
           world.updateLicence.addConditionsUndertakings();
        });
        Then("^the condition undertaking should be created$", () -> {
            response = world.updateLicence.getCaseDetails("condition-undertaking",world.updateLicence.getConditionUndertaking());
            assertThat(response.body("conditionCategory.id", Matchers.equalTo("cu_cat_fin"),
                    "licence.id.toString()",  Matchers.hasToString(world.createLicence.getLicenceId())));
        });
        When("^I add submission details$", () -> {
            world.updateLicence.createSubmission();
        });
        Then("^the submission should be created$", () -> {
            response = world.updateLicence.getCaseDetails("submission", world.updateLicence.getSubmissionsId());
            assertThat(response.body("submissionType.id", Matchers.equalTo("submission_type_o_env"),
                    "submissionType.description",  Matchers.equalTo("ENV")));
        });
        When("^I add notes$", () -> {
            world.updateLicence.createCaseNote();
        });
        Then("^case notes should be created$", () -> {
            response = world.updateLicence.getCaseDetails("processing/note",world.updateLicence.getCaseNoteId());
            assertThat(response.body("comment", Matchers.equalTo("case note submitted through the API")));
        });
        And("^i add a new public inquiry$", () -> {
            click("//*[@id='menu-licence/cases']", SelectorType.XPATH);
            clickByLinkText(Integer.toString(world.updateLicence.getCaseId()));
            click("//*[@id='menu-case_hearings_appeals']",SelectorType.XPATH);
            clickByLinkText("Add Public Inquiry");

            // Creation of public inquiry
            enterText("//*[@id='fields[agreedDate]_day']","21",SelectorType.XPATH);
            enterText("//*[@id='fields[agreedDate]_month']","6",SelectorType.XPATH);
            enterText("//*[@id='fields[agreedDate]_year']","2014",SelectorType.XPATH);
            selectValueFromDropDown("//*[@id='fields[agreedByTc]']",SelectorType.XPATH,"Nick Jones");
            selectValueFromDropDown("//*[@id='fields[agreedByTcRole]']",SelectorType.XPATH,"Traffic Commissioner");
            selectValueFromDropDown("//*[@id='assignedCaseworker']",SelectorType.XPATH,"ANDREW DREW");
            selectFirstValueInList("//*[@id='fields_piTypes__chosen']/ul");
            selectFirstValueInList("//*[@id='fields_reasons__chosen']/ul/li/input");
            click("//*[@id='form-actions[submit]']",SelectorType.XPATH);

            // Creation of hearing
            clickByLinkText("Add hearing");





        });
        And("^i add and publish a hearing$", () -> {
        });
        Then("^the public inquiry should be published$", () -> {
        });
        And("^delete a case note$", () -> {
        });
        Then("^the note should be deleted$", () -> {
        });
    }
}