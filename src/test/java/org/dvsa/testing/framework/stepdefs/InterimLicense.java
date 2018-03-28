package org.dvsa.testing.framework.stepdefs;

import activesupport.number.Int;
import activesupport.string.Str;
import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import javafx.scene.layout.Background;
import org.dvsa.testing.*;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.pages.*;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.dvsa.testing.lib.pages.internal.*;

public class InterimLicense implements En {
    public InterimLicense() {

        Given("^I have an interim application$", () -> {
            Application.interimLink();
        });

        When("^I have zero vehicle authority on a goods variation application$", () -> {
         InterimPage.addInterim();
         InterimPage.enterInterimDetail(Str.randomWord(150));
         InterimPage.startDate(12,01,2018);
         InterimPage.endDate(12, 04, 2018);
         InterimPage.vehicleAuthority(1);
         InterimPage.trailerAuthority(0);

        });

        Then("^I save the application with no error$", () -> {
            InterimPage.save();
        });
    }

}

