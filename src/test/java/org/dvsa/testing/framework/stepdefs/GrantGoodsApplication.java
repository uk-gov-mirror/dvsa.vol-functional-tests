package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;

import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.payGoodsFeesAndGrantLicence;

public class GrantGoodsApplication implements En {
    public GrantGoodsApplication() throws Exception{
        GrantLicenceAPI grantApp = new GrantLicenceAPI();
        CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();

        When("^I pay fees$", () -> {
            payGoodsFeesAndGrantLicence(grantApp, goodsApp);
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