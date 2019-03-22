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

        });
    }
}
