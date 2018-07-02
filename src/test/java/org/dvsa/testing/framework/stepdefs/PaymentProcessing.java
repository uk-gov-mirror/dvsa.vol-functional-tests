package org.dvsa.testing.framework.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.eo.Se;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class PaymentProcessing extends BasePage implements En {
    public PaymentProcessing() {
        Given("^i am on the payment processing page$", () -> {
            waitAndClick("//li[@class='admin__title']", SelectorType.XPATH);
            clickByLinkText("Payment processing");
            waitForTextToBePresent("Payment Processing");
        });
        When("^i add a new fee$", () -> {
            String amount = "100";
            createAdminFee(amount);
        });
        Then("^the fee should be created$", () -> {

        });
        And("^when i pay for the fee$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the fee should be paid and no longer visible in the fees table$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }

    public void createAdminFee(String amount) {
        waitAndClick("//button[@id='new']", SelectorType.XPATH);
        waitForTextToBePresent("Create new fee");
        selectValueFromDropDown("fee-details[feeType]", SelectorType.NAME, "NETA Bus Fine");
        waitAndEnterText("amount", SelectorType.ID, amount);
        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
        do {
            //nothing
        } while (isElementPresent("//button[@id='form-actions[submit]']", SelectorType.XPATH));
        waitAndClick("//tbody/tr[1]/td[7]", SelectorType.XPATH);
        waitAndClick("//*[@value='Pay']", SelectorType.XPATH);
        waitForTextToBePresent("Pay fee");
        payFee(amount);
    }

    public void payFee(String amount){

        enterText("details[received]", amount, SelectorType.NAME);
        enterText("details[payer]", "Automation payer", SelectorType.NAME);
        enterText("details[slipNo]", "1234567", SelectorType.NAME);
        enterText("details[customerReference]", "AutomationCustomerRef", SelectorType.NAME);
        enterText("details[customerName]", "Jane Doe", SelectorType.NAME);
        enterText("address[searchPostcode][postcode]", "NG1 5FW", SelectorType.NAME);
        waitAndClick("address[searchPostcode][search]", SelectorType.NAME);
        clickByLinkText("Enter");
        waitAndSelectByIndex("", "//*[@id='fee_payment']/fieldset[2]/fieldset/div[3]/select[@name='address[searchPostcode][addresses]']", SelectorType.XPATH, 1);
        retryingFindClick(By.xpath("//*[@id='form-actions[pay]']"));
    }

    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                Browser.getDriver().findElement(by).click();
                result = true;
                break;
            } catch(Exception e) {
            }
            attempts++;
        }
        return result;
    }
}
