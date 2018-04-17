package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI.getApplicationNumber;

public class GrantApplication implements En {
    public GrantApplication() {
        GrantApplicationAPI grantApp = new GrantApplicationAPI();

        When("^I pay fees$", () -> {
            grantApp.createOverview();
            grantApp.getOutstandingFees();
            grantApp.payOutstandingFees();
            grantApp.grant();


        });
        Given("^I have an application which is under consideration$", () -> {
            if (getApplicationNumber() == null) {
                CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();
                goodsApp.createGoodsApp();
            }
        });
        Then("^the application should be granted$", () -> {
            grantApp.payGrantFees();
        });
    }
}
