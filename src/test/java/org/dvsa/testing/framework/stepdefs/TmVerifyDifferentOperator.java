package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
        Then("^the correct headings, data and links should be displayed for Your details section$", () -> {
            String country;
            if (world.createLicence.getCountryCode() == "GB") {
                country = "United Kingdom";
            } else
            {
                country = "Northern Ireland";
            }

            String now = world.createLicence.getLicenceNumber();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dateTime = LocalDate.parse(now).format(formatter);
            System.out.println("After : " + dateTime);

            Assert.assertEquals(world.createLicence.getForeName()+ " " + world.createLicence.getFamilyName(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(dateTime,getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(dateTime, getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][3]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getEmailAddress(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][4]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals("No certificates attached", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][5]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
          //  Assert.assertEquals(world.createLicence.getEmailAddress(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][6]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
          //  Assert.assertEquals(world.createLicence.getAddressLine1()+world.createLicence.getPostcode() + " " + world.createLicence.getTown() + country, getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][6]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
          //  Assert.assertEquals(world.createLicence.getAddressLine1()+world.createLicence.getPostcode() + world.createLicence.getTown() +" " + country, getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][7]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
        });


        Then("^the correct headings, data and links should be displayed for Responsibilities section$", () -> {
            String tMType;

            if (world.createLicence.getTmType() == "tm_t_i") {
                tMType = "Internal";
            } else {
                tMType = "External";
            }

            Assert.assertEquals(world.createLicence.getIsOwner(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][2]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(tMType, getText("//*[@class='app-check-your-answers app-check-your-answers--long'][2]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
        });

        Then("^the correct headings, data and links should be displayed for Hours per week section$", () -> {
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][3]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][4]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][5]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][6]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals(world.createLicence.getHours(), getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][7]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));

        });
        Then("^the correct headings, data and links should be displayed for Other licences section$", () -> {
            Assert.assertEquals("None added", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][4]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));

        });
        Then("^the correct headings, data and links should be displayed for Additional information section$", () -> {
            Assert.assertEquals("None added", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][5]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));
            Assert.assertEquals("0", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][5]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));

        });
        Then("^the correct headings, data and links should be displayed for Other employment section$", () -> {
            Assert.assertEquals("None added", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][6]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));

        });
        Then("^the correct headings, data and links should be displayed for Convictions & Penalties section$", () -> {
            Assert.assertEquals("None added", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][7]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));

        });
        Then("^the correct headings, data and links should be displayed for Revoked, curtailed or suspended licences section$", () -> {
            Assert.assertEquals("None added", getText("//*[@class='app-check-your-answers app-check-your-answers--long'][8]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH));

        });
    }
}