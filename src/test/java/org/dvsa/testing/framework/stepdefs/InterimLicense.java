package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.internal.*;
import org.joda.time.LocalDate;
import org.dvsa.testing.lib.pages.BasePage;

import static org.junit.Assert.assertTrue;


public class InterimLicense extends BasePage implements En {
    public InterimLicense() {


        When("^I have zero vehicle authority on a goods variation application$", () -> {
            InterimPage.addInterim();
            InterimPage.startDate(LocalDate.now().getDayOfWeek(), LocalDate.now().getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.endDate(LocalDate.now().plusDays(7).getDayOfWeek(), LocalDate.now().plusMonths(2).getMonthOfYear(), LocalDate.now().getYear());
            InterimPage.vehicleAuthority(10);
        });

        Then("^I save the application with an error$", () -> {
            InterimPage.save();
            assertTrue(isTextPresent( "The interim vehicle authority cannot exceed the total vehicle authority",60));
        });
    }
}



