package org.dvsa.testing.framework.Journeys.licence;

import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.Utils.Generic.UniversalActions;
import org.dvsa.testing.framework.enums.SelfServeSection;
import org.dvsa.testing.framework.pageObjects.BasePage;
import org.dvsa.testing.framework.pageObjects.enums.SelectorType;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class AddressesJourney extends BasePage {
    private final World world;

    // Field ids are duplicated across the correspondence and consultant sections, so target by name
    public static final String CORRESPONDENCE_ADDRESS_LINE_1 = "correspondence_address[addressLine1]";
    public static final String CORRESPONDENCE_TOWN = "correspondence_address[town]";
    public static final String CORRESPONDENCE_POSTCODE = "correspondence_address[postcode]";
    public static final String PRIMARY_CONTACT_NUMBER = "contact[phone_primary]";

    public static final String VALIDATION_SUMMARY = "//*[@id='validationSummary']";
    public static final String VALIDATION_SUMMARY_ITEMS = "//*[@id='validationSummary']//li[@class='validation-summary__item']/a";

    private String originalCorrespondenceAddressLine1;
    private String originalCorrespondencePostcode;

    public AddressesJourney(World world) {
        this.world = world;
    }

    public String getOriginalCorrespondenceAddressLine1() {
        return originalCorrespondenceAddressLine1;
    }

    public String getOriginalCorrespondencePostcode() {
        return originalCorrespondencePostcode;
    }

    public void openLicenceAddressesPage() {
        world.selfServeNavigation.navigateToPage("licence", SelfServeSection.ADDRESSES);
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

    public boolean isValidationSummaryDisplayed() {
        waitForElementToBePresent(VALIDATION_SUMMARY);
        return isElementPresent(VALIDATION_SUMMARY, SelectorType.XPATH);
    }

    public List<String> getValidationSummaryMessages() {
        return findElements(VALIDATION_SUMMARY_ITEMS, SelectorType.XPATH).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public String getInlineErrorForField(String fieldName) {
        String inlineError = String.format(
                "//input[@name='%s']/preceding-sibling::p[contains(@class,'govuk-error-message')]", fieldName);
        if (!isElementPresent(inlineError, SelectorType.XPATH)) {
            return "";
        }
        return getText(inlineError, SelectorType.XPATH).replace("Error:", "").trim();
    }

    public String getFieldValue(String fieldName) {
        return getValue(String.format("//input[@name='%s']", fieldName), SelectorType.XPATH);
    }

    private void clearField(String fieldName) {
        clearAndEnter(String.format("//input[@name='%s']", fieldName), SelectorType.XPATH, "");
    }
}
