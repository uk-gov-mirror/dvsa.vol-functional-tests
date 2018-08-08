package org.dvsa.testing.framework.stepdefs;

import activesupport.string.Str;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Journeys.JourneySteps;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddDirectorVariation extends BasePage implements En {

    private String firstName = String.format("Director%s", Str.randomWord(4));
    private String lastName = "LastName";
    private World world;

    public AddDirectorVariation(World world) {
        this.world = world;
        world.journeySteps = new JourneySteps(world);

        When("^i add a new person$", () -> {
            world.journeySteps.externalUserLogin();
            world.journeySteps.addPerson(firstName, lastName);
        });
        Then("^a new director should be added to my licence$", () -> {
            waitForTextToBePresent("Directors");
            List<WebElement> director = Browser.navigate().findElements(By.xpath("//*/tbody/tr[*]/td[1]/input"));
            long directors = director.size();
            assertEquals(directors, 2);
            assertTrue(director.stream().anyMatch(d -> d.getAttribute("value").contains(firstName)));
        });
        And("^a non urgent task is created in internal$", () -> {
            assertEquals(world.journeySteps.navigateToInternalTask(world), "");
        });
        When("^i enter \"([^\"]*)\" previous convictions details question$", (String arg0) -> {
            world.genericUtils.selectAllRadioButtons(arg0);
            if(arg0.equals("Yes")){
                click("add",SelectorType.ID);
                world.journeySteps.addPreviousConviction();
            }
            clickByName("form-actions[saveAndContinue]");
        });
        And("^an urgent task is created in internal$", () -> {
            assertEquals(world.journeySteps.navigateToInternalTask(world), "selected");
        });
        And("^i enter \"([^\"]*)\" to financial details question$", (String arg0) -> {
            world.genericUtils.selectAllRadioButtons(arg0);
            if(arg0.equals("Yes")){
                enterText("data[insolvencyDetails]", Str.randomWord(150),SelectorType.ID);
            }
            clickByName("form-actions[saveAndContinue]");
        });
    }
}