package org.dvsa.testing.framework.stepdefs;



import activesupport.MissingRequiredArgument;
import activesupport.aws.s3.S3;

import activesupport.driver.Browser;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import java.net.MalformedURLException;


public class CheckPresenceOfPDFs extends BasePage implements En {


    public CheckPresenceOfPDFs() throws MissingRequiredArgument {
        World world = new World();
        Browser browser = new Browser();
        String env = System.getProperty("env");
        String uri = String.format("https://ssap1.olcs.%s.nonprod.dvsa.aws/auth/login/", env);

        Given("^I have a valid \"([^\"]*)\" \"([^\"]*)\" licence$", (String arg0, String arg1) -> {
            world.genericUtils = new GenericUtils(world);
            world.createLicence.setOperatorType(arg0);
            if (arg1 =="NI") {
                world.createLicence.setNiFlag("Y");
            }
            world.genericUtils.createApplication();
            world.genericUtils.payFeesAndGrantLicence();
            world.genericUtils.grantLicence().payGrantFees();
            System.out.println("Licence: " + world.createLicence.getLicenceNumber());
            world.genericUtils.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
        });
        And("^I am on add Transport Manager Page$", () -> {
            clickByLinkText("Transport Managers");
            waitForTextToBePresent("change your licence");
            clickByLinkText("change your licence");
            waitAndClick("button[name='form-actions[submit]'", SelectorType.CSS);
        });


        Then("^I open TM(\\d+) Form$", (Integer arg0) -> {
            waitAndClick("//*[@id=\"add\"]", SelectorType.XPATH);
            click(nameAttribute("button", "data[addUser]"));
            click("//*[@class='form-control form-control--radio form-control--inline'][1]", SelectorType.XPATH);
        });

        Given("^I am on add Safety Inspectors Page$", () -> {
            clickByLinkText("Back");
            clickByLinkText("Back");
            clickByLinkText("Safety and compliance");
            click(nameAttribute("button","table[action]"), SelectorType.CSS);
        });

        Then("^I open Maintenance Form$", () -> {
           checkTextisPresent("sample contract");
        });

    }
}

