package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import junit.framework.TestCase;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SurrenderLogic extends BasePage implements En {
    String discLost = "2";
    String discDestroyed = "2";
    String discStolen = "1";
    String addressLine1 = "Surrender";
    String addressLine2 = "Premises";
    String contactNumber = "07123465976";


    public SurrenderLogic(World world) {
        And("^i have started a surrender$", () -> {
            world.UIJourneySteps.navigateToSurrendersStartPage();
            world.UIJourneySteps.startSurrender();
            world.UIJourneySteps.addDiscInformation(discDestroyed, discLost, discStolen);
        });
        Given("^i update my address details on my licence$", () -> {
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Addresses");
            world.UIJourneySteps.updateContactDetails(addressLine1, addressLine2, contactNumber);
        });
        Then("^continue with application link is displayed$", () -> {
            assertFalse(isLinkPresent("Apply to surrender licence", 30));
            assertTrue(isLinkPresent("Continue with application to surrender licence", 30));
        });
        And("^user is taken to information change page on clicking continue application$", () -> {
            clickByLinkText("Continue with");
            assertTrue(Browser.navigate().getCurrentUrl().contains("information-changed"));
            String expectedChangedText = "Warning\n" +
                    "Since starting your application to surrender your licence, you have made changes to your licence information.";
            String actualChangeText = getText("//*[@class='govuk-warning-text__text']", SelectorType.XPATH);
            assertEquals(expectedChangedText,actualChangeText);
        });
        Given("^i add a disc to my licence$", () -> {
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Licence discs");
            waitAndClick("//*[@value='Remove']",SelectorType.XPATH);
            waitAndClick("form-actions[submit]",SelectorType.NAME);
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Continue with");
            assertTrue(Browser.navigate().getCurrentUrl().contains("information-changed"));
            String expectedChangedText = "Warning\n" +
                    "Since starting your application to surrender your licence, you have made changes to your licence information.";
            String actualChangeText = getText("//*[@class='govuk-warning-text__text']", SelectorType.XPATH);
            assertEquals(expectedChangedText,actualChangeText);
        });
        And("^the new correspondence details are displayed on correspondence page$", () -> {
            click("//*[contains(text(),'Review')]", SelectorType.XPATH);
            assertEquals(world.UIJourneySteps.getSurrenderAddressDetails(), addressLine1 + "\n" + addressLine2);
        });
    }
}

