package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.IllegalBrowserException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class PaymentProcessing extends BasePage implements En {
    private World world;
    private String currentFeeCount;
    private String feeNumber;

    public String getCurrentFeeCount() {
        return currentFeeCount;
    }

    private void setCurrentFeeCount(String currentFeeCount) {
        this.currentFeeCount = currentFeeCount;
    }

    public String getFeeNumber() {
        return feeNumber;
    }

    public void setFeeNumber(String feeNumber) {
        this.feeNumber = feeNumber;
    }

    public PaymentProcessing(World world) {
        Given("^i am on the payment processing page$", () -> {
            waitAndClick("//li[@class='admin__title']", SelectorType.XPATH);
            clickByLinkText("Payment processing");
            waitForTextToBePresent("Payment Processing");
        });
        When("^i add a new \"([^\"]*)\" fee$", (String arg0) -> {
            String amount = "100";
            selectValueFromDropDown("status", SelectorType.ID, "Current");
            // Refresh page
            javaScriptExecutor("location.reload(true)");
            String feeCountBeforeAddingNewFee = getElementValueByText("//div[@class='table__header']/h3", SelectorType.XPATH);
            setCurrentFeeCount(world.genericUtils.stripAlphaCharacters(feeCountBeforeAddingNewFee));
            findElement("status", SelectorType.ID, 30).getAttribute("value").equals("current");
            world.UIJourneySteps.createAdminFee(amount, arg0);
        });
        Then("^the fee should be created$", () -> {
            // Refresh page
            javaScriptExecutor("location.reload(true)");
            String newFeeCount = world.genericUtils.stripAlphaCharacters(getElementValueByText("//div[@class='table__header']/h3", SelectorType.XPATH));
            waitForTextToBePresent("Outstanding");
            assertNotEquals(currentFeeCount, newFeeCount);
        });
        Then("^the fee should be paid and no longer visible in the fees table$", () -> {
            assertFalse(checkForFullMatch(getFeeNumber()));
            selectValueFromDropDown("status", SelectorType.ID, "Historic");
            waitForTextToBePresent("Paid");
        });
        And("^when i pay for the fee by \"([^\"]*)\"$", (String arg0) -> {
            setFeeNumber(world.genericUtils.stripAlphaCharacters(String.valueOf(findElement("//*/tbody/tr[1]/td[1]", SelectorType.XPATH, 10).getText())));
            world.UIJourneySteps.selectFee();
            if (arg0.equals("card")) {
                String bankCardNumber = "4006000000000600";
                String cardExpiryMonth = "10";
                String cardExpiryYear = "50";
                world.UIJourneySteps.payFee(null, arg0, bankCardNumber, cardExpiryMonth, cardExpiryYear);
            } else {
                world.UIJourneySteps.payFee("100", arg0, null, null, null);
            }
        });
    }
}