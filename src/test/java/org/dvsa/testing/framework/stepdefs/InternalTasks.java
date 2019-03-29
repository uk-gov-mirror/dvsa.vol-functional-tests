package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;

public class InternalTasks extends BasePage implements En {

    public InternalTasks(World world) {
        Then("^an urgent GV(\\d+)A task is created in internal$", (Integer arg0) -> {
            world.UIJourneySteps.navigateToInternalTask();
            isTextPresent("GV80A Application",10);
        });
    }
}
