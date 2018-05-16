package org.dvsa.testing.framework.stepdefs;

import activesupport.MissingRequiredArgument;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.lib.pages.internal.*;
import org.joda.time.LocalDate;
import org.dvsa.testing.lib.pages.BasePage;

import java.net.MalformedURLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class InterimLicense extends BasePage implements En {

    private static String VehicleErrorMessage = "The interim vehicle authority cannot exceed the total vehicle authority";
    private static String noDatesErrorMessage = "Value is required";
    private CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();

    public InterimLicense() throws Exception {

        When("^I have an interim vehicle authority greater than my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(goodsApp.getNoOfVehiclesRequired()+ 1);
        });

        When("^I have an interim vehicle authority equal to my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(goodsApp.getNoOfVehiclesRequired());
        });

        When("^I have an interim vehicle authority less than my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(goodsApp.getNoOfVehiclesRequired() -1);
        });

        When("^I create an interim application with no start and end dates$", () -> {
            InterimPage.vehicleAuthority(goodsApp.getNoOfVehiclesRequired());
            InterimPage.trailerAuthority(goodsApp.getNoOfVehiclesRequired());
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
    }
}


