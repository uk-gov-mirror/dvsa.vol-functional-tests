package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

public class UpgradeApplicationTask extends BasePage implements En {

    public UpgradeApplicationTask(World world) {
        And("^i choose to upgrade my licence$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin(world.createLicence.getLoginId(),world.createLicence.getEmailAddress());
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Type of licence");
            clickByLinkText("change your licence");
            waitForTextToBePresent("Applying to change a licence");
            waitAndClick("form-actions[submit]", SelectorType.ID);
            do {
//                javaScriptExecutor("location.reload(true)");
            }
            while (!isTextPresent("Type of licence",30));//condition
            waitAndClick("//*[contains(text(),'Standard International')]",SelectorType.XPATH);
            click("form-actions[save]",SelectorType.ID);
            waitForTextToBePresent("Apply to change a licence");
            clickByLinkText("Review and declarations");
            click("//*[@id='declarationsAndUndertakings[declarationConfirmation]']",SelectorType.XPATH);
            click("submit",SelectorType.ID);
            waitForTextToBePresent("Application overview");
            click("//*[@class='action--primary large']",SelectorType.XPATH);
        });
    }
}
