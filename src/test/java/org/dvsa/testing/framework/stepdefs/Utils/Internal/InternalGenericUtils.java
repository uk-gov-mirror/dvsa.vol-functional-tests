package org.dvsa.testing.framework.stepdefs.Utils.Internal;

import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimPsvLicenceAPI;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

public class InternalGenericUtils extends BasePage {

    public static void generateLetter() {
        clickByLinkText("Docs & attachments");
        isTextPresent("1 Docs & attachments", 60);
        clickByName("New letter");
        findElement("//*[@id='modal-title']", SelectorType.XPATH, 600);


        waitAndSelectByIndex("Generate letter", "//*[@id='category']", SelectorType.XPATH, 1);
        waitAndSelectByIndex("Generate letter", "//*[@id='documentSubCategory']", SelectorType.XPATH, 1);
        waitAndSelectByIndex("Generate letter", "//*[@id='documentTemplate']", SelectorType.XPATH, 5);
        waitAndClick("//*[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public static void payGoodsFees(GrantApplicationAPI grantApp, CreateInterimGoodsLicenceAPI goodsApp) {
        grantApp.createOverview(goodsApp.getApplicationNumber());
        grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
        grantApp.payOutstandingFees(goodsApp.getOrganisationId(), goodsApp.getApplicationNumber());
        grantApp.grant(goodsApp.getApplicationNumber());
    }

    public static void payPsvFees(GrantApplicationAPI grantApp, CreateInterimPsvLicenceAPI psvApp) {
        grantApp.createOverview(psvApp.getApplicationNumber());
        grantApp.getOutstandingFees(psvApp.getApplicationNumber());
        grantApp.payOutstandingFees(psvApp.getOrganisationId(), psvApp.getApplicationNumber());
        grantApp.grant(psvApp.getApplicationNumber());
    }
}