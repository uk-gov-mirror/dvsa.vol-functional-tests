package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertEquals;

public class RemoveLastDirector extends BasePage implements En {
    public RemoveLastDirector(World world) {

        And("^i remove a the last director$", () -> {
        world.UIJourneySteps.navigateToExternalUserLogin();
        world.UIJourneySteps.navigateToDirectorsPage();
        world.UIJourneySteps.removeDirector("1");
        });


        Then("^a task should be created in internal$", () -> {
          world.UIJourneySteps.navigateTointernalAdminUserLogin();
          world.UIJourneySteps.searchAndViewApplication();

        });

        Then("^a task is created in internal$", () -> {
            world.UIJourneySteps.navigateToInternalTask();
            clickByLinkText("Last director removed");
            waitForTextToBePresent("Linked to");
            String isSelected = findElement("//div[4]/label", SelectorType.XPATH, 30).getAttribute("class");
            assertEquals(isSelected, "");
        });
    }
}
