package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Journeys.APIJourneySteps;

public class PSVapplication implements En {
    private World world;

    public PSVapplication(World world) {
        world.APIJourneySteps = new APIJourneySteps(world);

        Given("^I have applied for a \"([^\"]*)\" \"([^\"]*)\" licence$", (String operator, String licenceType) -> {
            world.createLicence.setIsInterim("N");
            world.createLicence.setOperatorType(operator);
            world.createLicence.setLicenceType(licenceType);
            if(licenceType.equals("special_restricted") && (world.createLicence.getApplicationNumber() == null)){
                world.APIJourneySteps.createSpecialRestrictedLicence();
            }
            else if (world.createLicence.getApplicationNumber() == null) {
                world.APIJourneySteps.createAndSubmitApplication();
            }
        });

        Given("^I have a \"([^\"]*)\" \"([^\"]*)\" application which is under consideration$", (String arg0, String arg1) -> {
            world.createLicence.setIsInterim("Y");
            world.createLicence.setOperatorType(arg0);
            world.createLicence.setLicenceType(arg1);
            if (world.createLicence.getApplicationNumber() == null) {
                world.APIJourneySteps.createAndSubmitApplication();
            }
        });
    }
}
