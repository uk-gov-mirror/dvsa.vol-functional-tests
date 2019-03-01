package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.DataTable;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.getCurrentDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SurrenderLogic extends BasePage implements En {
    private String discLost = "2";
    private String discDestroyed = "2";
    private String discStolen = "1";
    private String addressLine1 = "Surrender";
    private String addressLine2 = "Premises";
    private String contactNumber = "07123465976";


    public SurrenderLogic(World world) {
        And("^i have started a surrender$", () -> {
            world.UIJourneySteps.navigateToSurrendersStartPage();
            world.UIJourneySteps.startSurrender();
        });
        Given("^i update my address details on my licence$", () -> {
            waitAndClick("form-actions[submit]", SelectorType.ID);
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
            assertEquals(expectedChangedText, actualChangeText);
        });
        Given("^i remove a disc to my licence$", () -> {
            waitAndClick("form-actions[submit]", SelectorType.ID);
            world.UIJourneySteps.addDiscInformation(discDestroyed, discLost, discStolen);
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Licence discs");
            waitAndClick("//*[@value='Remove']", SelectorType.XPATH);
            untilElementPresent("//*[@id='modal-title']", SelectorType.XPATH);
            waitAndClick("form-actions[submit]", SelectorType.NAME);
            javaScriptExecutor("location.reload(true)");
            waitForTextToBePresent("Disc number");
            clickByLinkText("Back");
        });
        And("^the new correspondence details are displayed on correspondence page$", () -> {
            click("//*[contains(text(),'Review')]", SelectorType.XPATH);
            assertEquals(world.UIJourneySteps.getSurrenderAddressLine1(), addressLine1 + "\n" + addressLine2);
        });
        Given("^i add a disc to my licence$", () -> {
            clickByLinkText("Home");
            clickByLinkText(world.createLicence.getLicenceNumber());
            clickByLinkText("Licence discs");
            waitAndClick("//*[@id='add']", SelectorType.XPATH);
            waitAndEnterText("data[additionalDiscs]", SelectorType.ID, "2");
            waitAndClick("form-actions[submit]", SelectorType.NAME);
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
            waitAndClick("form-actions[submit]", SelectorType.ID);
            world.UIJourneySteps.addDiscInformation(discDestroyed, discLost, discStolen);
            waitForTextToBePresent("In your possession");
            assertTrue(Browser.navigate().getCurrentUrl().contains("operator-licence"));
        });
        And("^user is taken to the operator licence page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("operator-licence"));
        });
        And("^i am on the community licence page$", () -> {
            if (world.createLicence.getLicenceType().equals("standard_international")) {
                waitAndClick("form-actions[submit]", SelectorType.ID);
                world.UIJourneySteps.addDiscInformation("2", "2", "1");
                waitForTextToBePresent("In your possession");
                world.UIJourneySteps.addOperatorLicenceDetails();
                assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
            }
            {
                //doesn't need to be run
            }
        });
        And("^user is taken to the community licence page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
        });
        And("^i am on the disc and doc review page$", () -> {
            waitAndClick("form-actions[submit]", SelectorType.ID);
            world.UIJourneySteps.addDiscInformation("2", "2", "1");
            waitForTextToBePresent("In your possession");
            world.UIJourneySteps.addOperatorLicenceDetails();
            if (world.createLicence.getLicenceType().equals("standard_international")) {
                assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
                world.UIJourneySteps.addCommunityLicenceDetails();
            }
            assertTrue(Browser.navigate().getCurrentUrl().contains("review"));
        });
        And("^user is taken to the disc and doc review page on clicking continue application$", () -> {
            clickByLinkText("Continue");
            assertTrue(Browser.navigate().getCurrentUrl().contains("review"));
        });
        And("^i am on the destroy disc page$", () -> {
            waitAndClick("form-actions[submit]", SelectorType.ID);
            world.UIJourneySteps.addDiscInformation("2", "2", "1");
            waitForTextToBePresent("In your possession");
            world.UIJourneySteps.addOperatorLicenceDetails();
            if (world.createLicence.getLicenceType().equals("standard_international")) {
                assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
                world.UIJourneySteps.addCommunityLicenceDetails();
            }
            waitAndClick("form-actions[submit]", SelectorType.NAME);
            assertTrue(Browser.navigate().getCurrentUrl().contains("destroy"));
        });
        And("^i am on the declaration page$", () -> {
            waitAndClick("form-actions[submit]", SelectorType.ID);
            world.UIJourneySteps.addDiscInformation("2", "2", "1");
            waitForTextToBePresent("In your possession");
            world.UIJourneySteps.addOperatorLicenceDetails();
            if (world.createLicence.getLicenceType().equals("standard_international")) {
                assertTrue(Browser.navigate().getCurrentUrl().contains("community-licence"));
                world.UIJourneySteps.addCommunityLicenceDetails();
            }
            waitAndClick("form-actions[submit]", SelectorType.NAME);
            waitAndClick("form-actions[submit]", SelectorType.NAME);
            assertTrue(Browser.navigate().getCurrentUrl().contains("declaration"));
        });
        And("^my application to surrender is under consideration$", () -> {
            world.updateLicence.printLicenceDiscs();
            world.UIJourneySteps.submitSurrender();
        });
        When("^the caseworker approves the surrender$", () -> {
            world.UIJourneySteps.caseworkManageSurrender();
            waitForTextToBePresent("Surrender details");
            waitAndClick("//*[contains(text(),'Digital signature')]", SelectorType.XPATH);
            waitAndClick("//*[contains(text(),'ECMS')]", SelectorType.XPATH);
            waitAndClick("actions[surrender]", SelectorType.ID);
        });
        Then("^the licence status should be \"([^\"]*)\"$", (String arg0) -> {
            waitForTextToBePresent("Overview");
            assertEquals(getText("//*[contains(@class,'status')]", SelectorType.XPATH), arg0.toUpperCase());
        });
        And("^the surrender menu should be hidden in internal$", () -> {
            assertFalse(isElementPresent("//*[contains(@id,'menu-licence_surrender"));
        });
        And("^the licence details page should display$", () -> {
            assertTrue(isTextPresent("Licence details", 40));
        });
        When("^the caseworker attempts to withdraw the surrender$", () -> {
            world.UIJourneySteps.caseworkManageSurrender();
            waitForTextToBePresent("Surrender details");
            waitAndClick("//*[contains(text(),'Digital signature')]", SelectorType.XPATH);
            waitAndClick("//*[contains(text(),'ECMS')]", SelectorType.XPATH);
            waitAndClick("//*[contains(text(),'Withdraw')]", SelectorType.XPATH);
        });
        Then("^a modal box is displayed$", () -> {
            assertTrue(isElementPresent("//*[@class='modal']", SelectorType.XPATH));
        });
        And("^a prompt message is displayed$", () -> {
            assertTrue(isTextPresent("Confirm Withdraw", 30));
            assertTrue(isTextPresent(String.format("Are you sure you wish to withdraw the application to surrender licence %s", world.createLicence.getLicenceNumber()), 30));
        });
        And("^the caseworker confirms the withdraw$", () -> {
            waitAndClick("continue", SelectorType.ID);
        });
        Then("^the modal box is hidden$", () -> {
            assertFalse(isElementPresent("//*[@class='modal']", SelectorType.XPATH));
        });
        And("^the caseworker cancels the withdraw$", () -> {
            waitAndClick("cancel", SelectorType.ID);
        });
        And("^the surrender menu should be displayed$", () -> {
            assertTrue(isElementPresent("//*[contains(text(),'Surrender')]", SelectorType.XPATH));
        });
        Then("^the user should remain on the surrender details page$", () -> {
            assertTrue(Browser.navigate().getCurrentUrl().contains("surrender-details"));
            assertTrue(isLinkPresent("Surrender", 30));
        });
        And("^the licence should not displayed in selfserve$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin(world.createLicence.getLoginId(), world.createLicence.getEmailAddress());
            assertFalse(isLinkPresent(world.UIJourneySteps.getLicenceNumber(), 30));
        });
        And("^the user should be able to re apply for a surrender in internal$", () -> {
            world.UIJourneySteps.submitSurrender();
        });
        Then("^the quick actions and decision buttons are not displayed for the menu items listed$", (DataTable buttons) -> {
            assertFalse(isTextPresent("Quick actions", 30));
            List<String> section_button = buttons.asList(String.class);
            for (String button : section_button) {
                clickByLinkText(button);
                assertTrue(isElementNotPresent("//*[contains(@id,'menu-licence-quick-actions')]",SelectorType.XPATH));
                assertTrue(isElementNotPresent("//*[contains(@id,'menu-licence-decisions')]",SelectorType.XPATH));
            }
        });
        When("^i search for my licence$", () -> {
            world.APIJourneySteps.createAdminUser();
            world.UIJourneySteps.navigateToInternalAdminUserLogin(world.updateLicence.adminUserLogin,world.updateLicence.adminUserEmailAddress);
            world.UIJourneySteps.searchAndViewLicence();
        });
        And("^i choose to surrender a single licence$", () -> {

        });
    }
}