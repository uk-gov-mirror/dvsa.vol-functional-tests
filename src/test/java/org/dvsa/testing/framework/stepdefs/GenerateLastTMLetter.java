package org.dvsa.testing.framework.stepdefs;

import activesupport.MissingRequiredArgument;
import activesupport.database.DBUnit;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;

import java.sql.ResultSet;

import static junit.framework.TestCase.assertEquals;

public class GenerateLastTMLetter implements En {

    private static String operatorType = System.getProperty("operatorType");
    private World world;

    public GenerateLastTMLetter(World world) throws MissingRequiredArgument {
        this.world = world;

        Given("^i have a valid \"([^\"]*)\" licence$", (String arg0) -> {
            world.genericUtils = new GenericUtils(world);
            world.genericUtils.createApplication(arg0);
            if(String.valueOf(arg0).equals("public")){
                world.genericUtils.payPsvFeesAndGrantLicence();
            }
            else {
                world.genericUtils.payGoodsFeesAndGrantLicence();
            }
        });
        Then("^a flag should be set in the DB$", () -> {
            ResultSet resultSet = DBUnit.checkResult("SELECT transport_manager_id FROM OLCS_RDS_OLCSDB.note where id = 1;");
            if(resultSet.next()) {
                int columnValue = Integer.parseInt(resultSet.getString("transport_manager_id"));
                assertEquals(14, columnValue);
            }
            //reset licence to valid to be used by next scenario
            world.genericUtils.updateLicenceStatus(world.createLicence.getLicenceId(),"reset-to-valid");

        });
        Given("^the licence status is \"([^\"]*)\"$", (String arg0) -> {
            world.genericUtils.updateLicenceStatus(world.createLicence.getLicenceId(), arg0);
        });
    }
}