package org.dvsa.testing.framework.stepdefs.vol;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.dvsa.testing.framework.Injectors.World;

public class AddressChanges {
    private final World world;

    public AddressChanges(World world) {
        this.world = world;
    }

    @When("i clear the mandatory correspondence address fields and save")
    public void iClearTheMandatoryCorrespondenceAddressFieldsAndSave() {
        world.addressesJourney.clearMandatoryCorrespondenceFieldsAndSave();
    }

    @Then("the addresses page should display the mandatory field validation errors")
    public void theAddressesPageShouldDisplayTheMandatoryFieldValidationErrors() {
        world.addressesJourney.verifyMandatoryFieldValidationErrorsAreDisplayed();
    }

    @And("the original correspondence address should not have been changed")
    public void theOriginalCorrespondenceAddressShouldNotHaveBeenChanged() {
        world.addressesJourney.verifyOriginalCorrespondenceAddressIsUnchanged();
    }
}
