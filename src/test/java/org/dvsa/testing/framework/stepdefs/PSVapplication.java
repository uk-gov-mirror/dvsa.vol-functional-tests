package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Journeys.APIJourneySteps;

public class PSVapplication implements En {
    private World world;

    public PSVapplication(World world) {
        world.APIJourneySteps = new APIJourneySteps(world);

        Given("^I have applied for a \"([^\"]*)\" \"([^\"]*)\" licence$", (String arg0, String arg1) -> {
            world.createLicence.setOperatorType(arg0);
            world.createLicence.setLicenceType(arg1);
            if (world.createLicence.getApplicationNumber() == null) {
                world.APIJourneySteps.createApplication();
            }
        });

        Given("^I have a \"([^\"]*)\" \"([^\"]*)\" application which is under consideration$", (String arg0, String arg1) -> {
            world.createLicence.setOperatorType(arg0);
            world.createLicence.setLicenceType(arg1);
            if (world.createLicence.getApplicationNumber() == null) {
                world.APIJourneySteps.createApplication();
            }
        });
    }
}
