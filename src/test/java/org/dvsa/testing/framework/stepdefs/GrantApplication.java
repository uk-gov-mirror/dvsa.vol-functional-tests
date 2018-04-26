package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;

import static org.dvsa.testing.framework.stepdefs.Utils.InternalGenericUtils.payFees;

public class GrantApplication implements En {
    public GrantApplication() {
        GrantApplicationAPI grantApp = new GrantApplicationAPI();
        CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();

        When("^I pay fees$", () -> {
            payFees(grantApp, goodsApp);
        });
        Given("^I have an application which is under consideration$", () -> {
            if (goodsApp.getApplicationNumber() == null) {
                goodsApp.createGoodsApp();
            }
        });
        Then("^the application should be granted$", () -> {
            grantApp.payGrantFees(goodsApp.getOrganisationId(),goodsApp.getApplicationNumber());
        });
    }
}