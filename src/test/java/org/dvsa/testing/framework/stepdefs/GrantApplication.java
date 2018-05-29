package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import enums.OperatorType;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.runner.Hooks;

public class GrantApplication implements En {

    private World world;
    private static String licenceType;

    public GrantApplication(World world) throws Exception{
        this.world = world;
//        world.genericUtils = new GenericUtils(world);

        When("^I pay fees$", () -> {
            if(String.valueOf(licenceType).equals("public")){
                world.genericUtils.payPsvFeesAndGrantLicence();
            }
            else {
                world.genericUtils.payGoodsFeesAndGrantLicence();
            }
        });
        Then("^the application should be granted$", () -> {
            world.genericUtils.grantLicence().payGrantFees();
        });
        Given("^I have a \"([^\"]*)\" application which is under consideration$", (String arg0) -> {
            world.createLicence.setOperatorType(arg0);
            if (world.createLicence.getApplicationNumber() == null) {
                world.genericUtils.createApplication();
            }
        });
    }

    @Before
    public static void setup() {
        Hooks hooks = new Hooks();
        hooks.setup();
    }

    @After
    public void tearDown(){
        Hooks hooks = new Hooks();
        hooks.attach();
    }
}