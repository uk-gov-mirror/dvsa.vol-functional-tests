package org.dvsa.testing.framework.stepdefs.Utils;

import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

public class GenerateLetter extends BasePage {

    public static void generateLetter() throws InterruptedException {
        clickByLinkText("Docs & attachments");
        isTextPresent("1 Docs & attachments", 60);
        clickByName("New letter");
        findElement("//*[@id='modal-title']", SelectorType.XPATH, 600);
        waitAndSelectByIndex("//*[@id='category']", 1);
        waitAndSelectByIndex("//*[@id='documentSubCategory']", 1);
        waitAndSelectByIndex("//*[@id='documentTemplate']", 5);
        waitAndClick("//*[@id='form-actions[submit]']", SelectorType.XPATH);
    }
}