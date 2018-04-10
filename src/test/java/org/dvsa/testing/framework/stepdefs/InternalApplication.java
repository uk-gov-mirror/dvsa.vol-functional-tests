package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertFalse;

import static org.dvsa.testing.framework.stepdefs.Utils.GenerateLetter.generateLetter;
import static org.junit.Assert.assertNotNull;

public class InternalApplication extends BasePage implements En {
    public InternalApplication() {
        Then("^The pop up should contain letter details$", () -> {
            String categoryValue = getAttributeText("//*[@id='generate-document']/div[2]/text()", SelectorType.XPATH);
            assertNotNull(categoryValue);

            String subCategoryValue = getAttributeText("//*[@id='generate-document']/div[3]/text()", SelectorType.XPATH);
            assertNotNull(subCategoryValue);

            String templateValue = getAttributeText("//*[@id='generate-document']/div[4]/span", SelectorType.XPATH);
            assertNotNull(templateValue);

            String docStoreLink = getAttributeText("//*[@id='generate-document']/div[4]/div/strong/text()", SelectorType.XPATH);
            assertNotNull(docStoreLink);
        });
        When("^I generate a letter$", () -> {
           generateLetter();
        });
    }
}
