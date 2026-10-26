package org.dvsa.testing.framework.Journeys.licence;

import org.dvsa.testing.framework.pageObjects.BasePage;
import org.dvsa.testing.framework.pageObjects.enums.SelectorType;
import org.dvsa.testing.framework.Injectors.World;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscsJourney extends BasePage {
    private World world;
    private String lastSelectedDiscNumber;

    public DiscsJourney(World world) {
        this.world = world;
    }

    public void verifyDiscsPageDisplays() {
        waitForElementToBePresent("//h1[contains(text(), 'Licence discs')]");
        waitForElementToBePresent("//table");
    }

    public void verifyDiscNumberIsPresent(String discNumber) {
        waitForElementToBePresent("//table");
        String discRowXpath = "//table//tr[contains(., '" + discNumber + "')]";
        assertTrue(findElements(discRowXpath, SelectorType.XPATH).size() > 0,
                "Disc " + discNumber + " should be present in table");
    }

    public void verifyDiscNumberIsNotPresent(String discNumber) {
        waitForElementToBePresent("//table");
        String discRowXpath = "//table//tr[contains(., '" + discNumber + "')]";
        assertTrue(findElements(discRowXpath, SelectorType.XPATH).isEmpty(),
                "Disc " + discNumber + " should not be present in table");
    }

    public void verifyDiscsTableHasRows(int expectedCount) {
        waitForElementToBePresent("//table");
        String rowsXpath = "//table//tbody//tr";
        int actualCount = findElements(rowsXpath, SelectorType.XPATH).size();
        assertTrue(actualCount == expectedCount,
                "Expected " + expectedCount + " disc rows, but found " + actualCount);
    }

    public void verifyDiscsTableHasAtLeastRows(int minimumCount) {
        waitForElementToBePresent("//table");
        String rowsXpath = "//table//tbody//tr";
        int actualCount = findElements(rowsXpath, SelectorType.XPATH).size();
        assertTrue(actualCount >= minimumCount,
                "Expected at least " + minimumCount + " disc rows, but found " + actualCount);
    }

    public void captureFirstDiscNumber() {
        waitForElementToBePresent("//table");
        String firstDiscCellXpath = "//table//tbody//tr[1]//td[contains(@data-heading, 'Disc number')]";
        WebElement discCell = findElement(firstDiscCellXpath, SelectorType.XPATH);
        lastSelectedDiscNumber = discCell.getText().trim();
    }

    public void removeFirstDisc() {
        captureFirstDiscNumber();
        
        // Click checkbox for this disc first - find by row containing disc number, then checkbox within
        String checkboxXpath = "//table//tr[.//td[contains(text(), '" + lastSelectedDiscNumber + "')]]//input[@type='checkbox' and @name='table[id][]']";
        waitAndClick(checkboxXpath, SelectorType.XPATH);
        
        removeDisc(lastSelectedDiscNumber);
    }

    public void replaceFirstDisc() {
        captureFirstDiscNumber();
        
        // Click checkbox for this disc first - find by row containing disc number, then checkbox within
        String checkboxXpath = "//table//tr[.//td[contains(text(), '" + lastSelectedDiscNumber + "')]]//input[@type='checkbox' and @name='table[id][]']";
        waitAndClick(checkboxXpath, SelectorType.XPATH);
        
        replaceDisc(lastSelectedDiscNumber);
    }

    public void removeDisc(String discNumber) {
        // Click the Remove button for this disc
        String removeButtonXpath = "//table//tr[contains(., '" + discNumber + "')]//button[contains(@name, 'table[action][void]')]";
        waitAndClick(removeButtonXpath, SelectorType.XPATH);

        // Modal confirmation appears
        waitForElementToBePresent("//div[contains(@class, 'modal')]");
        String confirmXpath = "//div[contains(@class, 'modal')]//button[contains(text(), 'Confirm')]";
        waitForElementToBePresent(confirmXpath);
        waitAndClick(confirmXpath, SelectorType.XPATH);

        // Wait for page to refresh - disc row will now show "Pending" status instead of the disc number
        waitForElementToBePresent("//table");
    }

    public void replaceDisc(String discNumber) {
        // Click the Replace button for this disc
        String replaceButtonXpath = "//table//tr[contains(., '" + discNumber + "')]//button[contains(@name, 'table[action][replace]')]";
        waitAndClick(replaceButtonXpath, SelectorType.XPATH);

        // Modal confirmation appears
        waitForElementToBePresent("//div[contains(@class, 'modal')]");
        String confirmXpath = "//div[contains(@class, 'modal')]//button[contains(text(), 'Confirm')]";
        waitForElementToBePresent(confirmXpath);
        waitAndClick(confirmXpath, SelectorType.XPATH);

        // Wait for page to refresh after replace
        waitForElementToBePresent("//table");
    }
}


