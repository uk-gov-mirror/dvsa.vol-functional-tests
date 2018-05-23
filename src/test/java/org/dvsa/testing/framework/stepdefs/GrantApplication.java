package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;

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
            world.genericUtils.grantLicence().payGrantFees(world.genericUtils.createApp().getOrganisationId(),world.genericUtils.createApp().getApplicationNumber());
        });
        Given("^I have a \"([^\"]*)\" application which is under consideration$", (String arg0) -> {
            licenceType = arg0;
            if (world.createLicence.getApplicationNumber() == null) {
                world.genericUtils.createApplication(licenceType);
            }
        });
    }
}