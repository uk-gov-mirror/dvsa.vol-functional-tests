package org.dvsa.testing.framework.stepdefs;

import activesupport.MissingRequiredArgument;
import activesupport.database.DBUnit;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import java.sql.ResultSet;

import static junit.framework.TestCase.assertEquals;
import static org.dvsa.testing.framework.stepdefs.RemoveTM.alertHeaderValue;

public class GenerateLastTMLetter extends BasePage implements En {

    private World world;

    public GenerateLastTMLetter(World world) throws MissingRequiredArgument {
        this.world = world;

        Given("^i have a valid \"([^\"]*)\" licence$", (String arg0) -> {
            world.genericUtils = new GenericUtils(world);
            world.createLicence.setOperatorType(arg0);
            world.genericUtils.createApplication();
            if(String.valueOf(arg0).equals("public")){
                world.genericUtils.payPsvFeesAndGrantLicence();
            }
            else {
                world.genericUtils.payGoodsFeesAndGrantLicence();
            }
        });
        Then("^a flag should be set in the DB$", () -> {
            ResultSet resultSet = DBUnit.checkResult(String.format("SELECT opt_out_tm_letter FROM OLCS_RDS_OLCSDB.licence\n" +
                    "WHERE lic_no='%s';",world.createLicence.getLicenceNumber()));
            if(resultSet.next()) {
                int columnValue = Integer.parseInt(resultSet.getString("transport_manager_id"));
                assertEquals(1, columnValue);
            }
            //reset licence to valid to be used by next scenario
            world.genericUtils.updateLicenceStatus(world.createLicence.getLicenceId(),"reset-to-valid");

        });
        Given("^the licence status is \"([^\"]*)\"$", (String arg0) -> {
            world.genericUtils.updateLicenceStatus(world.createLicence.getLicenceId(), arg0);
        });
        And("^the user confirms they want to send letter$", () -> {
            waitForTextToBePresent(alertHeaderValue);
            click("//*[@class='form-control form-control--radio form-control--inline'][1]");
            click("//*[@id='form-actions[submit]']", SelectorType.XPATH);
        });
    }

    @Before
    public static void setup() {
        Hooks.setup();
    }

    @After
    public void tearDown(){
        Hooks hooks = new Hooks();
        hooks.attach();
        Hooks.teardown();
    }
}