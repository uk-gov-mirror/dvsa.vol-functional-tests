package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
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
        Given("^i remove a disc to my licence$", () -> {
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Licence discs");
            waitAndClick("//*[@value='Remove']",SelectorType.XPATH);
            waitAndClick("form-actions[submit]",SelectorType.NAME);
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
        });
        And("^the new correspondence details are displayed on correspondence page$", () -> {
            click("//*[contains(text(),'Review')]", SelectorType.XPATH);
            assertEquals(world.UIJourneySteps.getSurrenderAddressLine1(), addressLine1 + "\n" + addressLine2);
        });
        Given("^i add a disc to my licence$", () -> {
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Licence discs");
            waitAndClick("//*[@id='add']",SelectorType.XPATH);
            waitAndEnterText("data[additionalDiscs]",SelectorType.ID,"2");
            waitAndClick("form-actions[submit]",SelectorType.NAME);
            world.updateLicence.printLicenceDiscs();
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
        });
        Given("^i am on the surrenders review contact details page$", () -> {
            assertTrue(Browser.navigate().getCurrentUrl().contains("review-contact-details"));
        });
        And("^i leave the surrenders journey$", () -> {
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
        });
        And("^user is taken to review contact page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("review-contact-details"));
            assertEquals(world.UIJourneySteps.getSurrenderAddressLine1(), world.createLicence.getAddressLine1());
            assertEquals(world.UIJourneySteps.getSurrenderTown(), world.createLicence.getTown());
        });
        Given("^i am on the surrenders current discs page$", () -> {
            click("//*[@id='form-actions[submit]']", SelectorType.XPATH);
            assertTrue(Browser.navigate().getCurrentUrl().contains("current-discs"));
        });
        And("^user is taken to the surrenders current discs on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("current-discs"));
        });
        And("^i am on the operator licence page$", () -> {
            world.UIJourneySteps.addDiscInformation("2","2","1");
            waitForTextToBePresent("In your possession");
            assertTrue(Browser.navigate().getCurrentUrl().contains("operator-licence"));
        });
        And("^user is taken to the operator licence page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("operator-licence"));
        });
        And("^i am on the community licence page$", () -> {
            if (world.createLicence.getLicenceType().equals("standard_international")) {
                world.UIJourneySteps.addDiscInformation("2", "2", "1");
                waitForTextToBePresent("In your possession");
                world.UIJourneySteps.addOperatorLicenceDetails();
                assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
            }{
                //doesn't need to be run
            }
        });
        And("^user is taken to the community licence page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
        });
        And("^i am on the disc and doc review page$", () -> {
                world.UIJourneySteps.addDiscInformation("2", "2", "1");
                waitForTextToBePresent("In your possession");
                world.UIJourneySteps.addOperatorLicenceDetails();
            if (world.createLicence.getLicenceType().equals("standard_international")){
                assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
                world.UIJourneySteps.addCommunityLicenceDetails();
            }
            assertTrue(Browser.navigate().getCurrentUrl().contains("review"));
        });
        And("^user is taken to the disc and doc review page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("review"));
        });
    }
}