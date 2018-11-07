package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Journeys.APIJourneySteps;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.openqa.selenium.By;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TMDetails extends BasePage implements En {

    public TMDetails(World world) {
        world.APIJourneySteps = new APIJourneySteps(world);
        world.genericUtils = new GenericUtils(world);
        Given("^I have a new application$", () -> {
            world.APIJourneySteps.createPartialApplication();
        });
        And("^the transport manager is the operator$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin(world.createLicence.getLoginId(),world.createLicence.getEmailAddress());
            clickByLinkText(world.createLicence.getApplicationNumber());
            world.UIJourneySteps.nominateOperatorUserAsTransportManager(1);
        });
        Then("^the optional wording should not be displayed in the \"([^\"]*)\" section$", (String section) -> {
            assertTrue(Browser.navigate().findElements(By.xpath(String.format("//*[@id=\"%s\"]/div/div", section.replace(" ", "")))).stream().noneMatch(x -> x.getText().contains("(optional)")));
        });
        And("^the \"([^\"]*)\" button should not be displayed$", (String button) -> {
            assertTrue(Browser.navigate().findElements(By.xpath("//button")).stream().noneMatch(x -> x.getText().contains(button)));
        });
        When("^I click the yes radio button for the \"([^\"]*)\" section$", (String section) -> {
            world.genericUtils.findSelectAllRadioButtonsByValue("Y");
        });
        Then("^the \"([^\"]*)\" button should be displayed$", (String button) -> {
            assertFalse(Browser.navigate().findElements(By.xpath("//button")).stream().noneMatch(x -> x.getText().contains(button)));
        });
        When("^I click on the \"([^\"]*)\" button$", (String button) -> {
            world.genericUtils.findSelectAllRadioButtonsByValue("Y");
            click(String.format("//*[@data-label=\"%s\"]", button), SelectorType.XPATH);
        });
        Then("^I should be taken to the \"([^\"]*)\" page$", (String page) -> {
            assertTrue(Browser.navigate().getCurrentUrl().contains(page));
        });
        And("^page title \"([^\"]*)\" should be displayed on page$", (String arg0) -> {
            isTextPresent(arg0,10);
            clickByLinkText("Back");
        });
        When("^the users attempts to save without entering any data$", () -> {
            click("form-actions[submit]",SelectorType.ID);
        });
        Then("^a validation message should be displayed$", () -> {
            assertTrue(isElementPresent("validationSummary",SelectorType.ID));
        });
        When("^I click the no radio button for the \"([^\"]*)\" question$", (String arg0) -> {
            click("//*[@id=\"responsibilities\"]//input[@value='N']", SelectorType.XPATH);

        });
        Then("^the guidance text should be displayed$", () -> {
            String notDirectorText = findElement("//*[@id='responsibilities']/fieldset[2]/div",SelectorType.XPATH,10).getText();
            assertTrue(isTextPresent(notDirectorText,10));
        });
    }
}