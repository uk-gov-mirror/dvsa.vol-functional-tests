package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.dvsa.testing.framework.Journeys.JourneySteps.generateLetter;
import static org.junit.Assert.assertNotNull;

public class InternalApplication extends BasePage implements En {
    public InternalApplication() {
        Then("^The pop up should contain letter details$", () -> {

            waitForTextToBePresent("Amend letter");

            String categoryValue = getElementValueByText("//*[@id='generate-document']/div[2]", SelectorType.XPATH);
            assertNotNull(categoryValue);

            String subCategoryValue = getElementValueByText("//*[@id='generate-document']/div[3]", SelectorType.XPATH);
            assertNotNull(subCategoryValue);

            String templateValue = getElementValueByText("//*[@id='generate-document']/div[4]", SelectorType.XPATH);
            assertNotNull(templateValue);

            String docStoreLink = getElementValueByText("//*[@id='generate-document']/div[4]/div/strong", SelectorType.XPATH);
            assertNotNull(docStoreLink);
        });

        When("^I generate a letter$", () -> {
            generateLetter();
        });
    }
}