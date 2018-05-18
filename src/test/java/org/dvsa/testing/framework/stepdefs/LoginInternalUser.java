package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.pages.BasePage;

public class LoginInternalUser extends BasePage implements En {

    private CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();
    private GenericUtils genericUtils = new GenericUtils();

    public LoginInternalUser() throws Exception {

        Given("^I have logged in to internal$", () -> {
            if (goodsApp.getApplicationNumber() == null) {
                goodsApp.createAndSubmitGoodsApp();
            }
            genericUtils.internalUserLogin();
        });

        And("^I have an internal application$", () -> {
            selectValueFromDropDown("//select[@id='search-select']", SelectorType.XPATH, "Applications");
            do {
                SearchNavBar.search(goodsApp.getApplicationNumber());
            } while (!isLinkPresent(goodsApp.getApplicationNumber(), 60));
            clickByLinkText(goodsApp.getApplicationNumber());
            if (isLinkPresent("Interim", 60))
                clickByLinkText("Interim ");
        });
    }
}