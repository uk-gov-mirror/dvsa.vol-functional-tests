package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class InternalApplication extends BasePage implements En {
    public InternalApplication(World world) {
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
            assertTrue(docStoreLink.contains(".rtf"));
        });
        When("^a caseworker adds a new operating centre out of the traffic area$", () -> {
            world.UIJourneySteps.addNewOperatingCentre();
        });
        Then("^the postcode warning message should be displayed on internal$", () -> {
            assertTrue(isTextPresent("This operating centre is in a different traffic area from the other centres.",10));
            click("form-actions[confirm-add]",SelectorType.ID);
            click("form-actions[submit]",SelectorType.ID);
            waitForTextToBePresent("Operating centres and authorisation");
            assertTrue(isElementPresent("//*[@value='2 MAR PLACE, ALLOA, FK10 1AA']",SelectorType.XPATH));
        });
    }
}