package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.IllegalBrowserException;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;
import org.dvsa.testing.framework.Journeys.APIJourneySteps;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RefundInterim extends BasePage implements En {
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
            world.grantLicence.getOutstandingFees(world.createLicence.getApplicationNumber());
            world.grantLicence.payOutstandingFees(world.createLicence.getOrganisationId(), world.createLicence.getApplicationNumber());
        });
        And("^the licence has been refused$", () -> {
            world.grantLicence.refuse(world.createLicence.getApplicationNumber());
        });
        Then("^the interim fee should be refunded$", () -> {
            world.updateLicence.createInternalAdminUser();
            world.UIJourneySteps.navigateToInternalAdminUserLogin(world.updateLicence.getAdminUserLogin(), world.updateLicence.getAdminUserEmailAddress());
            world.UIJourneySteps.searchAndViewLicence();
            clickByLinkText("Fees");
            do {
                waitAndClick("//*[@id=\"status\"]/option[@value='all']", SelectorType.XPATH);
            } while (!isTextPresent("Paid",10));
            assertTrue(checkForPartialMatch("£68.00"));
            assertTrue(world.genericUtils.returnFeeStatus("CANCELLED"));
        });
        And("^the licence has been withdrawn$", () -> {
            world.grantLicence.withdraw(world.createLicence.getApplicationNumber());
        });
        Then("^the interim fee should not be refunded$", () -> {
            world.updateLicence.createInternalAdminUser();
            world.UIJourneySteps.navigateToInternalAdminUserLogin(world.updateLicence.getAdminUserLogin(), world.updateLicence.getAdminUserEmailAddress());
            world.UIJourneySteps.searchAndViewLicence();
            clickByLinkText("Fees");
            do {
                waitAndClick("//*[@id=\"status\"]/option[@value='all']", SelectorType.XPATH);
            } while (!isTextPresent("Paid",10));
            assertTrue(checkForPartialMatch("£68.00"));
            assertFalse(world.genericUtils.returnFeeStatus("CANCELLED"));
        });
        And("^the licence is granted$", () -> {
            world.APIJourneySteps.grantLicenceAndPayFees();
        });
        And("^the interim is granted$", () -> {
            world.updateLicence.grantInterimApplication(world.createLicence.getApplicationNumber());
        });
        When("^i pay for the interim application$", () -> {
            world.UIJourneySteps.payForInterimApp();
        });
        And("^the application has been refused$", () -> {
            world.grantLicence.refuse(String.valueOf(Integer.parseInt(world.createLicence.getApplicationNumber()) + 1));
        });
        And("^the application has been withdrawn$", () -> {
            world.grantLicence.withdraw(String.valueOf(Integer.parseInt(world.createLicence.getApplicationNumber()) + 1));
        });
        And("^the variation interim is granted$", () -> {
            world.updateLicence.grantInterimApplication(String.valueOf(Integer.parseInt(world.createLicence.getApplicationNumber()) + 1));
        });
        Then("^the report should be generated$", () -> {
            ValidatableResponse interimTable = world.updateLicence.getInterimRefunds();
            world.updateLicence.createInternalAdminUser();
            world.UIJourneySteps.navigateToInternalAdminUserLogin(world.updateLicence.getAdminUserLogin(), world.updateLicence.getAdminUserEmailAddress());
            click("//*[@class='admin__title']");
            click("//*[@id='menu-admin-dashboard/admin-report']");
            click("//*[@id='menu-admin-dashboard/admin-interim-refunds']");
            waitForTextToBePresent(interimTable.extract().body().jsonPath().get()+ " Interim Refunds"); // Change to any table heading for a count.
            isTextPresent("The table is currently empty",5);

            enterText("//*[@id='feeStartDate_day']","2", SelectorType.XPATH);
            enterText("//*[@id='feeStartDate_month']","12", SelectorType.XPATH);
            enterText("//*[@id='feeStartDate_year']","2018", SelectorType.XPATH);

            enterText("//*[@id='feeEndDate_day']","2", SelectorType.XPATH);
            enterText("//*[@id='feeEndDate_month']","12", SelectorType.XPATH);
            enterText("//*[@id='feeEndDate_year']","2018", SelectorType.XPATH);

            selectValueFromDropDown("//*[@id='trafficAreas']",SelectorType.XPATH, "North East of England");

            click("//*[@id='submit']");

            // Now target table and stream of its data.

            // Check there is a restart button and that it does return the page to as it was before.
        });
        And("^the refund should be displayed$", () -> {

        });
    }
}