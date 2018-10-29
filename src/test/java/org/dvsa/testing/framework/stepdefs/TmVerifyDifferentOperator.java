package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TmVerifyDifferentOperator extends BasePage implements En {

    public TmVerifyDifferentOperator(World world) {
        Given("^the operator is on check your answers page$", () -> {
            world.APIJourneySteps.createPartialApplication();
            world.createLicence.addTransportManager();
            world.createLicence.submitTransport();
            world.createLicence.addTmResponsibilities();
            world.UIJourneySteps.navigateToTMExternalUserLogin();
            clickByLinkText("View details");
            clickByLinkText("change your details");
            findSelectAllRadioButtonsByValue("N");
            click(nameAttribute("button", "form-actions[submit]"), SelectorType.CSS);
        });

        Then("^the correct data should be displayed$", () -> {
            String dateUI = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            String townUI = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][3]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);

            String tMType;

            if (world.createLicence.getTmType() == "tm_t_i") {
                tMType = "Internal";
            } else {
                tMType = "External";
            }

            Assert.assertTrue(isTextPresent(world.createLicence.getForeName() + " " + world.createLicence.getFamilyName(), 30));
            Assert.assertTrue(isTextPresent(dateUI, 30));
            Assert.assertTrue(isTextPresent(townUI, 30));
            Assert.assertTrue(isTextPresent(world.createLicence.getEmailAddress(), 30));
            Assert.assertTrue(isTextPresent("No certificates attached", 30));
            Assert.assertTrue(isTextPresent(world.createLicence.getAddressLine1(), 30));
            Assert.assertTrue(isTextPresent(world.createLicence.getAddressLine1(), 30));
            Assert.assertTrue(isTextPresent(world.createLicence.getIsOwner(), 30));
            Assert.assertTrue(isTextPresent(tMType, 30));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][3]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][4]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][5]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][6]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][7]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
        });
        And("^operator makes a change to the place of birth, address and email$", () -> {
            clickByLinkText("Change");

            enterField(nameAttribute("input", "details[birthPlace]"), "changeTown");
            enterField(nameAttribute("input", "details[emailAddress]"), "changeTest@gmail.com");
            enterField(nameAttribute("input", "homeAddress[addressLine1]"), "56 Address Change");
            click(nameAttribute("button", "form-actions[submit]"));
            assertTrue(isTextPresent("changeTown", 30));
            assertTrue(isTextPresent("changeTest@gmail.com", 30));
            assertTrue(isTextPresent("56 Address Change", 30));
        });
    }
}