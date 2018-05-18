package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;

import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.payGoodsFeesAndGrantLicence;

public class GrantGoodsApplication implements En {
    public GrantGoodsApplication() throws Exception{
        GrantLicenceAPI grantApp = new GrantLicenceAPI();
        CreateLicenceAPI app = new CreateLicenceAPI();

        When("^I pay fees$", () -> {
            payGoodsFeesAndGrantLicence(grantApp, app);
        });
        Given("^I have an application which is under consideration$", () -> {
            if (app.getApplicationNumber() == null) {
                app.createAndSubmitApp();
            }
        });
        Then("^the application should be granted$", () -> {
            grantApp.payGrantFees(app.getOrganisationId(),app.getApplicationNumber());
        });
    }
}