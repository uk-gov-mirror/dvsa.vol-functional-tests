package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.internal.*;
import org.joda.time.LocalDate;
import org.dvsa.testing.lib.pages.BasePage;

import static org.dvsa.testing.framework.stepdefs.Utils.APICreateInterimGoodsLicence.getNoOfVehiclesRequired;
import static org.junit.Assert.assertTrue;


public class InterimLicense extends BasePage implements En {

    private static String errorMessage = "The interim vehicle authority cannot exceed the total vehicle authority";

    public InterimLicense() {

        When("^I have an interim vehicle authority greater than my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(getNoOfVehiclesRequired()+ 1);
        });

        Then("^I should get an error when i save the application$", () -> {
            InterimPage.save();
            assertTrue(isTextPresent(errorMessage,60));
        });
        When("^I have an interim vehicle authority equal to my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(getNoOfVehiclesRequired());
        });
        Then("^I should be able to save the application without any errors$", () -> {
            InterimPage.save();
            assertTrue(!isTextPresent(errorMessage,60));
        });
        When("^I have an interim vehicle authority less than my application vehicle authority$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(getNoOfVehiclesRequired() -1);
        });
    }
}


