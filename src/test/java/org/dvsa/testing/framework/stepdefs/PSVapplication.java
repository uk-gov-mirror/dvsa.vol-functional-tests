package org.dvsa.testing.framework.stepdefs;

import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.runner.Hooks;

public class PSVapplication implements En {
    private World world;

    public PSVapplication(World world) {

        Given("^I have applied for a \"([^\"]*)\" \"([^\"]*)\" licence$", (String arg0, String arg1) -> {
            world.createLicence.setOperatorType(arg0);
            world.createLicence.setLicenceType(arg1);
            if (world.createLicence.getApplicationNumber() == null) {
                world.genericUtils.createApplication();
            }
        });

        Given("^I have a \"([^\"]*)\" \"([^\"]*)\" application which is under consideration$", (String arg0, String arg1) -> {
            world.createLicence.setOperatorType(arg0);
            world.createLicence.setLicenceType(arg1);
            if (world.createLicence.getApplicationNumber() == null) {
                world.genericUtils.createApplication();
            }
        });

        After(new String[]{"@INT"}, (Scenario scenario) -> {
            String[] args = new String[0];
            Hooks hooks = new Hooks();
            hooks.main(args);
        });
    }
}
