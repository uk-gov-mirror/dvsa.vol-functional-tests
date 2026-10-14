package org.dvsa.testing.framework.stepdefs.vol;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.Journeys.licence.AddressesJourney;
import org.dvsa.testing.framework.Utils.Generic.UniversalActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressChanges {
    private final World world;

    private static final String ADDRESS_LINE_1_ERROR = "Enter the first line of your address";
    private static final String TOWN_ERROR = "Enter your town/city";
    private static final String POSTCODE_ERROR = "Postcode: Postcode is required for UK addresses";
    private static final String CONTACT_NUMBER_ERROR = "Contact number is missing";

    public AddressChanges(World world) {
        this.world = world;
    }

    @When("i clear the mandatory correspondence address fields and save")
    public void iClearTheMandatoryCorrespondenceAddressFieldsAndSave() {
        world.addressesJourney.clearMandatoryCorrespondenceFieldsAndSave();
    }

    @Then("the addresses page should display the mandatory field validation errors")
    public void theAddressesPageShouldDisplayTheMandatoryFieldValidationErrors() {
        assertTrue(world.addressesJourney.isValidationSummaryDisplayed(),
                "Addresses page should display the 'There is a problem' validation summary");

        List<String> summary = world.addressesJourney.getValidationSummaryMessages();
        for (String expected : List.of(ADDRESS_LINE_1_ERROR, TOWN_ERROR, POSTCODE_ERROR, CONTACT_NUMBER_ERROR)) {
            assertTrue(summary.contains(expected),
                    "Validation summary should contain '" + expected + "' but was: " + summary);
        }

        assertEquals(ADDRESS_LINE_1_ERROR,
                world.addressesJourney.getInlineErrorForField(AddressesJourney.CORRESPONDENCE_ADDRESS_LINE_1));
        assertEquals(TOWN_ERROR,
                world.addressesJourney.getInlineErrorForField(AddressesJourney.CORRESPONDENCE_TOWN));
        assertEquals(POSTCODE_ERROR,
                world.addressesJourney.getInlineErrorForField(AddressesJourney.CORRESPONDENCE_POSTCODE));
        assertEquals(CONTACT_NUMBER_ERROR,
                world.addressesJourney.getInlineErrorForField(AddressesJourney.PRIMARY_CONTACT_NUMBER));
    }

    @And("the original correspondence address should not have been changed")
    public void theOriginalCorrespondenceAddressShouldNotHaveBeenChanged() {
        UniversalActions.clickHome();
        world.addressesJourney.openLicenceAddressesPage();
        assertEquals(world.addressesJourney.getOriginalCorrespondenceAddressLine1(),
                world.addressesJourney.getFieldValue(AddressesJourney.CORRESPONDENCE_ADDRESS_LINE_1),
                "Correspondence address line 1 should be unchanged after a failed save");
        assertEquals(world.addressesJourney.getOriginalCorrespondencePostcode(),
                world.addressesJourney.getFieldValue(AddressesJourney.CORRESPONDENCE_POSTCODE),
                "Correspondence postcode should be unchanged after a failed save");
    }
}
