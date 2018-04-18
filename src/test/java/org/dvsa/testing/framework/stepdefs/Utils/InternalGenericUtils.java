package org.dvsa.testing.framework.stepdefs.Utils;

import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

public class InternalGenericUtils extends BasePage {

    public static void generateLetter() throws InterruptedException {
        clickByLinkText("Docs & attachments");
        isTextPresent("1 Docs & attachments", 60);
        clickByName("New letter");
        findElement("//*[@id='modal-title']", SelectorType.XPATH, 600);
        waitAndSelectByIndex("//*[@id='category']", 1);
        waitAndSelectByIndex("//*[@id='documentSubCategory']", 1);
        waitAndSelectByIndex("//*[@id='documentTemplate']", 5);
        waitAndClick("//*[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public static void payFees(GrantApplicationAPI grantApp, CreateInterimGoodsLicenceAPI goodsApp) {
        grantApp.createOverview(goodsApp.getApplicationNumber());
        grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
        grantApp.payOutstandingFees(goodsApp.getOrganisationId(),goodsApp.getApplicationNumber());
        grantApp.grant(goodsApp.getApplicationNumber());
    }
}