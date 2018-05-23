package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import org.dvsa.testing.lib.pages.internal.*;
import org.joda.time.LocalDate;
import org.dvsa.testing.lib.pages.BasePage;

import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.internalUserLogin;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class InterimLicense extends BasePage implements En {

    private static String VehicleErrorMessage = "The interim vehicle authority cannot exceed the total vehicle authority";
    private static String noDatesErrorMessage = "Value is required";
    private World world;

    public InterimLicense(World world) throws Exception {
        this.world = world;

        When("^I have an interim vehicle authority greater than my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(world.createLicence.getNoOfVehiclesRequired()+ 1);
        });

        When("^I have an interim vehicle authority equal to my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(world.createLicence.getNoOfVehiclesRequired());
        });

        When("^I have an interim vehicle authority less than my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(world.createLicence.getNoOfVehiclesRequired() -1);
        });

        When("^I create an interim application with no start and end dates$", () -> {
            InterimPage.vehicleAuthority(world.createLicence.getNoOfVehiclesRequired());
            InterimPage.trailerAuthority(world.createLicence.getNoOfVehiclesRequired());
        });

        Then("^I should get an error when i save the application$", () -> {
            InterimPage.save();
            assertTrue(isTextPresent(VehicleErrorMessage,60));
        });

        Then("^I should be able to save the application without any errors$", () -> {
            InterimPage.save();
            assertFalse(isTextPresent(VehicleErrorMessage,60));
        });

        Then("^I should not error when i save the application$", () -> {
            InterimPage.save();
            assertFalse(isTextPresent(noDatesErrorMessage,60));
        });

        Then("^I should not error when i attempt to grant the application$", () -> {
            InterimPage.grant();
            assertTrue(isTextPresent(noDatesErrorMessage,60));
        });
        And("^i have logged in to internal$", () -> {
            internalUserLogin();
            world.genericUtils.searchAndViewApplication();
        });
    }
}


