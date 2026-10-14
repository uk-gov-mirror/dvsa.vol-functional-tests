package org.dvsa.testing.framework.Journeys.licence;

import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.Utils.Generic.UniversalActions;
import org.dvsa.testing.framework.enums.SelfServeSection;
import org.dvsa.testing.framework.pageObjects.BasePage;
import org.dvsa.testing.framework.pageObjects.enums.SelectorType;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressesJourney extends BasePage {
    private final World world;

    // Field ids are duplicated across the correspondence and consultant sections, so target by name
    private static final String CORRESPONDENCE_ADDRESS_LINE_1 = "correspondence_address[addressLine1]";
    private static final String CORRESPONDENCE_TOWN = "correspondence_address[town]";
    private static final String CORRESPONDENCE_POSTCODE = "correspondence_address[postcode]";
    private static final String PRIMARY_CONTACT_NUMBER = "contact[phone_primary]";

    private static final String VALIDATION_SUMMARY = "//*[@id='validationSummary']";
    private static final String VALIDATION_SUMMARY_ITEMS = "//*[@id='validationSummary']//li[@class='validation-summary__item']/a";
    private static final String INPUT_BY_NAME = "//input[@name='%s']";
    private static final String INLINE_ERROR_BY_INPUT_NAME = "//input[@name='%s']/preceding-sibling::p[contains(@class,'govuk-error-message')]";

    private static final String ADDRESS_LINE_1_ERROR = "Enter the first line of your address";
    private static final String TOWN_ERROR = "Enter your town/city";
    private static final String POSTCODE_ERROR = "Postcode: Postcode is required for UK addresses";
    private static final String CONTACT_NUMBER_ERROR = "Contact number is missing";

    private String originalCorrespondenceAddressLine1;
    private String originalCorrespondencePostcode;

    public AddressesJourney(World world) {
        this.world = world;
    }

    public void clearMandatoryCorrespondenceFieldsAndSave() {
        openLicenceAddressesPage();
        originalCorrespondenceAddressLine1 = getFieldValue(CORRESPONDENCE_ADDRESS_LINE_1);
        originalCorrespondencePostcode = getFieldValue(CORRESPONDENCE_POSTCODE);

        clearField(CORRESPONDENCE_ADDRESS_LINE_1);
        clearField(CORRESPONDENCE_TOWN);
        clearField(CORRESPONDENCE_POSTCODE);
        clearField(PRIMARY_CONTACT_NUMBER);
        UniversalActions.clickSaveAndReturn();
    }

    public void verifyMandatoryFieldValidationErrorsAreDisplayed() {
        waitForElementToBePresent(VALIDATION_SUMMARY);
        assertTrue(isElementPresent(VALIDATION_SUMMARY, SelectorType.XPATH),
                "Addresses page should display the 'There is a problem' validation summary");

        List<String> summary = getValidationSummaryMessages();
        for (String expected : List.of(ADDRESS_LINE_1_ERROR, TOWN_ERROR, POSTCODE_ERROR, CONTACT_NUMBER_ERROR)) {
            assertTrue(summary.contains(expected),
                    "Validation summary should contain '" + expected + "' but was: " + summary);
        }

        assertEquals(ADDRESS_LINE_1_ERROR, getInlineError(CORRESPONDENCE_ADDRESS_LINE_1));
        assertEquals(TOWN_ERROR, getInlineError(CORRESPONDENCE_TOWN));
        assertEquals(POSTCODE_ERROR, getInlineError(CORRESPONDENCE_POSTCODE));
        assertEquals(CONTACT_NUMBER_ERROR, getInlineError(PRIMARY_CONTACT_NUMBER));
    }

    public void verifyOriginalCorrespondenceAddressIsUnchanged() {
        UniversalActions.clickHome();
        openLicenceAddressesPage();
        assertEquals(originalCorrespondenceAddressLine1, getFieldValue(CORRESPONDENCE_ADDRESS_LINE_1),
                "Correspondence address line 1 should be unchanged after a failed save");
        assertEquals(originalCorrespondencePostcode, getFieldValue(CORRESPONDENCE_POSTCODE),
                "Correspondence postcode should be unchanged after a failed save");
    }

    private void openLicenceAddressesPage() {
        world.selfServeNavigation.navigateToPage("licence", SelfServeSection.ADDRESSES);
    }

    private List<String> getValidationSummaryMessages() {
        return findElements(VALIDATION_SUMMARY_ITEMS, SelectorType.XPATH).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private String getInlineError(String fieldName) {
        String inlineError = String.format(INLINE_ERROR_BY_INPUT_NAME, fieldName);
        if (!isElementPresent(inlineError, SelectorType.XPATH)) {
            return "";
        }
        return getText(inlineError, SelectorType.XPATH).replace("Error:", "").trim();
    }

    private String getFieldValue(String fieldName) {
        return getValue(String.format(INPUT_BY_NAME, fieldName), SelectorType.XPATH);
    }

    private void clearField(String fieldName) {
        clearAndEnter(String.format(INPUT_BY_NAME, fieldName), SelectorType.XPATH, "");
    }
}
