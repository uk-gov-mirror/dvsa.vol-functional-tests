package org.dvsa.testing.framework.stepdefs.vol;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.enums.SelfServeSection;

public class DiscsChanges {
    private World world;

    public DiscsChanges(World world) {
        this.world = world;
    }

    @When("i navigate to the discs page")
    public void iNavigateToTheDiscsPage() {
        world.selfServeNavigation.navigateToPage("licence", SelfServeSection.LICENCE_DISCS);
    }

    @Then("the discs page should display")
    public void theDiscsPageShouldDisplay() {
        world.discsJourney.verifyDiscsPageDisplays();
    }

    @Then("the disc {string} should be present on the discs page")
    public void theDiscShouldBePresentOnTheDiscsPage(String discNumber) {
        world.discsJourney.verifyDiscNumberIsPresent(discNumber);
    }

    @Then("the disc {string} should not be present on the discs page")
    public void theDiscShouldNotBePresentOnTheDiscsPage(String discNumber) {
        world.discsJourney.verifyDiscNumberIsNotPresent(discNumber);
    }

    @Then("the discs page should display {int} discs")
    public void theDiscsPageShouldDisplayNumberOfDiscs(int count) {
        world.discsJourney.verifyDiscsTableHasRows(count);
    }

    @Then("the discs page should display at least {int} discs")
    public void theDiscsPageShouldDisplayAtLeastNumberOfDiscs(int count) {
        world.discsJourney.verifyDiscsTableHasAtLeastRows(count);
    }

    @When("i remove the first disc")
    public void iRemoveTheFirstDisc() {
        world.discsJourney.removeFirstDisc();
    }

    @When("i replace the first disc")
    public void iReplaceTheFirstDisc() {
        world.discsJourney.replaceFirstDisc();
    }
}


