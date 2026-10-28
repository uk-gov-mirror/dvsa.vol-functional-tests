package org.dvsa.testing.framework.stepdefs.vol;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.hc.core5.http.HttpException;
import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.enums.SelfServeSection;

public class InternalUndertakingsAndConditionsChanges {
    private World world;

    public InternalUndertakingsAndConditionsChanges(World world) {
        this.world = world;
    }

    @When("I navigate to the undertakings and conditions page on internal")
    public void iNavigateToTheUndertakingsAndConditionsPageOnInternal() throws HttpException {
        world.internalNavigation.navigateToPage("licence", SelfServeSection.CONDITIONS_AND_UNDERTAKINGS);
    }

    @Then("the undertakings and conditions page should display on internal")
    public void theUndertakingsAndConditionsPageShouldDisplayOnInternal() {
        world.internalUndertakingsAndConditionsJourney.verifyUndertakingsPageDisplays();
    }

    @Then("the undertakings table should display")
    public void theUndertakingsTableShouldDisplay() {
        world.internalUndertakingsAndConditionsJourney.verifyUndertakingsTableExists();
    }

    @Then("the conditions table should display")
    public void theConditionsTableShouldDisplay() {
        world.internalUndertakingsAndConditionsJourney.verifyConditionsTableExists();
    }

    @Then("the undertaking {string} should be displayed")
    public void theUndertakingShouldBeDisplayed(String undertakingText) {
        world.internalUndertakingsAndConditionsJourney.verifyUndertakingTextIsPresent(undertakingText);
    }
}
