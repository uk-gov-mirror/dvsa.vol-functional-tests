package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class GrantApplication implements En {
    public GrantApplication() {
        GrantApplicationAPI grantApp = new GrantApplicationAPI();
        CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();


        When("^I pay fees$", () -> {
            grantApp.createOverview(goodsApp.getApplicationNumber());
            grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
            grantApp.payOutstandingFees(goodsApp.getOrganisationId(),goodsApp.getApplicationNumber());
            grantApp.grant(goodsApp.getApplicationNumber());


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
