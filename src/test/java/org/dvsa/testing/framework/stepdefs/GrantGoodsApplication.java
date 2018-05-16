package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;

import static org.dvsa.testing.framework.stepdefs.Utils.Internal.InternalGenericUtils.payGoodsFees;

public class GrantGoodsApplication implements En {
    public GrantGoodsApplication() throws Exception{
        GrantApplicationAPI grantApp = new GrantApplicationAPI();
        CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();

        When("^I pay fees$", () -> {
            payGoodsFees(grantApp, goodsApp);
        });
        Given("^I have an application which is under consideration$", () -> {
            if (goodsApp.getApplicationNumber() == null) {
                goodsApp.createAndSubmitGoodsApp();
            }
        });
        Then("^the application should be granted$", () -> {
            grantApp.payGrantFees(goodsApp.getOrganisationId(),goodsApp.getApplicationNumber());
        });
    }
}