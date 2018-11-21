package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.junit.Assert;

public class PsvSurrenders extends BasePage implements En {
    private String town;
    private String phoneNumber;

    public PsvSurrenders(World world) {
        And("^i choose to surrender my licence$", () -> {
            world.UIJourneySteps.navigateToSurrendersStartPage();
        });
        When("^i am on the review contact details page$", () -> {
            click("//*[@id='submit']", SelectorType.XPATH);
            waitForTextToBePresent("Review your contact information");
        });

        Then("^the correct licence details should be displayed$", () -> {
            String licenceNumber = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(world.APIJourneySteps.getLicenceStatusDetails(),licenceNumber);
            String licenceHolder = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][1]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(world.createLicence.getForeName()+" "+world.createLicence.getFamilyName(),licenceHolder);
        });
        And("^the correct correspondence details should be displayed$", () -> {
            String licenceAddress1 = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][2]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(world.createLicence.getAddressLine1(),licenceAddress1);
            String licenceTown = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][2]/div[@class='app-check-your-answers__contents'][3]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(world.createLicence.getTown(),licenceTown);
        });
        And("^the correct contact details should be displayed$", () -> {
            String contactNumber = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][1]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(world.createLicence.getPhoneNumber(),contactNumber);
            String emailAddress = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][3]/div[@class='app-check-your-answers__contents'][3]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(world.createLicence.getBusinessEmailAddress(),emailAddress);
        });
        And("^i update my correspondence address$", () -> {
            this.town = "Leicester";
            click("//a[contains(text(),'Change')][1]", SelectorType.XPATH);
            waitForTextToBePresent("Addresses");
            findElement("addressTown", SelectorType.ID,5).clear();
            enterText("addressTown",this.town,SelectorType.ID);
            click("//*[@id='form-actions[save]']",SelectorType.XPATH);
            waitForTextToBePresent("Review your contact information");
        });
        Then("^the new correspondence details should be displayed on the review page$", () -> {
            String licenceTown = getText("//*[@class='app-check-your-answers app-check-your-answers--long'][2]/div[@class='app-check-your-answers__contents'][2]/dd[@class='app-check-your-answers__answer']", SelectorType.XPATH);
            Assert.assertEquals(this.town,licenceTown);
        });
    }
}