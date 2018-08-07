package org.dvsa.testing.framework.stepdefs;

import activesupport.string.Str;
import cucumber.api.java8.En;
import io.restassured.response.ValidatableResponse;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddDirectorVariation extends BasePage implements En {
    private ValidatableResponse apiResponse;
    private String firstName = String.format("Director%s", Str.randomWord(4));
    private String lastName = "LastName";

    public AddDirectorVariation(World world) {
        When("^i add a new person$", () -> {
            world.genericUtils.externalUserLogin();
            world.genericUtils.addPerson(firstName, lastName);
        });
        And("^i enter financial details$", () -> {
            world.genericUtils.selectAllRadioButtons("No");
            clickByName("form-actions[saveAndContinue]");
        });
        And("^i enter previous convictions details$", () -> {
            world.genericUtils.selectAllRadioButtons("No");
            clickByName("form-actions[saveAndContinue]");
        });
        Then("^a new director should be added to my licence$", () -> {
            waitForTextToBePresent("Directors");
            List<WebElement> director = Browser.getDriver().findElements(By.xpath("//*/tbody/tr[*]/td[1]/input"));
            long directors = director.size();
            assertEquals(directors, 2);
            assertTrue(director.stream().anyMatch(d -> d.getAttribute("value").contains(firstName)));
        });
        And("^a non urgent task is created in internal$", () -> {
            world.genericUtils.internalAdminUserLogin();
            world.genericUtils.searchAndViewApplication();
            clickByLinkText("Processing");
            clickByLinkText("Add director(s)");
            waitForTextToBePresent("Linked to");
            String tickBoxValue = findElement("//div[4]/label", SelectorType.XPATH, 30).getAttribute("class");
            assertEquals(tickBoxValue, "");
        });
    }
}