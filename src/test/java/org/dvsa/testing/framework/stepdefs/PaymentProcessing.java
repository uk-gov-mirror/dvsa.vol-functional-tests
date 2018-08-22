package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PaymentProcessing extends BasePage implements En {
    private World world;
    private String currentFeeCount;

    public String getCurrentFeeCount() {
        return currentFeeCount;
    }

    public void setCurrentFeeCount(String currentFeeCount) {
        this.currentFeeCount = currentFeeCount;
    }

    private String historicalFeeCurrent;

    public String getHistoricalFeeCurrent() {
        return historicalFeeCurrent;
    }

    public void setHistoricalFeeCurrent(String historicalFeeCurrent) {
        this.historicalFeeCurrent = historicalFeeCurrent;
    }

    public PaymentProcessing(World world) {
        Given("^i am on the payment processing page$", () -> {
            waitAndClick("//li[@class='admin__title']", SelectorType.XPATH);
            clickByLinkText("Payment processing");
            waitForTextToBePresent("Payment Processing");
            selectValueFromDropDown("status", SelectorType.ID, "Historic");
            waitForTextToBePresent("Paid");
            String feeCountBeforePayingForNewFee = getElementValueByText("//div[@class='table__header']/h3", SelectorType.XPATH);
            setHistoricalFeeCurrent(world.genericUtils.stripAlphaCharacters(feeCountBeforePayingForNewFee));
        });
        When("^i add a new \"([^\"]*)\" fee$", (String arg0) -> {
            String amount = "100";
            selectValueFromDropDown("status", SelectorType.ID, "Current");
            // Refresh page
            javaScriptExecutor("location.reload(true)");
            String feeCountBeforeAddingNewFee = getElementValueByText("//div[@class='table__header']/h3", SelectorType.XPATH);
            setCurrentFeeCount(world.genericUtils.stripAlphaCharacters(feeCountBeforeAddingNewFee));
            findElement("status", SelectorType.ID, 30).getAttribute("value").equals("current");
            world.journeySteps.createAdminFee(amount, arg0);
        });
        Then("^the fee should be created$", () -> {
            // Refresh page
            javaScriptExecutor("location.reload(true)");
            String newFeeCount = world.genericUtils.stripAlphaCharacters(getElementValueByText("//div[@class='table__header']/h3", SelectorType.XPATH));
            waitForTextToBePresent("Outstanding");
            assertNotEquals(currentFeeCount, newFeeCount);
        });
        Then("^the fee should be paid and no longer visible in the fees table$", () -> {
            selectValueFromDropDown("status", SelectorType.ID, "Historic");
            waitForTextToBePresent("Paid");
            String feeCountAfterFeeHasBeenPaid = world.genericUtils.stripAlphaCharacters(getElementValueByText("//div[@class='table__header']/h3", SelectorType.XPATH));
            // Refresh page
            javaScriptExecutor("location.reload(true)");
            assertNotEquals(historicalFeeCurrent, feeCountAfterFeeHasBeenPaid);
            System.out.println(String.valueOf(Integer.parseInt(historicalFeeCurrent) + 1));
            assertEquals(String.valueOf(Integer.parseInt(historicalFeeCurrent) + 1), feeCountAfterFeeHasBeenPaid);
        });
        And("^when i pay for the fee by \"([^\"]*)\"$", (String arg0) -> {
            world.journeySteps.selectFee();
            if (arg0.equals("card")) {
                String bankCardNumber = "4006000000000600";
                String cardExpiryMonth = "10";
                String cardExpiryYear = "50";
                world.journeySteps.payFee(null, arg0, bankCardNumber, cardExpiryMonth, cardExpiryYear);
            } else {
                world.journeySteps.payFee("100", arg0, null, null, null);
            }
        });
    }
}