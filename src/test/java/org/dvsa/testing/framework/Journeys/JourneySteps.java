package org.dvsa.testing.framework.Journeys;

import activesupport.IllegalBrowserException;
import activesupport.MissingDriverException;
import activesupport.MissingRequiredArgument;
import activesupport.aws.s3.S3;
import activesupport.driver.Browser;
import activesupport.string.Str;
import activesupport.system.Properties;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.stepdefs.World;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;

import java.net.MalformedURLException;

import static junit.framework.TestCase.assertTrue;

public class JourneySteps extends BasePage {

    private World world;
    private static final String zipFilePath = "/src/test/resources/ESBR.zip";
    static int tmCount;
    EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));

    public JourneySteps(World world){
        this.world = world;
    }

    public static void internalSiteAddBusNewReg(int day, int month, int year) throws IllegalBrowserException {
        waitForTextToBePresent("Service details");
        assertTrue(isTextPresent("Service No. & type", 5));
        enterText("serviceNo", "123", SelectorType.ID);
        enterText("startPoint", Str.randomWord(9), SelectorType.ID);
        enterText("finishPoint", Str.randomWord(11), SelectorType.ID);
        enterText("via", Str.randomWord(5), SelectorType.ID);
        selectServiceType("//ul[@class='chosen-choices']", "//*[@id=\"busServiceTypes_chosen\"]/div/ul/li[1]", SelectorType.XPATH);
        enterDate(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
        enterText("effectiveDate_day", String.valueOf(day), SelectorType.ID);
        enterText("effectiveDate_month", String.valueOf(month), SelectorType.ID);
        enterText("effectiveDate_year", String.valueOf(year), SelectorType.ID);
        click(nameAttribute("button", "form-actions[submit]"));
    }

    private static void enterDate(int day, int month, int year) throws IllegalBrowserException {
        enterText("receivedDate_day", String.valueOf(day), SelectorType.ID);
        enterText("receivedDate_month", String.valueOf(month), SelectorType.ID);
        enterText("receivedDate_year", String.valueOf(year), SelectorType.ID);
    }

    public void uploadAndSubmitESBR(String state, int interval) throws MissingRequiredArgument, MalformedURLException, IllegalBrowserException, MissingDriverException {
        // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
        world.genericUtils.modifyXML(state, interval);
        GenericUtils.zipFolder();
        externalUserLogin();
        clickByLinkText("Bus");
        waitAndClick("//*[@id='main']/div[2]/ul/li[2]/a", SelectorType.XPATH);
        click(nameAttribute("button", "action"));
        String workingDir = System.getProperty("user.dir");
        uploadFile("//*[@id='fields[files][file]']", workingDir + zipFilePath, "document.getElementById('fields[files][file]').style.left = 0", SelectorType.XPATH);
        waitAndClick("//*[@name='form-actions[submit]']", SelectorType.XPATH);
    }

    public void searchAndViewApplication() throws IllegalBrowserException {
        selectValueFromDropDown("//select[@id='search-select']", SelectorType.XPATH, "Applications");

        String variationApplicationNumber = world.updateLicence.getVariationApplicationNumber();
        if (variationApplicationNumber != null) {
            do {
                SearchNavBar.search(variationApplicationNumber);
            } while (!isLinkPresent(variationApplicationNumber, 60));
            clickByLinkText(variationApplicationNumber);
            assertTrue(Boolean.parseBoolean(String.valueOf(Browser.navigate().getCurrentUrl().contains("variation"))));
        } else {
            do {
                SearchNavBar.search(String.valueOf(world.createLicence.getApplicationNumber()));
            } while (!isLinkPresent(world.createLicence.getApplicationNumber(), 200));
            clickByLinkText(world.createLicence.getApplicationNumber());
            if (isLinkPresent("Interim", 60))
                clickByLinkText("Interim ");
        }
    }

        public void createAdminFee(String amount, String feeType) throws IllegalBrowserException {
            waitAndClick("//button[@id='new']", SelectorType.XPATH);
            waitForTextToBePresent("Create new fee");
            selectValueFromDropDown("fee-details[feeType]", SelectorType.NAME, feeType);
            waitAndEnterText("amount", SelectorType.ID, amount);
            waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
        }

    public void payFee(String amount, @NotNull String paymentMethod, String bankCardNumber, String cardExpiryMonth, String cardExpiryYear) throws IllegalBrowserException {
        do {
            //nothing
        } while (isElementPresent("//button[@id='form-actions[submit]']", SelectorType.XPATH));
        waitAndClick("//tbody/tr[2]/td[7]", SelectorType.XPATH);
        waitAndClick("//*[@value='Pay']", SelectorType.XPATH);
        waitForTextToBePresent("Pay fee");

        if (paymentMethod.toLowerCase().trim().equals("cash") || paymentMethod.toLowerCase().trim().equals("cheque") || paymentMethod.toLowerCase().trim().equals("postal")) {
            enterText("details[received]", amount, SelectorType.NAME);
            enterText("details[payer]", "Automation payer", SelectorType.NAME);
            enterText("details[slipNo]", "1234567", SelectorType.NAME);
            enterText("details[customerName]", "Jane Doe", SelectorType.NAME);
        }
        if (paymentMethod.toLowerCase().trim().equals("card") && (isTextPresent("Pay fee", 10))) {
            selectValueFromDropDown("details[paymentType]", SelectorType.NAME, "Card Payment");
            enterText("details[customerName]", "Veena Skish", SelectorType.NAME);
            enterText("details[customerReference]", "AutomationCardCustomerRef", SelectorType.NAME);
            findAddress();
        }
        switch (paymentMethod.toLowerCase().trim()) {
            case "cash":
                selectValueFromDropDown("details[paymentType]", SelectorType.NAME, "Cash");
                enterText("details[customerReference]", "AutomationCashCustomerRef", SelectorType.NAME);
                findAddress();
                break;
            case "cheque":
                selectValueFromDropDown("details[paymentType]", SelectorType.NAME, "Cheque");
                enterText("details[chequeNo]", "12345", SelectorType.NAME);
                enterText("details[customerReference]", "AutomationChequeCustomerRef", SelectorType.NAME);
                enterText("details[chequeDate][day]", String.valueOf(getCurrentDayOfMonth()), SelectorType.NAME);
                enterText("details[chequeDate][month]", String.valueOf(getCurrentMonth()), SelectorType.NAME);
                enterText("details[chequeDate][year]", String.valueOf(getCurrentYear()), SelectorType.NAME);
                findAddress();
                break;
            case "postal":
                selectValueFromDropDown("details[paymentType]", SelectorType.NAME, "Postal Order");
                enterText("details[customerReference]", "AutomationPostalOrderCustomerRef", SelectorType.NAME);
                enterText("details[poNo]", "123456", SelectorType.NAME);
                findAddress();
                break;
            case "card":
                customerPaymentModule(bankCardNumber, cardExpiryMonth, cardExpiryYear);
                break;
        }
    }

    private void customerPaymentModule(String bankCardNumber, String cardExpiryMonth, String cardExpiryYear) throws IllegalBrowserException {
        waitForTextToBePresent("Card Number*");
        enterText("//*[@id='scp_cardPage_cardNumber_input']", bankCardNumber, SelectorType.XPATH);
        enterText("//*[@id='scp_cardPage_expiryDate_input']", cardExpiryMonth, SelectorType.XPATH);
        enterText("//*[@id='scp_cardPage_expiryDate_input2']", cardExpiryYear, SelectorType.XPATH);
        enterText("//*[@id='scp_cardPage_csc_input']", "123", SelectorType.XPATH);
        click("//*[@id='scp_cardPage_buttonsNoBack_continue_button']", SelectorType.XPATH);
        enterText("//*[@id='scp_additionalInformationPage_cardholderName_input']", "Mr Regression Test", SelectorType.XPATH);
        click("//*[@id='scp_additionalInformationPage_buttons_continue_button']", SelectorType.XPATH);
        waitForTextToBePresent("Online Payments");
        click("//*[@id='scp_confirmationPage_buttons_payment_button']", SelectorType.XPATH);
        if (isElementPresent("//*[@id='scp_storeCardConfirmationPage_buttons_back_button']", SelectorType.XPATH)) {
            waitForTextToBePresent("Online Payments");
            click("//*[@id='scp_storeCardConfirmationPage_buttons_back_button']", SelectorType.XPATH);
            waitForTextToBePresent("Payment successful");
            clickByLinkText("Back");
            waitForTextToBePresent("There are currently no outstanding fees to pay");
        }
    }


    private void findAddress() throws IllegalBrowserException {

        enterText("address[searchPostcode][postcode]", "NG1 5FW", SelectorType.NAME);
        waitAndClick("address[searchPostcode][search]", SelectorType.NAME);
        waitAndSelectByIndex("", "//*[@id='fee_payment']/fieldset[2]/fieldset/div[3]/select[@name='address[searchPostcode][addresses]']", SelectorType.XPATH, 1);
        do {
            world.genericUtils.retryingFindClick(By.xpath("//*[@id='form-actions[pay]']"));
        } while (getAttribute("//*[@name='address[addressLine1]']", SelectorType.XPATH, "value").isEmpty());
    }

    public void addPerson(String firstName, String lastName) throws IllegalBrowserException {
        waitForTextToBePresent("Current licences");
        clickByLinkText(world.createLicence.getLicenceNumber());
        waitForTextToBePresent("View your licence");
        clickByLinkText("Directors");
        waitForTextToBePresent("Directors");
        clickByName("add");
        waitForTextToBePresent("Add a director");
        selectValueFromDropDown("//select[@id='title']", SelectorType.XPATH, "Dr");
        enterText("forename", firstName, SelectorType.ID);
        enterText("familyname", lastName, SelectorType.ID);
        enterText("dob_day", String.valueOf(getPastDayOfMonth(5)), SelectorType.ID);
        enterText("dob_month", String.valueOf(getCurrentMonth()), SelectorType.ID);
        enterText("dob_year", String.valueOf(getPastYear(20)), SelectorType.ID);
        clickByName("form-actions[saveAndContinue]");
    }

    public String navigateToInternalTask(World world) throws IllegalBrowserException, MissingDriverException, MalformedURLException {
        world.genericUtils.createAdminUser();
        world.journeySteps.internalAdminUserLogin();
        world.journeySteps.searchAndViewApplication();
        clickByLinkText("Processing");
        clickByLinkText("Add director(s)");
        waitForTextToBePresent("Linked to");
        return findElement("//div[4]/label", SelectorType.XPATH, 30).getAttribute("class");
    }

    public void addPreviousConviction() throws IllegalBrowserException {
        selectValueFromDropDown("data[title]",SelectorType.ID,"Ms");
        enterText("data[forename]",Str.randomWord(8),SelectorType.NAME);
        enterText("data[familyName]",Str.randomWord(8),SelectorType.NAME);
        enterText("data[notes]",Str.randomWord(30),SelectorType.NAME);
        enterText("dob_day", String.valueOf(getPastDayOfMonth(5)), SelectorType.ID);
        enterText("dob_month", String.valueOf(getCurrentMonth()), SelectorType.ID);
        enterText("dob_year", String.valueOf(getPastYear(20)), SelectorType.ID);
        enterText("data[categoryText]",Str.randomWord(50),SelectorType.NAME);
        enterText("data[courtFpn]","Clown",SelectorType.NAME);
        enterText("data[penalty]","Severe",SelectorType.NAME);
        clickByName("form-actions[submit]");
    }

    public void internalAdminUserLogin() throws MissingRequiredArgument, IllegalBrowserException, MissingDriverException, MalformedURLException {
        String myURL = URL.build(ApplicationType.INTERNAL, env).toString();
        String newPassword = "Password1";
        String password = S3.getTempPassword(world.updateLicence.adminUserEmailAddress);

        if (Browser.isBrowserOpen()) {
            //Quit Browser and open a new window
            Browser.navigate().manage().deleteAllCookies();
        }
        Browser.navigate().get(myURL);
        System.out.println(world.updateLicence.adminUserLogin + "UserLogin");

        if (activesupport.driver.Browser.navigate().getCurrentUrl().contains("da")) {
            Login.signIn(world.updateLicence.adminUserLogin, password);}
        if (isTextPresent("Username", 60))
            Login.signIn(world.updateLicence.adminUserLogin, password);
        if (isTextPresent("Current password", 60)) {
            enterField(nameAttribute("input", "oldPassword"), password);
            enterField(nameAttribute("input", "newPassword"), newPassword);
            enterField(nameAttribute("input", "confirmPassword"), newPassword);
            click(nameAttribute("input", "submit"));
        }
    }

    public void externalUserLogin() throws MissingRequiredArgument, IllegalBrowserException, MissingDriverException, MalformedURLException {
        String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
        if (Browser.isBrowserOpen()) {
            //Quit Browser and open a new window
            Browser.navigate().manage().deleteAllCookies();
        }
        Browser.navigate().get(myURL);
        String password = S3.getTempPassword(world.createLicence.getEmailAddress());
        //check if user exists

        if (isTextPresent("Username", 60))
            Login.signIn(world.createLicence.getLoginId(), password);
        if (isTextPresent("Current password", 60)) {
            enterField(nameAttribute("input", "oldPassword"), password);
            enterField(nameAttribute("input", "newPassword"), "Password1");
            enterField(nameAttribute("input", "confirmPassword"), "Password1");
            click(nameAttribute("input", "submit"));
        }
    }

    public static void generateLetter() throws IllegalBrowserException {
        clickByLinkText("Docs & attachments");
        isTextPresent("1 Docs & attachments", 60);
        clickByName("New letter");
        findElement("//*[@id='modal-title']", SelectorType.XPATH, 600);
        waitAndSelectByIndex("Generate letter", "//*[@id='category']", SelectorType.XPATH, 1);
        waitAndSelectByIndex("Generate letter", "//*[@id='documentSubCategory']", SelectorType.XPATH, 1);
        waitAndSelectByIndex("Generate letter", "//*[@id='documentTemplate']", SelectorType.XPATH, 5);
        waitAndClick("//*[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public void removeInternalTransportManager() throws IllegalBrowserException {
        assertTrue(isTextPresent("Overview", 60));
        if (!isLinkPresent("Transport", 60) && isTextPresent("Granted", 60)) {
            clickByLinkText(world.createLicence.getLicenceNumber());
            tmCount = returnTableRows("//*[@id='lva-transport-managers']/fieldset/div/div[2]/table/tbody/tr", SelectorType.XPATH);
        }
        clickByLinkText("Transport");
        isTextPresent("TransPort Managers", 60);
        click("//*[@value='Remove']", SelectorType.XPATH);
    }

    public void addDirectorWithoutConvictions(String firstName, String lastName) throws MissingDriverException, IllegalBrowserException, MalformedURLException {
        world.journeySteps.externalUserLogin();
        world.journeySteps.addPerson(firstName, lastName);
        world.genericUtils.selectAllRadioButtons("No");
        clickByName("form-actions[saveAndContinue]");
        world.genericUtils.selectAllRadioButtons("No");
        clickByName("form-actions[saveAndContinue]");
    }

    public void changeVehicleReq(String noOfVehicles) throws IllegalBrowserException {
        clickByLinkText("Operating centres and authorisation");
        clickByLinkText("change your licence");
        waitAndClick("button[name='form-actions[submit]'", SelectorType.CSS);
        waitAndClick("//*[@id=\"OperatingCentres\"]/fieldset[1]/div/div[2]/table/tbody/tr/td[1]/input", SelectorType.XPATH);
        enterField(nameAttribute("input", "data[noOfVehiclesRequired]"),noOfVehicles);
        if (Integer.parseInt(noOfVehicles) > world.createLicence.getNoOfVehiclesRequired()) {
            click(nameAttribute("button", "form-actions[submit]"));}
        click(nameAttribute("button", "form-actions[submit]"));
    }

    public void changeVehicleAuth (String  noOfAuthVehicles) throws IllegalBrowserException {
        enterField(nameAttribute("input", "data[totAuthVehicles]"),noOfAuthVehicles);
        click(nameAttribute("button", "form-actions[save]"));
    }

}
