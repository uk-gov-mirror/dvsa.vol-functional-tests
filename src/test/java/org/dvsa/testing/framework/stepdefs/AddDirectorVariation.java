package org.dvsa.testing.framework.stepdefs;

import activesupport.string.Str;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddDirectorVariation extends BasePage implements En {
    private ValidatableResponse apiResponse;

    public AddDirectorVariation(World world) {
        When("^i add a new person$", () -> {
            world.createLicence.setTitle("title_mrs");
            world.createLicence.setForeName("Variation".concat(Str.randomWord(2)));
            world.createLicence.setFamilyName("Director".concat(Str.randomWord(2)));
            world.createLicence.setApplicationNumber(world.updateLicence.getVariationApplicationNumber());
            world.createLicence.addPartners();
        });
        And("^i enter financial details$", () -> {
            world.createLicence.addFinancialHistory();
        });
        And("^i enter previous convictions details$", () -> {
            world.createLicence.addConvictionsDetails();
        });
        Then("^a new director should be added to my licence$", () -> {
            apiResponse = world.updateLicence.grantVariation("grant-director-change");
            assertTrue(apiResponse.extract().response().asString().contains("identifier"));
            world.genericUtils.externalUserLogin();
            clickByLinkText(world.createLicence.getLicenceNumber());
            waitForTextToBePresent(world.createLicence.getLicenceNumber());
            clickByLinkText("Directors");

            List<WebElement> director = Browser.getDriver().findElements(By.xpath("//*/tbody/tr[*]/td[1]/input"));

            long directors = director.size();
            assertEquals(directors, 2);
            assertTrue(director.stream().anyMatch(d -> d.getAttribute("value").contains(world.createLicence.getForeName())));
        });
        And("^i create a director change variation$", () -> {
            world.updateLicence.createVariation("vtyp_director_change");
        });
    }
}