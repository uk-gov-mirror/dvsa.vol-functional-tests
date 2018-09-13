package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Journeys.APIJourneySteps;
import org.dvsa.testing.lib.pages.BasePage;
import org.openqa.selenium.By;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TMDetails extends BasePage implements En {

    public TMDetails(World world) {
        Given("^I have a new application$", () -> {
            world.APIJourneySteps = new APIJourneySteps(world);
            world.APIJourneySteps.createPartialApplication();
        });
        And("^the transport manager is the operator$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin();
            clickByLinkText(world.createLicence.getApplicationNumber());
            world.UIJourneySteps.addExistingPersonAsTransportManager();
        });
        Then("^the optional wording should not be displayed in the \"([^\"]*)\" section$", (String section) -> {
           assertTrue(Browser.navigate().findElements(By.xpath(String.format("//*[@id=\"%s\"]/div/div",section.replace(" ","")))).stream().noneMatch(x -> x.getText().contains("(optional)")));
        });
        And("^the \"([^\"]*)\" button should not be displayed$", (String button) -> {
            assertTrue(Browser.navigate().findElements(By.xpath("//button")).stream().noneMatch(x -> x.getText().contains(button)));
        });
        When("^I click the yes radio button for the \"([^\"]*)\" section$", (String section) -> {
            world.genericUtils.selectAllExternalRadioButtons("Y");
        });
        Then("^the \"([^\"]*)\" button should be displayed$", (String button) -> {
            assertFalse(Browser.navigate().findElements(By.xpath("//button")).stream().noneMatch(x -> x.getText().contains(button)));
        });
        When("^I click on the \"([^\"]*)\" button$", (String button) -> {
            world.genericUtils.selectAllExternalRadioButtons("Y");
            Browser.navigate().findElements(By.xpath("//button")).stream().filter(x -> x.getText().equals(button)).forEach(x -> x.click());
        });
        Then("^I should be taken to the \"([^\"]*)\" page$", (String page) -> {
           assertTrue(Browser.navigate().getCurrentUrl().contains(page));
           clickByLinkText("Back");
        });
    }
}